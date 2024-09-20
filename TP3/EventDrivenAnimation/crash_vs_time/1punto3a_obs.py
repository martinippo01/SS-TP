import json
import matplotlib.pyplot as plt

# Load JSON data from the file
with open("mean_and_std.json", "r") as file:
    data = json.load(file)

# Prepare data for plotting
labels = [0.5, 1, 2, 3, 4, 5]
means = [item['mean'] for item in data.values()]
stds = [item['std'] for item in data.values()]

# Plot
plt.figure(figsize=(10, 6))
plt.errorbar(labels, means, yerr=stds, fmt='o', capsize=5, capthick=2, markersize=8, color='blue', ecolor='red')
plt.xlabel('Temperatura $(V^2)$')
plt.ylabel('Tiempo (s)')
plt.xticks(rotation=45)

# Show plot
plt.tight_layout()
plt.show()
