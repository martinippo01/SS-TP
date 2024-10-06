import json
import sys

import matplotlib.pyplot as plt

if len(sys.argv) != 2:
    print("Usage: python error.py <json>")
    sys.exit(1)

config = None
with open(sys.argv[1], 'r') as f:
    config = json.load(f)
    config = config['error']

x = []
y = []
min_error = None
best_c = 0

input_file = config['inputFile']
with open(input_file, 'r') as f:
    data = json.load(f)
    keys = data.keys()
    keys_sorted = sorted(keys, key=lambda key: float(key))
    for key in keys_sorted:
        value = data[key]
        key_value = float(key)
        x.append(key_value)
        y.append(value)
        if min_error is None or value < min_error:
            min_error = value
            best_c = key_value

fontsize = 20
plt.figure(figsize=(10, 7))
plt.plot(x, y, 'o', markersize=5)
plt.xlabel('Exponente', fontsize=fontsize)
plt.ylabel('Error', fontsize=fontsize)
plt.plot(best_c, min_error, 'ro')
plt.axvline(best_c, color='r', linestyle='--', ymax=min_error / max(y), linewidth=1)
plt.xticks(fontsize=fontsize)
plt.yticks(fontsize=fontsize)
plt.ticklabel_format(axis='y', style='sci', scilimits=(0,0), useMathText=True)
plt.show()