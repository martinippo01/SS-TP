import json
import matplotlib.pyplot as plt
import numpy as np

# Load JSON data from a file
with open("1punto3b_min_errores.json", "r") as file:
    dynamic_data = json.load(file)

data = {}

# Extracting the 'c_value' from the dynamic_data and organizing it
for key, c_data in dynamic_data.items():
    c_values = [entry["c_value"] for entry in c_data.values()]
    data[key] = c_values

# Prepare data for plotting
means = [np.mean(values) for values in data.values()]
stds = [np.std(values) for values in data.values()]
labels = [0.5, 1, 2, 3, 4, 5]

# Plot
plt.figure(figsize=(10, 6))
plt.errorbar(labels, means, yerr=stds, fmt='o', capsize=5, capthick=2, markersize=8, color='blue', ecolor='red')
# TODO: Cambiar las palabras y eso
plt.title('Observable 1.3b pendiente de la slope')
plt.xlabel('Parameter')
plt.ylabel('Mean c_value')
plt.xticks(rotation=45)

# Show plot
plt.tight_layout()
plt.show()

