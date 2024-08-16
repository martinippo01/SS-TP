import json
import matplotlib.pyplot as plt

# Path to the output.json file
input_file = 'output.json'

# Read the JSON file
with open(input_file, 'r') as file:
    data = json.load(file)

# Extract the 'M' values into list x and 'avg_time' values into list y
data_sorted = sorted(data, key=lambda x: x['M'])
x = [entry['M'] for entry in data_sorted]
y = [entry['avg_time'] for entry in data_sorted]
yerr = [entry['std'] for entry in data_sorted]  # Extract the 'std' values

# Plotting the data with error bars
plt.figure(figsize=(10, 5))
plt.errorbar(x, y, yerr=yerr, fmt='o-', ecolor='r', capsize=5, capthick=2)

# Add labels to the axes
plt.xlabel('Valor de M (unidades)')
plt.ylabel('Tiempo (ms)')


# Add labels to the tick marks on the axes
plt.xticks(x)

# Display the graph
plt.ticklabel_format(axis='y', style='sci', scilimits=(0,0), useMathText=True)
plt.grid()
plt.xticks(range(1, 11))
plt.show()
