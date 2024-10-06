import concurrent.futures
import json
import os
import sys
import time

def get_max_amplitude(data):
    w = data['params']['w']
    print(f'w={w} - Processing results')
    steps = data['steps']
    max_amplitude = 0
    for step in steps:
        positions = step['positions']
        for position in positions:
            amplitude = abs(position['y'])
            max_amplitude = max(max_amplitude, amplitude)
    print(f'w={w} - Max amplitude: {max_amplitude}')
    return (w, max_amplitude)


if len(sys.argv) != 2:
    print("Usage: python analysis.py <config>")
    sys.exit(1)

config = None
with open(sys.argv[1], 'r') as f:
    config = json.load(f)
    config = config['analysis']

output_data = {}
input_dir = config['inputDir']

pool = concurrent.futures.ThreadPoolExecutor(max_workers=5)
futures = []
for f in os.listdir(input_dir):
    file_path = os.path.join(input_dir, f)
    with open(file_path, 'r') as f:
        data = json.load(f)
        future = pool.submit(get_max_amplitude, data)
        futures.append(future)
    futures.append(future)

for future in concurrent.futures.as_completed(futures):
    w, max_amplitude = future.result()
    output_data[w] = max_amplitude

output_file_path = config['outputFilePath']
output_dir = output_file_path.rsplit('/', 1)[0]
gmt = time.gmtime()
timestamp = time.strftime('%Y-%m-%d_%H-%M-%S', gmt)
output_file = f'{output_file_path}_{timestamp}.json'

if not os.path.exists(output_dir):
    os.makedirs(output_dir)

with open(output_file, 'w') as f:
    json.dump(output_data, f)

print(f"Resonance data saved to {output_file}")



