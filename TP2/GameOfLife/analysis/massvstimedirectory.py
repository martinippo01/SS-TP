import matplotlib.pyplot as plt
import json
import os
import sys

# Uso: python3 massvstimedirectory.py ../output/

directory = sys.argv[1]

all_cell_counts = []
max_steps = 0

for filename in os.listdir(directory):
    if filename.endswith('.json'):
        with open(os.path.join(directory, filename), 'r') as config_file:
            data = json.load(config_file)

            # Extract the 'liveCellsForStep' array
            live_cells_for_step = data["liveCellsForStep"]
            length_current = len(live_cells_for_step)

            if length_current > max_steps:
                max_steps = length_current

            # Calculate the number of live cells at each step
            cell_counts = [len(step) for step in live_cells_for_step]
            all_cell_counts.append(cell_counts)

# Plot the data from each file
for i, cell_counts in enumerate(all_cell_counts):
    plt.plot(cell_counts, marker='o', label=f'Simulación #{i + 1}')

plt.xticks(range(0, max_steps + 1, 1))
plt.xlabel('Paso temporal (pasos)')
plt.ylabel('Células vivas (unidades)')
plt.legend()
plt.show()
