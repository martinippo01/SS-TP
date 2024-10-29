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

data_by_ap_bp = {}

filenames = os.listdir(input_dir)
filenames_len = len(filenames)
filenames = filter(lambda f: f.endswith(".json"), filenames)

for idx, filename in enumerate(filenames):
    filepath = os.path.join(input_dir, filename)
    with open(filepath, 'r') as f:
        data = json.load(f)
        params = data["params"]

        ap = params["Ap"]
        bp = params["Bp"]
        if ap not in data_by_ap_bp:
            data_by_ap_bp[ap] = {}
        if bp not in data_by_ap_bp[ap]:
            data_by_ap_bp[ap][bp] = {
                'times': 0,
                'tries': 0
            }

        event = data["events"][-1]
        eventName = event["name"]
        if str.upper(eventName) == "TRY":
            data_by_ap_bp[ap][bp]['tries'] += 1
        data_by_ap_bp[ap][bp]['times'] += 1

    print(f"[{idx + 1}/{filenames_len}] {filename} processed")

output_file_path = config['outputFilePath']
output_dir = output_file_path.rsplit('/', 1)[0]
gmt = time.gmtime()
timestamp = time.strftime('%Y-%m-%d_%H-%M-%S', gmt)
output_file = f'{output_file_path}_{timestamp}.json'

if not os.path.exists(output_dir):
    os.makedirs(output_dir)

with open(output_file, 'w') as f:
    json.dump(data_by_ap_bp, f, indent=4)

print(f"Analysis results saved to {output_file}")
