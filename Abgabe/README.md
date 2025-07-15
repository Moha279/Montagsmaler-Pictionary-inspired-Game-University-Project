# Montagsmaler

A JavaFX project where users can draw an object, and a neural network tries to recognize what was drawn.

---

## Team

- Mohammad Elayan
- Mohammed Al-shami
- Dilshod Jabborov
- Taeb Al-Areqi

**Tutor:** Jonas Backhaus

---

## Features

- Mouse-based drawing area (JavaFX GUI)
- Real-time AI model recognition of hand-drawn sketches
- Displays similarity/probability (%) for each of the 5 classes:
  - Apple
  - Candle
  - Eyeglasses
  - Fork
  - Star

---

## Requirements

- **Java 17** or newer ([Download](https://adoptium.net/))
- No additional libraries required

---

## Installation & Running

1. Clone or download the repository.
2. Make sure `montagsmaler-fat.jar` and the `Data` folder are in the same directory.
3. **Windows:** Double-click `start.bat` to launch the app.  
   **Any OS:** Or run in the terminal:

java -jar montagsmaler-fat.jar

4. The main window will appear. Draw one of the five objects and see what the neural network predicts!

---

## Folder Structure

Abgabe/
  │───montagsmaler-fat.jar
  │───Montagsmaler.pptx
  │───README.md
  │
  ├───controller
  │
  ├───model
  │   │
  │   └───Data
  │       │
  │       ├───apple
  │       │
  │       ├───candle
  │       ├───eyeglasses
  │       │
  │       ├───fork
  │       │
  │       └───star
  │
  └───view
  
---

## Data

- The `Data/` directory contains JSON files for each class, based on Google's [QuickDraw dataset](https://quickdraw.withgoogle.com/data).
- Do **not** move or rename the `Data/` folder.

---

## Troubleshooting

- **App won't start:**  
  Make sure you have Java 17+ installed. Try running via terminal (`java -jar ...`).
- **"Data not found" error:**  
  Ensure the `Data` folder is in the same directory as the JAR file.

---

## Support

If you have questions or issues, please contact one of the team members or open an issue in the repository.

---

## License

This project is for educational purposes only.  
Do not redistribute or use commercially without permission.