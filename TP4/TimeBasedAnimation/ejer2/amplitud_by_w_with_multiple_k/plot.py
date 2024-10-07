import json
import sys
import matplotlib.pyplot as plt
import numpy as np

if len(sys.argv) != 2:
    print("Usage: python plot.py <config>")
    sys.exit(1)

config = None
with open(sys.argv[1], 'r') as f:
    config = json.load(f)
    config = config['plot']

x = {}
y = {}

ks = []

max_x = {}
max_y = {}

input_file = config['inputFile']
with open(input_file, 'r') as f:
    data = json.load(f)
    keys_k = data.keys()
    keys_k_sorted = sorted(keys_k, key=lambda key: float(key))
    for key_k in keys_k_sorted:
        ks.append(key_k)
        data_by_k = data[key_k]
        keys_w = data_by_k.keys()
        keys_w_sorted = sorted(keys_w, key=lambda key: float(key))
        x[key_k] = []
        y[key_k] = []
        for key_w in keys_w_sorted:
            value_by_w = data_by_k[key_w]
            x[key_k].append(float(key_w))
            y[key_k].append(float(value_by_w))
            if key_k not in max_y:
                max_x[key_k] = float(key_w)
                max_y[key_k] = value_by_w
            elif value_by_w > max_y[key_k]:
                max_x[key_k] = float(key_w)
                max_y[key_k] = value_by_w


fontsize = 20

# Get a colormap
cmap = plt.get_cmap('tab10')  # You can choose different colormaps like 'viridis', 'plasma', etc.
colors = cmap(np.linspace(0, 1, len(ks)))  # Get distinct colors for each k

plt.figure(figsize=(10, 7))
plt.xlabel('w $(rad/s)$', fontsize=fontsize)
plt.ylabel('Amplitud máxima $(m)$', fontsize=fontsize)

# Loop through ks and use different colors for each
for idx, k in enumerate(ks):
    color = colors[idx]
    plt.plot(x[k], y[k], 'o', markersize=5, color=color, label=f'k={k}')  # Use color for different k
    plt.plot(max_x[k], max_y[k], 'ro')  # Still highlight the max points with red
    plt.axvline(max_x[k], color='r', linestyle='--', ymax=max_y[k], linewidth=1)

# Add a legend to differentiate k values
plt.legend(loc='best', fontsize=fontsize)

plt.xticks(fontsize=fontsize)
plt.yticks(fontsize=fontsize)
plt.ticklabel_format(axis='y', style='sci', scilimits=(0, 0), useMathText=True)
plt.show()

# Save figure
fig_file_name = config["outputFilePath"] + "fig.png"
plt.savefig(fig_file_name)
