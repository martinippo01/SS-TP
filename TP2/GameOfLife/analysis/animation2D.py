import matplotlib.pyplot as plt
import matplotlib.animation as animation
import json
import datetime
import numpy as np


# Function to load simulation data from JSON
def load_json(json_file):
    with open(json_file, 'r') as file:
        data = json.load(file)
    return data

# Load animation data
animation_data = load_json('./input.json')
# Load simulation data from JSON file
simulation_data = load_json(animation_data["input_file"])

# Extract grid boundaries and steps
border = simulation_data["border"]
min_x, max_x = border["x.min"], border["x.max"]
min_y, max_y = border["y.min"], border["y.max"]
steps = simulation_data['liveCellsForStep']

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
        rect = plt.Rectangle((x, y), 1, 1, edgecolor='black', facecolor='black')
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
ax.grid(which='both', linestyle='--', linewidth=0.5)

# Add grid, labels, and show plot

plt.title('Live Cells Animation')

# Create animation
ani = animation.FuncAnimation(fig, update, frames=len(steps), interval=animation_data["interval"], blit=True)

# Save and show the animation
# ani.save(filename="pillow_example.gif", writer="pillow")
ani.save(filename=animation_data["output_dir"] + "_" + datetime.datetime.now().strftime("%Y%m%d_%H%M%S") + "/animation.html", writer="html")
plt.show()
