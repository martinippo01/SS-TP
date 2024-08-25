import sys
import json
import matplotlib.pyplot as plt
import math

# Pasarle directamente el result_2024...
with open(sys.argv[1], 'r') as config_file:
    data = json.load(config_file)

# Extract the 'liveCellsForStep' array
live_cells_for_step = data["liveCellsForStep"]

# Calculate the center of the grid
center_x = (data["border"]["x.min"] + data["border"]["x.max"]) / 2
center_y = (data["border"]["y.min"] + data["border"]["y.max"]) / 2


# Function to calculate the distance from the center
def pythagorean_distance(x, y):
    return math.sqrt((x - center_x) ** 2 + (y - center_y) ** 2)


# Calculate the maximum distance for each step
max_distances = []
for step in live_cells_for_step:
    max_distance = max(pythagorean_distance(cell["x"], cell["y"]) for cell in step)
    max_distances.append(max_distance)

# Plot the data
plt.plot(max_distances, marker='o')
plt.title('Maximum Pythagorean Distance from Center vs. Step')
plt.xlabel('Step')
plt.ylabel('Maximum Distance from Center')
plt.grid(True)
plt.show()
