import concurrent.futures
import json
import os
import sys
import time

def get_max_amplitude(f_p):
    with open(f_p, 'r') as _f:
        max_amplitude = 0
        data = json.load(_f)
        w = data['params']['w']
        k = data['params']['k']
        print(f'w={w} k ={k} - Processing results')
        steps = data['steps']
        for step in steps:
            positions = step['positions']
            for position in positions:
                amplitude = abs(position['y'])
                max_amplitude = max(max_amplitude, amplitude)
        print(f'w={w} k ={k} - Max amplitude: {max_amplitude}')
        return (w, k, max_amplitude)


if len(sys.argv) != 2:
    print("Usage: python analysis.py <config>")
    sys.exit(1)

config = None
with open(sys.argv[1], 'r') as f:
    config = json.load(f)
    config = config['analysis']

output_data = {}
max_per_k={}
input_dir = config['inputDir']

pool = concurrent.futures.ThreadPoolExecutor(max_workers=2)
futures = []
for f in os.listdir(input_dir):
    file_path = os.path.join(input_dir, f)
    future = pool.submit(get_max_amplitude, file_path)
    futures.append(future)

for future in concurrent.futures.as_completed(futures):
    w, k, max_amplitude = future.result()
    if k not in output_data:
        output_data[k] = {}
    output_data[k][w] = max_amplitude
    # TODO this is wrong
    if k not in max_per_k:
        max_per_k[k] = max_amplitude
    else:
        max_per_k[k] = max(max_per_k[k], max_amplitude)

output_file_path = config['outputFilePath']
output_dir = output_file_path.rsplit('/', 1)[0]
gmt = time.gmtime()
timestamp = time.strftime('%Y-%m-%d_%H-%M-%S', gmt)
output_file_plot = f'{output_file_path}plot{timestamp}.json'
output_file_analysis = f'{output_file_path}analysis{timestamp}.json'

if not os.path.exists(output_dir):
    os.makedirs(output_dir)

with open(output_file_plot, 'w') as f:
    json.dump(output_data, f)

print(f"Plot data saved to {output_file_plot}")

with open(output_file_analysis, 'w') as f:
    json.dump(max_per_k, f)

print(f"Analysis data saved to {output_file_analysis}")