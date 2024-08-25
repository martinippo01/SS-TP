import matplotlib.pyplot as plt
import json
import sys


# Pasarle directamente el result_2024...

with open(sys.argv[1], 'r') as config_file:
    data = json.load(config_file)

# Extract the 'liveCellsForStep' array
live_cells_for_step = data["liveCellsForStep"]

# Calculate the number of live cells at each step
cell_counts = [len(step) for step in live_cells_for_step]

# Plot the data
plt.plot(cell_counts, marker='o')
plt.title('Number of Live Cells vs. Step')
plt.xlabel('Step')
plt.ylabel('Number of Live Cells')
plt.show()

