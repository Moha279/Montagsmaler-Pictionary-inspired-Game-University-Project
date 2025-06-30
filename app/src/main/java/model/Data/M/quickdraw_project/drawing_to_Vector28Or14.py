import numpy as np
import json
import os

def drawing_to_vector(drawing, size=28): #Size in 14
    """
    Wandelt eine QuickDraw-Zeichnung in eine flache Vektor-Repräsentation um.
    Die Zeichnung wird auf eine 'size x size' Pixel-Matrix gerastert und anschließend abgeflacht.

    :param drawing: QuickDraw Format: List of strokes, each stroke: [x_points, y_points]
    :param size: Zielgröße der quadratischen Matrix (z.B. 14 oder 28)
    :return: Flacher Vektor (Liste) der Länge size*size mit Pixelwerten 0/1
    """
    canvas = np.zeros((size, size), dtype=np.float32)

    for stroke in drawing:
        x_points, y_points = stroke
        for x, y in zip(x_points, y_points):
            # Normiere Koordinaten auf Bereich [0, 1]
            x_norm = x / 255
            y_norm = y / 255

            # Skaliere auf Zielgröße
            xi = int(np.clip(x_norm * (size - 1), 0, size - 1))
            yi = int(np.clip(y_norm * (size - 1), 0, size - 1))

            canvas[yi, xi] = 1.0  # Pixel setzen

    # Flach machen
    flat_vector = canvas.flatten().tolist()
    return flat_vector


def convert_quickdraw_file(input_file, output_file, size=28, sample_size=None):
    """
    Liest QuickDraw .ndjson Datei ein, wandelt Zeichnungen in Vektoren um und speichert als JSON.

    :param input_file: Pfad zur QuickDraw ndjson Datei
    :param output_file: Ziel JSON-Datei für flache Vektoren
    :param size: Zielgröße für Rasterung (z.B. 14 oder 28)
    :param sample_size: Anzahl Zeichnungen, die verarbeitet werden (None = alle)
    """
    vectors = []
    with open(input_file, 'r', encoding='utf-8') as f:
        for i, line in enumerate(f):
            if sample_size is not None and i >= sample_size:
                break
            data = json.loads(line)
            drawing = data["drawing"]
            vec = drawing_to_vector(drawing, size=size)
            vectors.append(vec)

    os.makedirs(os.path.dirname(output_file), exist_ok=True)
    with open(output_file, 'w', encoding='utf-8') as out_f:
        json.dump(vectors, out_f)
    print(f"{len(vectors)} Zeichnungen konvertiert und gespeichert in: {output_file}")


if __name__ == "__main__":
    categories = ['apple', 'star', 'candle', 'fork', 'eyeglasses']
    input_dir = "samples"           # Ordner mit QuickDraw .ndjson Dateien (z.B. apple_sample.ndjson)
    output_dir = "converted_vectors"

    # Beispiel: Größe 28x28
    for cat in categories:
        input_path = os.path.join(input_dir, f"{cat}_sample.ndjson")
        output_path = os.path.join(output_dir, f"{cat}_vector_28.json")
        convert_quickdraw_file(input_path, output_path, size=28, sample_size=1000)

    # Beispiel: Größe 14x14 (wenn du kleinere möchtest)
    for cat in categories:
        input_path = os.path.join(input_dir, f"{cat}_sample.ndjson")
        output_path = os.path.join(output_dir, f"{cat}_vector_14.json")
        convert_quickdraw_file(input_path, output_path, size=14, sample_size=1000)
