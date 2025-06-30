# -*- coding: utf-8 -*-
import os
import random

def sample_drawings(input_path, output_path, sample_size=100):
    with open(input_path, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    print(f"{os.path.basename(input_path)}: {len(lines)} Zeichnungen gefunden.")

    if len(lines) < sample_size:
        print(f"Nur {len(lines)} vorhanden. Nehme alle.")
        sample_size = len(lines)

    samples = random.sample(lines, sample_size)

    with open(output_path, 'w', encoding='utf-8') as file:
        file.writelines(samples)

    print(f"Gespeichert: {sample_size} Zeichnungen → {output_path}\n")

def main():
    categories = ['apple', 'star', 'candle', 'fork', 'eyeglasses']
    input_dir = 'data'
    output_dir = 'samples'
    sample_size = 1000

    os.makedirs(output_dir, exist_ok=True)

    for category in categories:
        input_file = os.path.join(input_dir, f'{category}.ndjson')
        output_file = os.path.join(output_dir, f'{category}_sample.ndjson')
        sample_drawings(input_file, output_file, sample_size)

if __name__ == '__main__':
    main()