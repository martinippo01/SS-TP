import json
import math

# Load the JSON data
with open('../input/dynamic.json', 'r') as file:
    dynamic_data = json.load(file)

with open('../input/static.json', 'r') as file:
    static_data = json.load(file)

if static_data['inputData']['planeType'] != "BOX":
    raise Exception("Only implemented this analysis for box plane")

L = static_data['inputData']['L']
TOLERANCE = L / 90
DT = static_data['inputData']['maxTime'] / 100
M = static_data['inputData']['m']

def get_vn_obstacle(particles_crashed):
    hyp = (particles_crashed['position']['x'] ** 2 + particles_crashed['position']['y'] ** 2) ** 0.5
    cos = particles_crashed['position']['x'] / hyp
    sin = particles_crashed['position']['y'] / hyp
    return abs(particles_crashed['velocity']['vX'] * cos + particles_crashed['velocity']['vY'] * sin)

def get_vn_wall(particles_crashed):
    if abs(particles_crashed['position']['x']) < TOLERANCE or abs(particles_crashed['position']['x'] - L) < TOLERANCE:
        return abs((-1) * particles_crashed['velocity']['vX'])
    elif abs(particles_crashed['position']['y']) < TOLERANCE or abs(particles_crashed['position']['y'] - L) < TOLERANCE:
        return abs((-1) * particles_crashed['velocity']['vY'])
    print("failed")

velocities_n_wall = []
velocities_n_obstacle = []

pressure_wall_by_dt = []
pressure_obstacle_by_dt = []
time = 0

for i, event in enumerate(dynamic_data['events']):
    tc = event['tc']
    if tc > time + DT:
        time += DT
        # Append pressure with corresponding time (tc)
        pressure_wall_by_dt.append({"pressure": sum(velocities_n_wall), "tc": time})
        pressure_obstacle_by_dt.append({"pressure": sum(velocities_n_obstacle), "tc": time})
        velocities_n_wall = []
        velocities_n_obstacle = []
    if event['event'] == "OBSTACLE":
        velocities_n_obstacle.append((2 * M * get_vn_obstacle(event['particlesCrashed'][0])) / (DT * L))
    if event['event'] == "WALL":
        velocities_n_wall.append((2 * M * get_vn_wall(event['particlesCrashed'][0])) / (DT * L))

# Create the output dictionary
output_data = {
    "pressure_wall_by_dt": pressure_wall_by_dt,
    "pressure_obstacle_by_dt": pressure_obstacle_by_dt
}

# Write the data to a JSON file
with open('./pressure_data.json', 'w') as outfile:
    json.dump(output_data, outfile, indent=4)
