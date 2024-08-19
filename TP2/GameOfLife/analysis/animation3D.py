import json
import numpy as np
import pandas as pd
from matplotlib import pyplot as plt
import matplotlib.animation as animation
from mpl_toolkits.mplot3d.art3d import Poly3DCollection


# Function to load simulation data from JSON
def load_simulation_data(json_file):
    with open(json_file, 'r') as file:
        data = json.load(file)
    return data


# Load simulation data from JSON file
simulation_data = load_simulation_data('./simulation_results/2.json')

# Extract steps
steps = simulation_data['steps']

# Prepare the DataFrame
data = []
for step in steps:
    step_num = step['step']
    for cell in step['live_cells']:
        data.append({
            'time': step_num,
            'x': cell['x'],
            'y': cell['y'],
            'z': cell['z']
        })

df = pd.DataFrame(data)


# Function to create a cube (box) at position (x, y, z)
def create_cube(x, y, z, size=1):
    r = [-size / 2, size / 2]
    X, Y = np.meshgrid(r, r)
    X = X.flatten()
    Y = Y.flatten()
    Z = np.array([r[0]] * len(X))

    # Define the vertices of the cube
    vertices = np.array([
        [X[0] + x, Y[0] + y, Z[0] + z],  # Bottom-left-front
        [X[1] + x, Y[0] + y, Z[0] + z],  # Bottom-right-front
        [X[1] + x, Y[1] + y, Z[0] + z],  # Top-right-front
        [X[0] + x, Y[1] + y, Z[0] + z],  # Top-left-front
        [X[0] + x, Y[0] + y, Z[1] + z],  # Bottom-left-back
        [X[1] + x, Y[0] + y, Z[1] + z],  # Bottom-right-back
        [X[1] + x, Y[1] + y, Z[1] + z],  # Top-right-back
        [X[0] + x, Y[1] + y, Z[1] + z],  # Top-left-back
    ])

    # Define the six faces of the cube
    faces = [
        [vertices[0], vertices[1], vertices[2], vertices[3]],  # Front
        [vertices[4], vertices[5], vertices[6], vertices[7]],  # Back
        [vertices[0], vertices[1], vertices[5], vertices[4]],  # Bottom
        [vertices[2], vertices[3], vertices[7], vertices[6]],  # Top
        [vertices[1], vertices[2], vertices[6], vertices[5]],  # Right
        [vertices[0], vertices[3], vertices[7], vertices[4]]  # Left
    ]

    return faces


# Function to initialize the cubes for the current step
def initialize_cubes(live_cells):
    ax.clear()  # Clear existing cubes
    # Add new cubes
    for cell in live_cells:
        x = cell['x']
