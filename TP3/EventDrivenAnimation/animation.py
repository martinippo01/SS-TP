import json
import plotly.graph_objs as go

# Load the JSON data
with open('input/1725755086592_dynamic.json', 'r') as file:
    dynamic_data = json.load(file)

with open('input/1725755086592_static.json', 'r') as file:
    static_data = json.load(file)

# Extract simulation parameters
L = static_data['inputData']['L']
obstacle = static_data['inputData']['obstacles'][0]

# Obstacle properties
obstacle_x = obstacle['position']['x']
obstacle_y = obstacle['position']['y']
obstacle_radius = obstacle['radius']

# Initial particle positions from the first event
particles = dynamic_data['events'][0]['particles']
x_positions = [p['position']['x'] for p in particles]
y_positions = [p['position']['y'] for p in particles]

# Create the figure
fig = go.Figure()

# Add the scatter plot for particles
fig.add_trace(go.Scatter(x=x_positions, y=y_positions, mode='markers',
                         marker=dict(size=10, color='blue'), name="Particles"))

# Add the obstacle as a shape
fig.add_shape(type="circle",
              x0=obstacle_x - obstacle_radius, y0=obstacle_y - obstacle_radius,
              x1=obstacle_x + obstacle_radius, y1=obstacle_y + obstacle_radius,
              line_color="red")

# Prepare frames for each event in the dynamic JSON
frames = []
for i, event in enumerate(dynamic_data['events']):
    x_positions = [p['position']['x'] for p in event['particles']]
    y_positions = [p['position']['y'] for p in event['particles']]
    frames.append(go.Frame(data=[go.Scatter(x=x_positions, y=y_positions)],
                           name=f"Frame {i+1}"))

# Update the layout with play/pause buttons
fig.update_layout(
    xaxis=dict(range=[0, L], autorange=False),
    yaxis=dict(range=[0, L], autorange=False),
    title="Particle Simulation (Frame-by-Frame)",
    updatemenus=[dict(type="buttons", showactive=False,
                      buttons=[dict(label="Play",
                                    method="animate",
                                    args=[None, {"frame": {"duration": 10, "redraw": True},
                                                 "fromcurrent": True, "mode": "immediate"}]),
                               dict(label="Pause",
                                    method="animate",
                                    args=[[None], {"frame": {"duration": 0, "redraw": True},
                                                   "mode": "immediate"}])])])

# Add frames to the figure
fig.frames = frames

# Show plot
fig.show()
