import matplotlib.pyplot as plt
import matplotlib.animation as animation
import json
import numpy as np


# Function to load simulation data from JSON
def load_simulation_data(json_file):
    with open(json_file, 'r') as file:
        data = json.load(file)
    return data


# Load simulation data from JSON file
simulation_data = load_simulation_data('./results/1.json')

# Extract grid boundaries and steps
min_x, max_x = simulation_data['min_x'], simulation_data['max_x']
min_y, max_y = simulation_data['min_y'], simulation_data['max_y']
steps = simulation_data['steps']

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
    step_data = steps[frame]
    live_cells = step_data['live_cells']
    initialize_rectangles(live_cells)
    return rectangles


# Set limits for better visualization
plt.xlim(min_x - 1, max_x + 1)
plt.ylim(min_y - 1, max_y + 1)

# Set grid lines to be at every integer
ax.set_xticks(np.arange(min_x - 1, max_x + 2, 1))
ax.set_yticks(np.arange(min_y - 1, max_y + 2, 1))
ax.grid(which='both', linestyle='--', linewidth=0.5)

# Add grid, labels, and show plot

plt.title('Live Cells Animation')

# Create animation
ani = animation.FuncAnimation(fig, update, frames=len(steps), interval=500, blit=True)

# Save and show the animation
# ani.save(filename="pillow_example.gif", writer="pillow")
ani.save(filename="./animation/html_example.html", writer="html")
plt.show()
