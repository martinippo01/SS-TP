import json
import matplotlib.pyplot as plt

# Load JSON data from the file
with open("mean_and_std.json", "r") as file:
    data = json.load(file)

# Prepare data for plotting
temperatures = [0.25, 1, 4, 9, 16, 25]
means = [item['mean'] for item in data.values()]
stds = [item['std'] for item in data.values()]

# Plot
plt.figure(figsize=(10, 6))
plt.errorbar(temperatures, means, yerr=stds, fmt='o', capsize=5, capthick=2, markersize=8, color='blue', ecolor='red')
plt.xlabel('Temperatura $(v^2)$', fontsize=15)
plt.ylabel('Tiempo (s)', fontsize=16)
plt.xticks(temperatures, ['0.25' if temp == 0.25 else str(int(temp)) for temp in temperatures], fontsize=20)
plt.gca().get_xticklabels()[0].set_rotation(90)
plt.xticks(fontsize=15)
plt.yticks(fontsize=15)
plt.tight_layout()

plt.show()
