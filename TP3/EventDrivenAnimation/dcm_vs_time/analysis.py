import statistics
import sys
import json
import os
import time


def create_dir_if_not_exists(directory: str):
    if not os.path.exists(directory):
        os.makedirs(directory)


def calculate_dc(position1, position2):
    x1, y1 = position1
    x2, y2 = position2
    # dc is the quadratic distance between two points
    # dc = (x2 - x1)^2 + (y2 - y1)^2
    return (x2 - x1) ** 2 + (y2 - y1) ** 2


if len(sys.argv) != 2:
    print("Usage: python analysis.py <json>")
    sys.exit(1)

dc_by_time = {}
# The time at which the big particle crashes into the wall as it can't move further anymore
saturation_times = []

with open(sys.argv[1], 'r') as input_file:
    config = json.load(input_file)['analysis']
    input_dir = config['inputDir']
    output_dir = config['outputDir']

    initial_position = None
    big_particle_id = None

    iteration_dirs = [f for f in os.listdir(input_dir) if os.path.isdir(os.path.join(input_dir, f))]
    for iteration_dir in iteration_dirs:

        if initial_position is None:
            static_file_path = f"{input_dir}{iteration_dir}/static.json"
            with open(static_file_path, 'r') as static_file:
                static_data = json.load(static_file)
                simulation_type = static_data['inputData']['simulationType']
                if simulation_type != 'BIG_PARTICLE':
                    print("Simulation type is not BIG_PARTICLE")
                    sys.exit(1)
                big_particle = static_data['inputData']['particles'][0]
                initial_position = big_particle['position']
                initial_position = (initial_position['x'], initial_position['y'])
                big_particle_id = big_particle['id']

        dynamic_file_path = f"{input_dir}{iteration_dir}/dynamic.json"
        with open(dynamic_file_path, 'r') as dynamic_file:
            dynamic_data = json.load(dynamic_file)
            events = dynamic_data['events']
            saturation_time = None
            for event in events:
                tc = event['tc']
                event_type = event['event']
                particles_crashed = event['particlesCrashed']

                if saturation_time is None and event == 'WALL' and big_particle_id in particles_crashed:
                    saturation_time = tc

                tc_str = f'{tc:.2f}'
                particles = event['particles']
                big_particle = next(p for p in particles if p['id'] == big_particle_id)
                position = big_particle['position']
                position = (position['x'], position['y'])
                dc = calculate_dc(initial_position, position)
                if tc_str not in dc_by_time:
                    dc_by_time[tc_str] = []
                dc_by_time[tc_str].append(dc)

            saturation_times.append(saturation_time)

analysis_by_time = {}
for tc_str, dcs in dc_by_time.items():
    mean = statistics.mean(dcs)
    std = statistics.stdev(dcs)
    analysis_by_time[tc_str] = {'mean': mean, 'std': std, 'dcs': dcs}

saturation_mean = statistics.mean(saturation_times)
saturation_std = statistics.stdev(saturation_times)
saturation = {
    'mean': saturation_mean,
    'std': saturation_std,
    'times': saturation_times
}

out = {
    'dc': analysis_by_time,
    'saturation': saturation
}


gmt = time.gmtime()
timestamp = time.strftime('%Y-%m-%d_%H-%M-%S', gmt)
output_file_full_name = f'{output_dir}dcm_vs_time_{timestamp}.json'
create_dir_if_not_exists(output_dir)

with open(output_file_full_name, 'w') as output_file:
    json.dump(out, output_file)

print(f"DCM vs Time data saved to {output_file_full_name}")


