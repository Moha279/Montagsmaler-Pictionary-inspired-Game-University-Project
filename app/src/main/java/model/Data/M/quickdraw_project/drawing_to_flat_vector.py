import json
import os

def drawing_to_flat_vector(drawing):
    """
    Konvertiert eine QuickDraw-Zeichnung in eine flache Liste von double-Werten.
    Format: [x1, y1, x2, y2, ...]
    """
    result = []
    for stroke in drawing:
        x, y = stroke
        for i in range(len(x)):
            result.append(float(x[i]))
            result.append(float(y[i]))
    return result

def convert_and_save_per_category(input_dir, output_dir, categories, sample_size=100):
    os.makedirs(output_dir, exist_ok=True)

    for category in categories:
        input_file = os.path.join(input_dir, f"{category}_sample.ndjson")
        output_file = os.path.join(output_dir, f"{category}_input.json")

        print(f"📂 Lese: {input_file}")
        try:
            with open(input_file, "r", encoding="utf-8") as f:
                lines = f.readlines()

            vectors = []
            for line in lines[:sample_size]:
                data = json.loads(line)
                flat_vector = drawing_to_flat_vector(data["drawing"])
                vectors.append(flat_vector)

            with open(output_file, "w", encoding="utf-8") as out:
                json.dump(vectors, out)

            print(f"✅ Gespeichert: {output_file} ({len(vectors)} Vektoren)")

        except Exception as e:
            print(f"❌ Fehler bei {category}: {e}")

if __name__ == "__main__":
    categories = ['apple', 'star', 'candle', 'fork', 'eyeglasses']
    input_directory = "samples"         # enthält *_sample.ndjson
    output_directory = "input_vectors"  # hierhin schreiben wir die JSON-Dateien

    convert_and_save_per_category(input_directory, output_directory, categories)
