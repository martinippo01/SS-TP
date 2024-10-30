import json
import os
import sys
import time

if len(sys.argv) != 2:
    print("Usage: python analysis.py <config>")
    sys.exit(1)

config = sys.argv[1]

with open(config) as f:
    config = json.load(f)
    config = config["analysis"]

input_dir = config["inputDir"]

distance_by_ap_bp = {}
counter_by_ap_bp = {}

filenames = os.listdir(input_dir)
filenames_len = len(filenames)
filenames = filter(lambda filename: filename.endswith(".json"), filenames)
processed_count = 0

for idx, filename in enumerate(filenames):

    filepath = os.path.join(input_dir, filename)
    current_distance = 0
    with open(filepath, 'r') as f:
        data = json.load(f)
        params = data["params"]

        ap = params["Ap"]
        bp = params["Bp"]
        if ap not in distance_by_ap_bp:
            distance_by_ap_bp[ap] = {}
            counter_by_ap_bp[ap] = {}
        if bp not in distance_by_ap_bp[ap]:
            distance_by_ap_bp[ap][bp] = 0
            counter_by_ap_bp[ap][bp] = 0

        last_x_position = data["events"][-1]["redPlayer"]["pos"]["x"]
        current_distance = 100 - last_x_position
        counter_by_ap_bp[ap][bp] += 1
        distance_by_ap_bp[ap][bp] += (current_distance - distance_by_ap_bp[ap][bp]) / counter_by_ap_bp[ap][bp]
    print(f"[{idx + 1}/{filenames_len}] {filename} processed")

output_file_path = config['outputFilePath']
output_dir = output_file_path.rsplit('/', 1)[0]
gmt = time.gmtime()
timestamp = time.strftime('%Y-%m-%d_%H-%M-%S', gmt)
output_file = f'{output_file_path}_{timestamp}.json'

if not os.path.exists(output_dir):
    os.makedirs(output_dir)

print(distance_by_ap_bp)

with open(output_file, 'w') as f:
    json.dump(distance_by_ap_bp, f, indent=4)

print(f"Analysis results saved to {output_file}")
