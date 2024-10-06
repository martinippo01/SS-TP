import json
import sys

import matplotlib.pyplot as plt
import numpy as np

if len(sys.argv) != 2:
    print("Usage: python result.py <json>")
    sys.exit(1)


config = None
with open(sys.argv[1], 'r') as f:
    config = json.load(f)
    config = config['result']

x = []
y = []

input_file = config['inputFile']
with open(input_file, 'r') as f:
    data = json.load(f)
    keys = data.keys()
    keys_sorted = sorted(keys, key=lambda key: float(key))
    for key in keys_sorted:
        value = data[key]
        x.append(float(key))
        y.append(value)

c = config['c']
min_f_x = config['min']
max_f_x = config['max']
count_f_x = config['count']
f_x = np.linspace(min_f_x, max_f_x, count_f_x).tolist()
f_y = [x_i ** c for x_i in f_x]

fontsize = 20
plt.figure(figsize=(10, 7))
plt.plot(x, y, 'o', markersize=5)
plt.plot(f_x, f_y, linewidth=2, color='red')
plt.xlabel('k $(kg/s^2)$', fontsize=fontsize)
plt.ylabel('w $(rad/s)$', fontsize=fontsize)
plt.xticks(fontsize=fontsize)
plt.yticks(fontsize=fontsize)
plt.show()