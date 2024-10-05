import json
import cv2
import numpy as np
import argparse


parser = argparse.ArgumentParser(description="Process animation parameters.")
parser.add_argument('--input', type=str, default="../input/input.json", help='Path to the input json')
parser.add_argument('--output', type=str, default="../output/", help='Path to the output directory')
parser.add_argument('--name', type=str, default="multiple_particles_animations", help='Name of the output video')
args = parser.parse_args()


# Load JSON data
with open(args.input) as f:
    data = json.load(f)

# Video settings
width, height = 640, 480
fps = 10  # Frames per second

total_width = data['params']['l0'] * data['params']['n']
total_height = data['params']['a']


args.output += '/' if args.output[-1] != '/' else args.output + ''
out = cv2.VideoWriter(args.output + args.name + '.mp4', cv2.VideoWriter_fourcc(*'mp4v'), fps, (width, height))


# Function to draw a dot at given x, y coordinates
def draw_dot(frame, x, y):
    # x = int((x + 1) / 2 * width)  # Scale x to fit the window
    # y = int((1 - y) / 2 * height)  # Scale y and invert to match OpenCV's coordinates
    x = int((x - 0) / (total_width - 0) * (0.95 * width))  # Normalize x based on min and max
    # y = int((1 - y) / 2 * height)  # Scale y and invert to match OpenCV's coordinates
    y = int((1 - (y + total_height) / (2 * total_height)) * height)  # Normalize y based on total_height
    cv2.circle(frame, (x, y), 5, (0, 0, 255), -1)  # Draw smaller red circles for particles

# Animate over each step in the JSON file
for step in data['steps']:
    frame = np.ones((height, width, 3), dtype=np.uint8) * 255  # White background

    # Loop through each particle's position and draw it
    for pos in step['positions']:
        draw_dot(frame, pos['x'], pos['y'])

    # Display time on the frame
    cv2.putText(frame, f"Time: {step['time']:.1f}s", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0,0,0), 2)

    # Write frame to the video
    out.write(frame)
    cv2.imshow('Animation', frame)

    if cv2.waitKey(int(1000 / fps)) & 0xFF == ord('q'):
        break

out.release()
cv2.destroyAllWindows()
