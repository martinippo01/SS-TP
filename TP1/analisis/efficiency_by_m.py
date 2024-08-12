import json
import matplotlib.pyplot as plt

# Path to the output.json file
input_file = 'output.json'

# Read the JSON file
with open(input_file, 'r') as file:
    data = json.load(file)

# Extract the 'M' values into list x and 'avg_time' values into list y
x = [entry['M'] for entry in data]
y = [entry['avg_time'] for entry in data]
yerr = [entry['std'] for entry in data]  # Extract the 'std' values

# Plotting the data with error bars
plt.figure(figsize=(10, 5))
plt.errorbar(x, y, yerr=yerr, fmt='o', ecolor='r', capsize=5, capthick=2)

# Add labels to the axes
plt.xlabel('M Values')
plt.ylabel('Average Time (with Standard Deviation)')

# Add title to the graph
plt.title('Graph of M vs Time with Standard Deviation')

# Add labels to the tick marks on the axes
plt.xticks(x)

# Display the graph
plt.show()
