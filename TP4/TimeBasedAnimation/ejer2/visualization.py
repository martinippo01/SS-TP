import json
import plotly.graph_objs as go
from plotly.subplots import make_subplots

# Load JSON data from a file
with open('../../TimeBasedSimulation/outputs/coupled/gear5/2gear.json') as f:
    data = json.load(f)

# Extract time steps and positions from the JSON data
time_steps = [step["time"] for step in data["steps"]]
positions_data = [step["positions"] for step in data["steps"]]

# Create a figure with initial points
fig = go.Figure()

# Create traces for the initial positions
x_values = [pos['x'] for pos in positions_data[0]]
y_values = [pos['y'] for pos in positions_data[0]]

# Add scatter plot for the initial positions
scatter = go.Scatter(x=x_values, y=y_values, mode='markers', marker=dict(size=6), name='Time: 0.05')

# Add initial trace
fig.add_trace(scatter)

# Create frames for animation
frames = []
for i, time in enumerate(time_steps):
    x_values = [pos['x'] for pos in positions_data[i]]
    y_values = [pos['y'] for pos in positions_data[i]]
    frames.append(go.Frame(data=[go.Scatter(x=x_values, y=y_values, mode='markers', marker=dict(size=6))],
                           name=f'Time: {time}'))

# Set up the layout for the animation
fig.update_layout(
    title="Animated Scatter Plot",
    xaxis=dict(title='X', range=[0, 0.12]),
    yaxis=dict(title='Y', range=[-2,2]),
    updatemenus=[{
        "buttons": [
            {
                "args": [None, {"frame": {"duration": 500, "redraw": True}, "fromcurrent": True}],
                "label": "Play",
                "method": "animate"
            },
            {
                "args": [[None], {"frame": {"duration": 0, "redraw": False}, "mode": "immediate", "transition": {"duration": 0}}],
                "label": "Pause",
                "method": "animate"
            }
        ],
        "direction": "left",
        "pad": {"r": 10, "t": 87},
        "showactive": False,
        "type": "buttons",
        "x": 0.1,
        "xanchor": "right",
        "y": 0,
        "yanchor": "top"
    }],
    sliders=[{
        "active": 0,
        "yanchor": "top",
        "xanchor": "left",
        "currentvalue": {
            "font": {"size": 20},
            "prefix": "Time:",
            "visible": True,
            "xanchor": "right"
        },
        "transition": {"duration": 300, "easing": "cubic-in-out"},
        "pad": {"b": 10, "t": 50},
        "len": 0.9,
        "x": 0.1,
        "y": 0,
        "steps": [{
            "args": [[f'Time: {time}'], {"frame": {"duration": 300, "redraw": True}, "mode": "immediate", "transition": {"duration": 300}}],
            "label": str(time),
            "method": "animate"
        } for time in time_steps]
    }]
)

# Add the frames to the figure
fig.frames = frames

# Show the figure
fig.show()




