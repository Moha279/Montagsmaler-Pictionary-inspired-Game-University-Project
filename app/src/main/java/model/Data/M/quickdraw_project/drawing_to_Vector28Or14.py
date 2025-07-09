import os
import json
import numpy as np
from PIL import Image, ImageDraw
import random

def perturb_drawing(drawing, shift_range=12, scale_range=0.8):
    """
    Fügt jedem QuickDraw-Stroke eine zufällige Verschiebung und Skalierung hinzu,
    um realistischere Variationen wie in der GUI zu simulieren.
    """
    shift_x = random.uniform(-shift_range, shift_range)
    shift_y = random.uniform(-shift_range, shift_range)
    scale = 1.0 + random.uniform(-scale_range, scale_range)

    new_drawing = []
    for stroke in drawing:
        x_pts, y_pts = stroke
        new_x = [(x - 128) * scale + 128 + shift_x for x in x_pts]
        new_y = [(y - 128) * scale + 128 + shift_y for y in y_pts]
        new_drawing.append([new_x, new_y])
    return new_drawing


def drawing_to_binary_vector(drawing, size=28, canvas_size=256, stroke_width=10):
    img = Image.new("L", (canvas_size, canvas_size), color=255)
    draw = ImageDraw.Draw(img)

    for stroke in drawing:
        x_pts, y_pts = stroke
        coords = [(
            x / 255 * (canvas_size - 1),
            y / 255 * (canvas_size - 1)
        ) for x, y in zip(x_pts, y_pts)]
        if len(coords) > 1:
            draw.line(coords, fill=0, width=stroke_width)
        else:
            x, y = coords[0]
            r = stroke_width // 2
            draw.ellipse((x-r, y-r, x+r, y+r), fill=0)

    img28 = img.resize((28, 28), resample=Image.LANCZOS)
    arr28 = np.array(img28)
    bin28 = (arr28 < 128).astype(np.float32)

    if size == 14:
        bin14 = bin28.reshape(14, 2, 14, 2).max(axis=(1, 3))
        return bin14.flatten().tolist()

    return bin28.flatten().tolist()


def convert_quickdraw_file(input_file, output_file, size=28, augment=True):
    vectors = []
    with open(input_file, 'r', encoding='utf-8') as f:
        for line in f:
            original_drawing = json.loads(line)["drawing"]

            # Optional Augmentierung aktivieren
            drawing = perturb_drawing(original_drawing) if augment else original_drawing
            vec = drawing_to_binary_vector(drawing, size=size)
            vectors.append(vec)

    os.makedirs(os.path.dirname(output_file), exist_ok=True)
    with open(output_file, 'w', encoding='utf-8') as outf:
        json.dump(vectors, outf)
    print(f"Konvertiert {len(vectors)} Zeichnungen → {output_file}")


if __name__ == "__main__":
    categories = ['apple', 'star', 'candle', 'fork', 'eyeglasses']
    sample_dir = "samples_train"
    out_dir = "converted_vectors_train"
    for cat in categories:
        inp = os.path.join(sample_dir, f"{cat}_sample.ndjson")
        o28 = os.path.join(out_dir, f"{cat}_vector_28.json")
        o14 = os.path.join(out_dir, f"{cat}_vector_14.json")
        convert_quickdraw_file(inp, o28, size=28, augment=True)
        convert_quickdraw_file(inp, o14, size=14, augment=True)
