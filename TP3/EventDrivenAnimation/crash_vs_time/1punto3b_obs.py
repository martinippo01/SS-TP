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
temperatures = [0.25, 1, 4, 9, 16, 25]

# Plot
plt.figure(figsize=(10, 6))
plt.errorbar(temperatures, means, yerr=stds, fmt='o', capsize=5, capthick=2, markersize=4, color='blue', ecolor='red')
plt.xlabel('$Temperatura\ (v^2)$', fontsize=20)
plt.ylabel('Pendiente (1/s)', fontsize=20)
plt.xticks(temperatures, ['0.25' if temp == 0.25 else str(int(temp)) for temp in temperatures], fontsize=20)
plt.gca().get_xticklabels()[0].set_rotation(90)
plt.yticks(fontsize=20)

# Show plot
plt.tight_layout()
plt.show()

