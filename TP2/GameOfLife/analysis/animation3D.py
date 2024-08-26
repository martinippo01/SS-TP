# import matplotlib.pyplot as plt
# import numpy as np
# from matplotlib.animation import FuncAnimation
# import json
# import datetime
# import math
#
#
# # Function to load simulation data from JSON
# def load_json(json_file):
#     with open(json_file, 'r') as file:
#         data = json.load(file)
#     return data
#
#
# def cell_color_by_distance(x, y, z):
#     distance = math.sqrt((x - center_x) ** 2 + (y - center_y) ** 2 + (z - center_z) ** 2)
#     return ((distance / max_distance), 1 - (distance / max_distance), 0)
#
#
# # Load animation data
# animation_data = load_json('./input.json')
# # Load simulation data from JSON file
# simulation_data = load_json(animation_data["input_file"])
#
# # Extract grid boundaries and steps
# border = simulation_data["border"]
# min_x, max_x = border["x.min"], border["x.max"]
# min_y, max_y = border["y.min"], border["y.max"]
# min_z, max_z = border["z.min"], border["z.max"]
# center_x = (max_x - min_x) / 2
# center_y = (max_y - min_y) / 2
# center_z = (max_z - min_z) / 2
# max_distance = math.sqrt((center_x - max_x) ** 2 + (center_y - max_y) ** 2 + (center_z - max_z) ** 2)
# steps = simulation_data['liveCellsForStep']
#
#
# # Initialize figure and 3D axis
# fig = plt.figure()
# ax = fig.add_subplot(projection='3d')
#
# # Prepare some initial empty arrays
# x, y, z = np.indices((max_x - min_x, max_y - min_y, max_z - min_z))
# colors = np.empty((max_x - min_x, max_y - min_y, max_z - min_z), dtype=object)
#
# # Function to update the voxels at each frame
# def update(frame):
#     # Clear the previous voxels
#     ax.clear()
#
#     # Reset colors
#     colors.fill(None)
#
#     # Get the current voxel positions from the frame
#     current_voxels = steps[frame]
#
#     # Create a new boolean voxel array and update colors
#     voxelarray = np.zeros((max_x - min_x, max_y - min_y, max_z - min_z), dtype=bool)
#     for (x_, y_, z_) in current_voxels:
#         voxelarray[x_, y_, z_] = True
#         colors[x_, y_, z_] = cell_color_by_distance(x_, y_, z_)
#
#     # Draw the updated voxel array
#     ax.voxels(voxelarray, facecolors=colors, edgecolor='k')
#
#
# # Create the animation
# ani = FuncAnimation(fig, update, frames=len(steps), repeat=True)
# ani.save(filename=animation_data["output_dir"] + "" + datetime.datetime.now().strftime("%Y%m%d%H%M%S") + "/animation.html", writer="html")
#
# plt.show()
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.animation import FuncAnimation
import json
import datetime
import math


# Function to load simulation data from JSON
def load_json(json_file):
    with open(json_file, 'r') as file:
        data = json.load(file)
    return data


def cell_color_by_distance(x, y, z):
    distance = math.sqrt((x - center_x) ** 2 + (y - center_y) ** 2 + (z - center_z) ** 2)
    return (distance / max_distance, 1 - (distance / max_distance), 0)


# Load animation data
animation_data = load_json('./input.json')
# Load simulation data from JSON file
simulation_data = load_json(animation_data["input_file"])

# Extract grid boundaries and steps
border = simulation_data["border"]
min_x, max_x = border["x.min"], border["x.max"]
min_y, max_y = border["y.min"], border["y.max"]
min_z, max_z = border["z.min"], border["z.max"]
center_x = (max_x - min_x) / 2
center_y = (max_y - min_y) / 2
center_z = (max_z - min_z) / 2
max_distance = math.sqrt((center_x - max_x) ** 2 + (center_y - max_y) ** 2 + (center_z - max_z) ** 2)
steps = simulation_data['liveCellsForStep']


# Initialize figure and 3D axis
fig = plt.figure()
ax = fig.add_subplot(projection='3d')

# Prepare some initial empty arrays
x, y, z = np.indices((max_x - min_x, max_y - min_y, max_z - min_z))
colors = np.empty((max_x - min_x, max_y - min_y, max_z - min_z), dtype=object)

# Function to update the voxels at each frame
def update(frame):
    # Clear the previous voxels
    ax.clear()

    # Reset colors
    colors.fill(None)

    # Get the current voxel positions from the frame
    current_voxels = steps[frame]

    # Create a new boolean voxel array and update colors
    voxelarray = np.zeros((max_x - min_x, max_y - min_y, max_z - min_z), dtype=bool)
    for cell in current_voxels:
        x_, y_, z_ = int(cell['x']), int(cell['y']), int(cell['z'])

        # Bounds checking to prevent out-of-range errors
        if min_x <= x_ < max_x and min_y <= y_ < max_y and min_z <= z_ < max_z:
            voxelarray[x_ - min_x, y_ - min_y, z_ - min_z] = True
            colors[x_ - min_x, y_ - min_y, z_ - min_z] = cell_color_by_distance(x_, y_, z_)

    # Draw the updated voxel array
    ax.voxels(voxelarray, facecolors=colors, edgecolor='k')


# Create the animation
ani = FuncAnimation(fig, update, frames=len(steps), repeat=True)
ani.save(filename=animation_data["output_dir"] + "" + datetime.datetime.now().strftime("%Y%m%d%H%M%S") + "/animation.html", writer="html")

plt.show()
