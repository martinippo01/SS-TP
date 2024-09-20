import json
import numpy as np
import matplotlib.pyplot as plt

# Load data from JSON file
with open("1punto3b_min_errores.json", "r") as file:
    dynamic_data = json.load(file)

data = {}

# Extracting the 'c_value' from the dynamic_data and organizing it
for key, c_data in dynamic_data.items():
    c_values = [entry["c_value"] for entry in c_data.values()]
    data[key] = c_values

# Prepare data for plotting
pendientes = [np.mean(values) for values in data.values()]

# Define colors and linestyles
colors = ['b', 'r', 'g', 'y', 'c', 'm']
my_labels = ['T = 0.25 $v^2$', 'T = 1 $v^2$', 'T = 4 $v^2$', 'T = 9 $v^2$', 'T = 16 $v^2$', 'T = 25 $v^2$']

# Plot each line with a unique color and linestyle
for i, pend in enumerate(pendientes):
    plt.axline((0, 0), slope=pend, color=colors[i % len(colors)],
               linestyle='-.', label=my_labels[i])

# Set limits for x and y axes
plt.xlim(0, 5)  # Adjust these values based on your data range
plt.ylim(0, 14000)  # Adjust these values based on your data range

# Set labels with fontsize
plt.xlabel('$Tiempo\ (s)$', fontsize=15)
plt.ylabel('Cantidad Acumulativa de Choques', fontsize=15)

# Set font size for ticks
plt.tick_params(axis='both', labelsize=15)

# Show legend with fontsize
plt.legend(fontsize=14)
plt.tight_layout()
plt.show()