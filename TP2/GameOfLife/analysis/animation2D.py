import matplotlib.pyplot as plt
import matplotlib.animation as animation
import json
import datetime
import math
import numpy as np
import sys


# Function to load simulation data from JSON
def load_json(json_file):
    with open(json_file, 'r') as file:
        data = json.load(file)
    return data


if len(sys.argv) < 2:
    print("Usage: python3 animation2D.py <input.json>")
    sys.exit(1)

# Load animation data
animation_data = load_json(sys.argv[1])
# Load simulation data from JSON file
simulation_data = load_json(animation_data["input_file"])

# Extract grid boundaries and steps
border = simulation_data["border"]
min_x, max_x = border["x.min"], border["x.max"]
min_y, max_y = border["y.min"], border["y.max"]
center_x = (max_x - min_x) / 2
center_y = (max_y - min_y) / 2
max_distance = math.sqrt((center_x - max_x) ** 2 + (center_y - max_y) ** 2)
steps = simulation_data['liveCellsForStep']


def cell_color_by_distance(x, y):
    distance = math.sqrt((x - center_x) ** 2 + (y - center_y) ** 2)
    return ((distance / max_distance), 1 - (distance / max_distance), 0)


# Create the plot
fig, ax = plt.subplots()

# Initialize rectangles list
rectangles = []


# Function to initialize rectangles for the current step
def initialize_rectangles(live_cells):
    global rectangles
    # Clear existing rectangles
    for rect in rectangles:
        rect.remove()
    rectangles = []
    # Add new rectangles
    for cell in live_cells:
        x = cell['x']
        y = cell['y']
        color = cell_color_by_distance(x, y)
        rect = plt.Rectangle((x, y), 1, 1, edgecolor=color, facecolor=color)
        ax.add_patch(rect)
        rectangles.append(rect)


# Animation update function
def update(frame):
    # Get live cells for the current step
    initialize_rectangles(steps[frame])
    return rectangles


# Set limits for better visualization
plt.xlim(min_x - 1, max_x + 1)
plt.ylim(min_y - 1, max_y + 1)

# Set grid lines to be at every integer
# ax.set_xticks(np.arange(min_x - 1, max_x + 2, 1))
# ax.set_yticks(np.arange(min_y - 1, max_y + 2, 1))
# ax.grid(which='both', linestyle='--', linewidth=0.5)

# Add grid, labels, and show plot

plt.title('Live Cells Animation')

# Create animation
ani = animation.FuncAnimation(fig, update, frames=len(steps), interval=animation_data["interval"], blit=True)

# Save and show the animation
# ani.save(filename="pillow_example.gif", writer="pillow")
ani.save(filename=animation_data["output_dir"] + "" + datetime.datetime.now().strftime("%Y%m%d%H%M%S") + "/animation.html", writer="html")
plt.show()
