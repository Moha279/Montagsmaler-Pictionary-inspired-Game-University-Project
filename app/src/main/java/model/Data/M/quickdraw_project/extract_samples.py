# -*- coding: utf-8 -*-
import os
import random

def sample_drawings(input_path, output_path, sample_size=100):
    with open(input_path, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    print(f"{os.path.basename(input_path)}: {len(lines)} Zeichnungen geladen für Zufallsauswahl.")

    if len(lines) < sample_size:
        print(f"Nur {len(lines)} vorhanden. Nehme alle.")
        sample_size = len(lines)

    samples = random.sample(lines, sample_size)

    with open(output_path, 'w', encoding='utf-8') as file:
        file.writelines(samples)

    print(f"Gespeichert: {sample_size} Zeichnungen → {output_path}\n")

def main():
    categories = ['apple', 'star', 'candle', 'fork', 'eyeglasses']
    input_dir = 'split/test'  # Jetzt NUR Trainingsdaten verwenden
    output_dir = 'samples_test'     # Hier speicherst du deine zufällige Auswahl
    sample_size = 2000

    os.makedirs(output_dir, exist_ok=True)

    for category in categories:
        input_file = os.path.join(input_dir, f'{category}_test.ndjson')  # Verwende NUR die Trainingsdatei
        output_file = os.path.join(output_dir, f'{category}_sample.ndjson')
        sample_drawings(input_file, output_file, sample_size)

if __name__ == '__main__':
    main()
