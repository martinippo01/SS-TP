import json
import cv2
import numpy as np

# Load the JSON data
with open('./input/dynamic.json', 'r') as file:
    dynamic_data = json.load(file)

with open('./input/static.json', 'r') as file:
    static_data = json.load(file)

# Extract simulation parameters
L = static_data['inputData']['L']
obstacle = static_data['inputData']['obstacles'][0]

# Set up window size based on the size of the simulation plane
window_size = 600
scale = window_size / L

# Obstacle parameters
obstacle_x = int(obstacle['position']['x'] * scale)
obstacle_y = int(obstacle['position']['y'] * scale)
obstacle_radius = int(obstacle['radius'] * scale)


# Create a function to draw a frame
def draw_frame(particles, frame_number):
    # Create a blank white image
    frame = np.ones((window_size, window_size, 3), dtype=np.uint8) * 255

    # Draw the obstacle (red circle)
    cv2.circle(frame, (obstacle_x, obstacle_y), obstacle_radius, (0, 0, 255), -1)

    # Draw each particle (blue circle)
    for particle in particles:
        x = int(particle['position']['x'] * scale)
        y = int(particle['position']['y'] * scale)
        particle_radius = int(particle['radius'] * scale)
        cv2.circle(frame, (x, y), particle_radius, (255, 0, 0), -1)

    # Add text to show the frame number
    cv2.putText(frame, f"Frame: {frame_number}", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 0), 2)

    return frame


# Get particle data from events and render each frame
for i, event in enumerate(dynamic_data['events']):
    particles = event['particles']

    # Draw the frame
    frame = draw_frame(particles, i + 1)

    # Show the frame using OpenCV
    cv2.imshow('Particle Simulation', frame)

    # Wait for a short moment to simulate the animation speed
    if cv2.waitKey(10) & 0xFF == ord('q'):
        break

# When the animation is done, close the window
cv2.destroyAllWindows()
