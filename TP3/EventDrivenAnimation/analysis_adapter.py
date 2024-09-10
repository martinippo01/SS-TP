import sys
import json
import shutil
import os


def create_dir_if_not_exists(directory: str):
    if not os.path.exists(directory):
        os.makedirs(directory)


def adapt_iteration(input_dir: str, period: str, output_dir: str, timestamp: str):
    create_dir_if_not_exists(f"{output_dir}{timestamp}")
    shutil.copyfile(f"{input_dir}{timestamp}/static.json", f"{output_dir}{timestamp}/static.json")

    adapted_events = []
    with open(f"{input_dir}{timestamp}/dynamic.json", 'r') as file:
        dynamic_data = json.load(file)
        events = dynamic_data['events']
        next_time = 0
        for event in events:
            tc = event['tc']
            if tc >= next_time:
                adapted_event = event
                adapted_event['tc'] = next_time
                adapted_events.append(adapted_event)
                next_time += period

    adapted_dynamic_data = {
        "events": adapted_events
    }

    with open(f"{output_dir}{timestamp}/dynamic.json", 'w') as file:
        json.dump(adapted_dynamic_data, file)


if len(sys.argv) != 2:
    print("Usage: python analysis_adapter.py <json>")
    sys.exit(1)

with open(sys.argv[1], 'r') as input:
    data = json.load(input)

    input_dir = data['inputDir']
    period = data['period']
    output_dir = data['outputDir']

    iteration_dirs = [f for f in os.listdir(input_dir) if os.path.isdir(os.path.join(input_dir, f))]
    for iteration_dir in iteration_dirs:
        timestamp = iteration_dir.split('/')[-1]
        adapt_iteration(input_dir, period, output_dir, timestamp)
