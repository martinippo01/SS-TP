import json

import cv2
import numpy as np
import argparse

parser = argparse.ArgumentParser(description="Process animation parameters.")
parser.add_argument('--input', type=str, default="../../TimeBasedSimulation/outputs/damped/beeman/2beeman.json", help='Path to the input json')
parser.add_argument('--output', type=str, default="../output/", help='Path to the output directory')
parser.add_argument('--name', type=str, default="multiple_particles_animations", help='Name of the output video')
args = parser.parse_args()

# Load JSON data
with open(args.input) as f:
    data = json.load(f)

# Video settings
width, height = 1920, 1080
fps = 50 # Frames per second

args.output += '/' if args.output[-1] != '/' else args.output + ''
out = cv2.VideoWriter(args.output + args.name + '.mp4', cv2.VideoWriter_fourcc(*'XVID'), fps, (width, height))


# Function to draw a dot at given x, y coordinates
def draw_dot(frame, x, y):
    x = int((x + 1) / 2 * width)  # Scale x to fit the window
    y = int((1 - y) / 2 * height)  # Scale y and invert to match OpenCV's coordinates
    cv2.circle(frame, (x, y), 40, (0, 255, 0), -1)  # Draw a green circle


# Animate over each step in the JSON file
for step in data['steps']:
    frame = np.zeros((height, width, 3), dtype=np.uint8)  # Black background
    pos = step['positions'][0]  # Get the first position for this step
    draw_dot(frame, pos['x'], pos['y'])

    # Display time on the frame
    cv2.putText(frame, f"Time: {step['time']:.1f}s", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255), 2)

    # Write frame to the video
    out.write(frame)
    cv2.imshow('Animation', frame)

    if cv2.waitKey(int(1000 / fps)) & 0xFF == ord('q'):
        break

out.release()
cv2.destroyAllWindows()
