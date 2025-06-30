import os
import random

def shuffle_drawings(input_path, output_path):
    with open(input_path, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    print(f"{os.path.basename(input_path)}: {len(lines)} Zeichnungen gefunden.")

    random.shuffle(lines)

    with open(output_path, 'w', encoding='utf-8') as file:
        file.writelines(lines)

    print(f"Gespeichert: {len(lines)} Zeichnungen → {output_path}\n")

def main():
    categories = ['apple', 'star', 'candle', 'fork', 'eyeglasses']
    input_dir = 'data'
    output_dir = 'shuffled'
    os.makedirs(output_dir, exist_ok=True)

    for category in categories:
        input_file = os.path.join(input_dir, f'{category}.ndjson')
        output_file = os.path.join(output_dir, f'{category}_shuffled.ndjson')
        shuffle_drawings(input_file, output_file)

if __name__ == '__main__':
    main()
