import json
import os
import sys
import time

import numpy


def calculate_error(f_x, f_y, c):
    error = 0
    for x, y in zip(f_x, f_y):
        f = x ** c
        error += (y - f) ** 2
    return error


if len(sys.argv) != 2:
    print("Usage: python analysis.py <json>")
    sys.exit(1)

config = None
with open(sys.argv[1], 'r') as f:
    config = json.load(f)
    config = config['analysis']

max_c = config['c']['max']
min_c = config['c']['min']
step_c = config['c']['step']
f_y = []
f_x = []
c_values = numpy.arange(min_c, max_c, step_c).tolist()

input_file = config['inputFile']
with open(input_file, 'r') as f:
    data = json.load(f)
    for key, value in data.items():
        f_x.append(float(key))
        f_y.append(value)

error_by_c = {}
for c in c_values:
    error = calculate_error(f_x, f_y, c)
    error_by_c[c] = error

output_file_path = config['outputFilePath']
output_dir = output_file_path.rsplit('/', 1)[0]
gmt = time.gmtime()
timestamp = time.strftime('%Y-%m-%d_%H-%M-%S', gmt)
output_file = f'{output_file_path}_{timestamp}.json'

if not os.path.exists(output_dir):
    os.makedirs(output_dir)

with open(output_file, 'w') as f:
    json.dump(error_by_c, f)

print(f"w_k_relation data saved to {output_file}")
