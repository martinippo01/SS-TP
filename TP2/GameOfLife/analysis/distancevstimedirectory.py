import json
import sys
import os
import matplotlib.pyplot as plt
import math


# Uso: python3 distancevstimedirectory.py ../output/


def pythagorean_distance(x, y, center_x, center_y):
    return math.sqrt((x - center_x) ** 2 + (y - center_y) ** 2)


directory = sys.argv[1]

for filename in os.listdir(directory):
    if filename.endswith(".json"):
        filepath = os.path.join(directory, filename)

        # Load the JSON data from the file
        with open(filepath, 'r') as config_file:
            data = json.load(config_file)

        # Extract the 'liveCellsForStep' array
        live_cells_for_step = data["liveCellsForStep"]

        # Calculate the center of the grid
        center_x = (data["border"]["x.min"] + data["border"]["x.max"]) / 2
        center_y = (data["border"]["y.min"] + data["border"]["y.max"]) / 2

        # Calculate the maximum distance for each step
        max_distances = []
        for step in live_cells_for_step:
            max_distance = max(pythagorean_distance(cell["x"], cell["y"], center_x, center_y) for cell in step)
            max_distances.append(max_distance)

        # Plot the data
        plt.plot(max_distances, marker='o', label=filename)

plt.title('Maximum Pythagorean Distance from Center vs. Step for Each JSON')
plt.xlabel('Step')
plt.ylabel('Maximum Distance from Center')
plt.grid(True)
plt.legend()
plt.show()
