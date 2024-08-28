import plotly.graph_objects as go
import numpy as np
import json
import datetime
import math
import sys


# Function to load simulation data from JSON
def load_json(json_file):
    with open(json_file, 'r') as file:
        data = json.load(file)
    return data


def cell_color_by_distance(x, y, z):
    distance = math.sqrt((x - center_x) ** 2 + (y - center_y) ** 2 + (z - center_z) ** 2)
    normalized_distance = distance / max_distance
    return f'rgb({int(normalized_distance * 255)}, {int((1 - normalized_distance) * 255)}, 0)'


if len(sys.argv) < 2:
    print("Usage: python3 animation2D.py <input.json>")
    sys.exit(1)

# Load animation data
animation_data = load_json(sys.argv[1])
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

# Create an empty list to store animation frames
frames = []

# Loop through each step and create scatter points for each voxel
for i, step in enumerate(steps):
    x, y, z, colors = [], [], [], []

    for cell in step:
        x_, y_, z_ = int(cell['x']), int(cell['y']), int(cell['z'])
        if min_x <= x_ < max_x and min_y <= y_ < max_y and min_z <= z_ < max_z:
            x.append(x_)
            y.append(y_)
            z.append(z_)
            colors.append(cell_color_by_distance(x_, y_, z_))

    # Add the frame for the current step
    frames.append(go.Frame(
        data=[go.Scatter3d(
            x=x,
            y=y,
            z=z,
            mode='markers',
            marker=dict(size=5, color=colors)
        )],
        name=f"Frame {i}"  # Name each frame for slider labels
    ))

# Set up the initial figure
initial_data = frames[0].data[0]  # Start with the first frame

# Set up sliders for manual control
sliders = [dict(
    active=0,
    currentvalue={"prefix": "Step: "},
    pad={"b": 10},
    steps=[dict(
        method="animate",
        args=[[f"Frame {i}"],
              dict(frame=dict(duration=animation_data["interval"], redraw=True),
                   mode="immediate")],
        label=str(i)) for i in range(len(frames))]
)]

# Set up the figure layout
fig = go.Figure(
    data=[initial_data],
    layout=go.Layout(
        sliders=sliders,
        updatemenus=[dict(type="buttons", showactive=False,
                          buttons=[dict(label="Play",
                                        method="animate",
                                        args=[None, dict(frame=dict(duration=animation_data["interval"], redraw=True),
                                                         fromcurrent=True, mode='immediate')]),
                                   dict(label="Pause",
                                        method="animate",
                                        args=[[None], dict(frame=dict(duration=0, redraw=False),
                                                           mode='immediate')])])],
        scene=dict(
            xaxis=dict(range=[min_x, max_x], autorange=False),
            yaxis=dict(range=[min_y, max_y], autorange=False),
            zaxis=dict(range=[min_z, max_z], autorange=False)
        ),
        title="3D Voxel Animation"
    ),
    frames=frames
)

# Show the figure and save it as an HTML file
output_file = animation_data["output_dir"] + datetime.datetime.now().strftime("%Y%m%d%H%M%S") + "_animation.html"
fig.write_html(output_file)
fig.show()
