import json
import sys
import matplotlib.pyplot as plt
import numpy as np

if len(sys.argv) != 2:
    print("Usage: python plot.py <config>")
    sys.exit(1)

# Load the config file
with open(sys.argv[1], 'r') as f:
    config = json.load(f)
    config = config['plot']

# Load the input data file
input_file = config['inputFile']
with open(input_file, 'r') as f:
    data = json.load(f)

# Initialize a colormap
ks = sorted(data.keys(), key=lambda k: float(k))  # Sort k values numerically
cmap = plt.get_cmap('tab10')  # Get a colormap
colors = cmap(np.linspace(0, 1, len(ks)))  # Get distinct colors for each k

plt.figure(figsize=(10, 7))

# Loop through each k and plot the corresponding (x, y) points in a different color
for idx, k in enumerate(ks):
    x = []
    y = []
    data_by_k = data[k]

    for key_w, value_by_w in data_by_k.items():
        x.append(float(key_w))  # Append w values to x
        y.append(float(value_by_w))  # Append corresponding y values

    # Find the maximum y value and its corresponding x (w)
    max_y_value = max(y)
    max_y_index = y.index(max_y_value)
    max_x_value = x[max_y_index]

    # Plot the points for this k value with a distinct color
    plt.scatter(x, y, color=colors[idx], label=f'k={k}', s=50)  # s=50 sets the marker size

    # Mark the maximum y point with a red color and add a vertical dotted line
    plt.scatter(max_x_value, max_y_value, color='red', s=100, edgecolor='black')
    plt.axvline(max_x_value, color='red', linestyle='--', linewidth=1)  # Dotted vertical line at max x

# Customize the plot
plt.xlabel('w $(rad/s)$', fontsize=16)
plt.ylabel('Amplitud $(m)$', fontsize=16)
plt.legend(loc='best', fontsize=12)  # Add a legend to differentiate k values

# Display the plot
plt.show()

# Save the figure if specified in config
fig_file_name = config["outputFilePath"] + "fig.png"
plt.savefig(fig_file_name)
