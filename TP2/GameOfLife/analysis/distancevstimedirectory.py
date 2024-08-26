import json
import sys
import os
import matplotlib.pyplot as plt
import math


# Uso: python3 distancevstimedirectory.py ../output/


def pythagorean_distance(x, y, z, center_x, center_y, center_z):
    return math.sqrt((x - center_x) ** 2 + (y - center_y) ** 2 + (z - center_z) ** 2)


directory = sys.argv[1]
i = 0

for filename in os.listdir(directory):
    if filename.endswith(".json"):
        filepath = os.path.join(directory, filename)

        # Load the JSON data from the file
        with open(filepath, 'r') as config_file:
            data = json.load(config_file)

        # Extract the 'liveCellsForStep' array
        live_cells_for_step = data["liveCellsForStep"]

        # Extract the 'is3d' flag
        is_3d = data["is3D"]

        # Calculate the center of the grid
        center_x = (data["border"]["x.min"] + data["border"]["x.max"]) / 2
        center_y = (data["border"]["y.min"] + data["border"]["y.max"]) / 2
        center_z = (data["border"]["z.min"] + data["border"]["z.max"]) / 2 if is_3d else 0

        # Calculate the maximum distance for each step
        max_distances = []
        for step in live_cells_for_step:
            max_distance = max(pythagorean_distance(cell["x"], cell["y"], cell["z"] if is_3d else 0, center_x, center_y, center_z) for cell in step)
            max_distances.append(max_distance)

        # Plot the data
        plt.plot(max_distances, marker='o', label=f'Simulación #{i+1}')
        i += 1

plt.xlabel('Paso temporal (pasos)')
plt.ylabel('Distancia máxima al centro (celdas)')
plt.legend()
plt.show()
