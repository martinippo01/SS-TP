import json
import cv2
import numpy as np
import argparse


# Parse arguments
parser = argparse.ArgumentParser(description="Process animation parameters.")
parser.add_argument('--input', type=str, default="./input/input.json", help='Path to the input json')
parser.add_argument('--output', type=str, default="./output/", help='Path to the output directory')
parser.add_argument('--name', type=str, default="rugby_animation", help='Name of the output video')
args = parser.parse_args()

# Load JSON data
with open(args.input) as f:
    data = json.load(f)

# Field dimensions from JSON
field_w, field_h = data["params"]["fieldW"], data["params"]["fieldH"]
scale = 19  # Scale up for visualization

# Initialize OpenCV video writer
frame_w, frame_h = int(field_w * scale), int(field_h * scale)
fourcc = cv2.VideoWriter_fourcc(*'mp4v')
output_path = f"{args.output}/{args.name}.mp4"
video_writer = cv2.VideoWriter(output_path, fourcc, 30, (frame_w, frame_h))


# Function to draw players and arrows on the frame
def draw_frame(event):
    frame = np.full((frame_h, frame_w, 3), (0, 255, 0), dtype=np.uint8)  # Green background
    arrow_scale = 0.3  # Scale factor for the arrows to make them smaller
    radius_factor = 1.25 * scale

    # Draw blue players
    for player in event["bluePlayers"]:
        pos = (int(player["pos"]["x"] * scale), int(player["pos"]["y"] * scale))
        vel = (int(player["vel"]["x"] * scale * arrow_scale), int(player["vel"]["y"] * scale * arrow_scale))
        cv2.circle(frame, pos, int(player["radius"] * radius_factor), (255, 0, 0), -1)  # Blue circle
        cv2.arrowedLine(frame, pos, (pos[0] + vel[0], pos[1] + vel[1]), (255, 0, 0), 2)

    # Draw red player
    red_player = event["redPlayer"]
    red_pos = (int(red_player["pos"]["x"] * scale), int(red_player["pos"]["y"] * scale))
    red_vel = (int(red_player["vel"]["x"] * scale * arrow_scale), int(red_player["vel"]["y"] * scale * arrow_scale))
    cv2.circle(frame, red_pos, int(red_player["radius"] * radius_factor), (0, 0, 255), -1)  # Red circle
    cv2.arrowedLine(frame, red_pos, (red_pos[0] + red_vel[0], red_pos[1] + red_vel[1]), (0, 0, 255), 2)

    return frame



# Generate frames and write to video
for event in data["events"]:
    frame = draw_frame(event)
    video_writer.write(frame)

# Release the video writer
video_writer.release()
print(f"Video saved to {output_path}")
