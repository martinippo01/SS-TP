import json
import cv2
import numpy as np
import argparse

# Parse arguments
parser = argparse.ArgumentParser(description="Process animation parameters.")
parser.add_argument('--input', type=str, default="./input/input.json", help='Path to the input JSON file')
parser.add_argument('--output', type=str, default="./output/", help='Path to the output directory')
parser.add_argument('--name', type=str, default="rugby_animation", help='Name of the output video')
parser.add_argument('--pitch', type=str, default="./images/field.png", help='Path to the rugby pitch image')
args = parser.parse_args()

# Load JSON data
with open(args.input) as f:
    data = json.load(f)

# Field dimensions from JSON
field_w, field_h = data["params"]["fieldW"], data["params"]["fieldH"]
scale = 19  # Scale up for visualization

# Define the width of the green margin (goal area)
margin_width = int(10 * scale)  # Adjust as necessary for goal width

# Initialize OpenCV video writer with expanded frame width
frame_w, frame_h = int((field_w + 2 * (margin_width / scale)) * scale), int(field_h * scale)
fourcc = cv2.VideoWriter_fourcc(*'mp4v')
output_path = f"{args.output}/{args.name}.mp4"
video_writer = cv2.VideoWriter(output_path, fourcc, 30, (frame_w, frame_h))

# Load and resize the rugby pitch image to fit within the expanded frame
pitch_image = cv2.imread(args.pitch)
pitch_image = cv2.resize(pitch_image, (frame_w - 2 * margin_width, frame_h))

# Function to draw players and arrows on the frame
def draw_frame(event):
    # Start with a green background and place the pitch image in the center
    frame = np.full((frame_h, frame_w, 3), (67, 132, 68), dtype=np.uint8)  # Green background for the entire frame
    frame[:, margin_width:margin_width + pitch_image.shape[1]] = pitch_image  # Place pitch in the center

    # Draw white border around the entire frame
    border_thickness = 5  # Thickness of the white border
    cv2.rectangle(frame, (0, 0), (frame_w - 1, frame_h - 1), (231, 230, 224), border_thickness)

    arrow_scale = 0.3  # Scale factor for the arrows to make them smaller
    radius_factor = 1.25 * scale

    # Draw blue players within the pitch area
    for player in event["bluePlayers"]:
        pos = (int(player["pos"]["x"] * scale) + margin_width, int(player["pos"]["y"] * scale))  # Offset by margin_width
        vel = (int(player["vel"]["x"] * scale * arrow_scale), int(player["vel"]["y"] * scale * arrow_scale))
        cv2.circle(frame, pos, int(player["radius"] * radius_factor), (122, 0, 0), -1)  # Blue circle
        cv2.arrowedLine(frame, pos, (pos[0] + vel[0], pos[1] + vel[1]), (122, 0, 0), 2)

    # Draw red player within the pitch area
    red_player = event["redPlayer"]
    red_pos = (int(red_player["pos"]["x"] * scale) + margin_width, int(red_player["pos"]["y"] * scale))  # Offset by margin_width
    red_vel = (int(red_player["vel"]["x"] * scale * arrow_scale), int(red_player["vel"]["y"] * scale * arrow_scale))
    cv2.circle(frame, red_pos, int(red_player["radius"] * radius_factor), (0, 0, 200), -1)  # Red circle
    cv2.arrowedLine(frame, red_pos, (red_pos[0] + red_vel[0], red_pos[1] + red_vel[1]), (0, 0, 200), 2)

    return frame

# Generate frames and write to video
for event in data["events"]:
    frame = draw_frame(event)
    video_writer.write(frame)

# Release the video writer
video_writer.release()
print(f"Video saved to {output_path}")
