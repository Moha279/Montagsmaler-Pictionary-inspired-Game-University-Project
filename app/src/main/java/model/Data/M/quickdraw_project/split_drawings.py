# -*- coding: utf-8 -*-
import os
import random

def split_drawings(input_path, train_output_path, test_output_path, train_ratio=0.8):
    """
    Teilt die QuickDraw-Daten in Trainings- und Testdaten.
    :param input_path: Pfad zur Original-ndjson-Datei
    :param train_output_path: Pfad zur Datei für Trainingsdaten
    :param test_output_path: Pfad zur Datei für Testdaten
    :param train_ratio: Anteil der Daten im Training (z.B. 0.8 für 80%)
    """
    with open(input_path, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    total = len(lines)
    print(f"{os.path.basename(input_path)}: {total} Zeichnungen geladen.")

    random.shuffle(lines)  # Durchmischen

    split_index = int(total * train_ratio)
    train_samples = lines[:split_index]
    test_samples = lines[split_index:]

    os.makedirs(os.path.dirname(train_output_path), exist_ok=True)
    os.makedirs(os.path.dirname(test_output_path), exist_ok=True)

    with open(train_output_path, 'w', encoding='utf-8') as train_file:
        train_file.writelines(train_samples)

    with open(test_output_path, 'w', encoding='utf-8') as test_file:
        test_file.writelines(test_samples)

    print(f"Trainingsdaten gespeichert: {len(train_samples)} → {train_output_path}")
    print(f"Testdaten gespeichert: {len(test_samples)} → {test_output_path}\n")

def main():
    categories = ['apple', 'star', 'candle', 'fork', 'eyeglasses']
    input_dir = 'data'           # Originaldaten
    output_train_dir = 'split/train'
    output_test_dir = 'split/test'
    train_ratio = 0.8            # 80% Training, 20% Test

    os.makedirs(output_train_dir, exist_ok=True)
    os.makedirs(output_test_dir, exist_ok=True)

    for category in categories:
        input_file = os.path.join(input_dir, f'{category}.ndjson')
        train_output_file = os.path.join(output_train_dir, f'{category}_train.ndjson')
        test_output_file = os.path.join(output_test_dir, f'{category}_test.ndjson')
        split_drawings(input_file, train_output_file, test_output_file, train_ratio)

if __name__ == '__main__':
    main()
