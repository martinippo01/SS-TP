# import json
# import plotly.graph_objs as go
#
# # Load the JSON data
# with open('./input/dynamic.json', 'r') as file:
#     dynamic_data = json.load(file)
#
# with open('./input/static.json', 'r') as file:
#     static_data = json.load(file)
#
# # Extract simulation parameters
# L = static_data['inputData']['L']
# obstacle = static_data['inputData']['obstacles'][0]
# is_circular = static_data['inputData']['planeType'] == 'CIRCULAR'
#
# # Obstacle properties
# obstacle_x = obstacle['position']['x']
# obstacle_y = obstacle['position']['y']
# obstacle_radius = obstacle['radius']
#
# # Initial particle positions and IDs from the first event
# particles = dynamic_data['events'][0]['particles']
# x_positions = [p['position']['x'] for p in particles]
# y_positions = [p['position']['y'] for p in particles]
# particle_ids = [p['id'] for p in particles]
#
# # Create the figure
# fig = go.Figure()
#
# # Add the scatter plot for particles with labels (IDs)
# fig.add_trace(go.Scatter(x=x_positions, y=y_positions, mode='markers+text',
#                          marker=dict(size=10, color='blue'), name="Particles",
#                          text=particle_ids, textposition='top center'))
#
# # Add the obstacle as a shape
# fig.add_shape(type="circle",
#               x0=obstacle_x - obstacle_radius, y0=obstacle_y - obstacle_radius,
#               x1=obstacle_x + obstacle_radius, y1=obstacle_y + obstacle_radius,
#               line_color="red")
#
# # Prepare frames for each event in the dynamic JSON
# frames = []
# for i, event in enumerate(dynamic_data['events']):
#     if is_circular:
#         x_positions = [p['position']['x'] + L/2 for p in event['particles']]
#         y_positions = [p['position']['y'] + L/2 for p in event['particles']]
#     else:
#         x_positions = [p['position']['x'] for p in event['particles']]
#         y_positions = [p['position']['y'] for p in event['particles']]
#     particle_ids = [p['id'] for p in event['particles']]
#     frames.append(go.Frame(data=[go.Scatter(x=x_positions, y=y_positions, text=particle_ids)],
#                            name=f"Frame {i+1}"))
#
# # Update the layout with play/pause buttons
# fig.update_layout(
#     xaxis=dict(range=[0, L], autorange=False),
#     yaxis=dict(range=[0, L], autorange=False),
#     title="Particle Simulation (Frame-by-Frame)",
#     updatemenus=[dict(type="buttons", showactive=False,
#                       buttons=[dict(label="Play",
#                                     method="animate",
#                                     args=[None, {"frame": {"duration": 0.1, "redraw": True},
#                                                  "fromcurrent": True, "mode": "immediate"}]),
#                                dict(label="Pause",
#                                     method="animate",
#                                     args=[[None], {"frame": {"duration": 0, "redraw": True},
#                                                    "mode": "immediate"}])])])
#
# # Add frames to the figure
# fig.frames = frames
#
# # Show plot
# fig.show()

import json
import plotly.graph_objs as go

# Load the JSON data
with open('./input/dynamic.json', 'r') as file:
    dynamic_data = json.load(file)

with open('./input/static.json', 'r') as file:
    static_data = json.load(file)

# Extract simulation parameters
L = static_data['inputData']['L']
obstacle = static_data['inputData']['obstacles'][0]
is_circular = static_data['inputData']['planeType'] == 'CIRCULAR'

# Obstacle properties
obstacle_x = obstacle['position']['x']
obstacle_y = obstacle['position']['y']
obstacle_radius = obstacle['radius']

# Initial particle positions and IDs from the first event
particles = dynamic_data['events'][0]['particles']
x_positions = [p['position']['x'] for p in particles]
y_positions = [p['position']['y'] for p in particles]
particle_ids = [p['id'] for p in particles]

# Create the figure
fig = go.Figure()

# Add the scatter plot for particles with labels (IDs)
fig.add_trace(go.Scatter(x=x_positions, y=y_positions, mode='markers+text',
                         marker=dict(size=10, color='blue'), name="Particles",
                         text=particle_ids, textposition='top center'))

# Add the obstacle as a shape
fig.add_shape(type="circle",
              x0=obstacle_x - obstacle_radius, y0=obstacle_y - obstacle_radius,
              x1=obstacle_x + obstacle_radius, y1=obstacle_y + obstacle_radius,
              line_color="red")

# Add an unfilled circle representing the LxL plane
if is_circular:
    fig.add_shape(type="circle",
                  x0=0, y0=0,
                  x1=L, y1=L,
                  line=dict(color='green', width=2),
                  fillcolor='rgba(0,0,0,0)')  # Transparent fill

# Prepare frames for each event in the dynamic JSON
frames = []
for i, event in enumerate(dynamic_data['events']):
    if is_circular:
        x_positions = [p['position']['x'] + L/2 for p in event['particles']]
        y_positions = [p['position']['y'] + L/2 for p in event['particles']]
    else:
        x_positions = [p['position']['x'] for p in event['particles']]
        y_positions = [p['position']['y'] for p in event['particles']]
    particle_ids = [p['id'] for p in event['particles']]
    frames.append(go.Frame(data=[go.Scatter(x=x_positions, y=y_positions, text=particle_ids)],
                           name=f"Frame {i+1}"))

# Update the layout with play/pause buttons
fig.update_layout(
    xaxis=dict(range=[0, L], autorange=False),
    yaxis=dict(range=[0, L], autorange=False),
    title="Particle Simulation (Frame-by-Frame)",
    updatemenus=[dict(type="buttons", showactive=False,
                      buttons=[dict(label="Play",
                                    method="animate",
                                    args=[None, {"frame": {"duration": 100, "redraw": True},
                                                 "fromcurrent": True, "mode": "immediate"}]),
                               dict(label="Pause",
                                    method="animate",
                                    args=[[None], {"frame": {"duration": 0, "redraw": True},
                                                   "mode": "immediate"}])])])

# Add frames to the figure
fig.frames = frames

# Export as an HTML with embedded animation (very efficient for large data)
fig.write_html('particle_simulation.html', auto_play=True)

# # Optional: Export as a GIF
# # Note: Plotly currently only supports GIF via 'orca' tool
# fig.write_image("particle_simulation.gif", format="gif")

# Show plot
fig.show()
