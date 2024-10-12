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
step = 1 / config['count']
f_x = np.arange(min_f_x, max_f_x, step)
f_y = [c * np.sqrt(k) for k in f_x]

fontsize = 20
plt.figure(figsize=(12, 8))
plt.plot(x, y, 'o', markersize=10)
plt.plot(f_x, f_y, linewidth=2, color='red', label='$w_0 = $' + str(c) + '$\cdot \sqrt{k}$')
plt.xlabel('Constante elástica $(kg/s^2)$', fontsize=fontsize)
plt.ylabel('Frecuencia de resonancia $(1/s)$', fontsize=fontsize)
plt.xticks(x, fontsize=fontsize)
plt.yticks(fontsize=fontsize)
plt.legend(fontsize=fontsize)
plt.show()
