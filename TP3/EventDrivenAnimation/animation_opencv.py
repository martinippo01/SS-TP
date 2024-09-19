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
simulation_type = static_data['inputData']['simulationType']
plane_type = static_data['inputData']['planeType']

# Set up window size based on the size of the simulation plane
plane_size = 600
window_size = plane_size + 50
scale = plane_size / L


# Create a function to draw a frame
def draw_frame(particles, frame_number):
    adj_y = 0
    adj_x = 0

    # Create a blank white image
    frame = np.ones((window_size, window_size, 3), dtype=np.uint8) * 255

    # Draw circular plane if the plane type is 'CIRCULAR' and set (0,0) as the center
    if plane_type == 'CIRCULAR':
        adj_y = window_size // 2
        adj_x = window_size // 2
        cv2.circle(frame, (adj_x, adj_y), plane_size // 2, (0, 0, 0), 2)

    # Draw the obstacle (red circle) if the simulation type is 'OBSTACLE'
    if simulation_type == 'OBSTACLE':
        obstacle = static_data['inputData']['obstacles'][0]
        obstacle_x = int(obstacle['position']['x'] * scale)
        obstacle_y = int(obstacle['position']['y'] * scale)
        obstacle_radius = int(obstacle['radius'] * scale)
        cv2.circle(frame, (obstacle_x + adj_x, obstacle_y + adj_y), obstacle_radius, (0, 0, 255), -1)

    # Draw each particle (blue circle)
    for particle in particles:
        x = int(particle['position']['x'] * scale)
        y = int(particle['position']['y'] * scale)
        particle_radius = int(particle['radius'] * scale)
        cv2.circle(frame, (x + adj_x, y + adj_y), particle_radius, (255, 0, 0), -1)

    # Add text to show the frame number
    cv2.putText(frame, f"Frame: {frame_number}", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 0), 2)

    return frame


fourcc = cv2.VideoWriter.fourcc(*'mp4v')
out = cv2.VideoWriter('particle_simulation_1.mp4', fourcc, 20.0, (window_size, window_size))
show_frame = True

# Get particle data from events and render each frame
events = dynamic_data['events']
length = len(events)
for i, event in enumerate(dynamic_data['events']):
    if i % 50 != 0:
        continue
    particles = event['particles']

    # Draw the frame
    frame = draw_frame(particles, i + 1)

    # Write the frame to the video file
    out.write(frame)
    print(f"Frame {i + 1}/{length} rendered")

    if show_frame:
        cv2.imshow('Particle Simulation', frame)

    # Wait for a short moment to simulate the animation speed
    if cv2.waitKey(10) & 0xFF == ord('q'):
        show_frame = False
        cv2.destroyAllWindows()

# When the animation is done, close the window
out.release()
