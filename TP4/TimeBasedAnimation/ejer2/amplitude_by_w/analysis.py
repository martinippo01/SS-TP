import json
import os
import sys
import time as time_lib

if len(sys.argv) != 2:
    print("Usage: python analysis.py <config_file>")
    sys.exit(1)

config_file = sys.argv[1]

# Read the configuration file
config = None
with open(config_file, 'r') as f:
    config = json.load(f)
    config = config['analysis']

# Read the data file
input_file = config['inputFile']
amplitude_by_time = {}
with open(input_file, 'r') as f:
    input_json = json.load(f)
    steps = input_json['steps']
    for step in steps:
        time = step['time']
        positions = step['positions']
        max_amplitude = 0
        for position in positions:
            amplitude = abs(position['y'])
            max_amplitude = max(max_amplitude, amplitude)
        amplitude_by_time[time] = max_amplitude

# Write the output file
output_file_path = config['outputFilePath']
gmt = time_lib.gmtime()
timestamp = time_lib.strftime('%Y-%m-%d_%H-%M-%S', gmt)
output_dir = output_file_path.rsplit('/', 1)[0]
output_file = f'{output_file_path}_{timestamp}.json'

if not os.path.exists(output_dir):
    os.makedirs(output_dir)

with open(output_file, 'w') as f:
    json.dump(amplitude_by_time, f)

print(f"Amplitude by time data saved to {output_file}")