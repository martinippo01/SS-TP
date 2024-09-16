import json
import plotly.graph_objs as go

# Load the JSON data
with open('./input/dynamic.json', 'r') as file:
    dynamic_data = json.load(file)

with open('./input/static.json', 'r') as file:
    static_data = json.load(file)

velocities_n_wall = []
velocities_n_obstacle = []

for i, event in enumerate(dynamic_data['events']):
    if i == 0 or event['event'] == "PARTICLES":
        continue
    ids: [] = event['particlesCrashed']
    for j, particle in enumerate(event['particles']):
        if particle['id'] in ids:
            if event['event'] == "OBSTACLE":
                velocities_n_obstacle.append(particle['velocity'])
            if event['event'] == "WALL":
                velocities_n_wall.append(particle['velocity']['vX'])




def get_vn_obstacle():
    deltaVX =

def get_vn_particles():

