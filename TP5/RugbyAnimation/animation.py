import json
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# Load JSON data
with open("output.json") as f:
    data = json.load(f)

# Parse data from JSON
steps = data["steps"]
n_frames = len(steps)

# Separate particles into two sets based on their ID
# (For simplicity, we can assume particles with even IDs are Set 1, odd IDs are Set 2)
runner_positions = [[(p["x"], p["y"]) for p in frame["particles"] if p["id"] == 0] for frame in steps]
pursuer_positions = [[(p["x"], p["y"]) for p in frame["particles"] if p["id"] != 0] for frame in steps]

# Initialize plot
fig, ax = plt.subplots()
scatter_runner = ax.scatter([], [], color='red', label="Set 1")
scatter_pursuer = ax.scatter([], [], color='blue', label="Set 2")
ax.set_xlim(0, 1)
ax.set_ylim(0, 1)
ax.legend()


# Update function for animation
def update(frame):
    scatter_runner.set_offsets(runner_positions[frame])
    scatter_pursuer.set_offsets(pursuer_positions[frame])


# Create animation
ani = FuncAnimation(fig, update, frames=n_frames, interval=50)
plt.show()
