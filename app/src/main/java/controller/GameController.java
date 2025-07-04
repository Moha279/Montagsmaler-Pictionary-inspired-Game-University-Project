package controller;

import model.NeuralNetz;

public class GameController {

    static NeuralNetz model = new NeuralNetz(784, 10, 5);

    /**
     * Converts 28x28 int matrix (0/1 pixels) to double[] input for model,
     * then classifies and forwards rounded results to GUI.
     * 
     * @param pixelMatrix 28x28 int matrix with pixel data (0 or 1)
     */
    public static double[] classifyFromPixelMatrix(double[][] pixelMatrix) {        double[] input = flatten(pixelMatrix);
        for (int i = 0; i < rawOutput.length; i++) {
            roundedOutput[i] = Math.round(rawOutput[i] * 100.0) / 100.0;
        }
        return roundedOutput;
    }

    /**
     * Helper method to flatten 28x28 int matrix into 1D double array (784)
     * 
     * @param matrix 28x28 int matrix
     * @return flattened double array
     */
    public static double[] flatten(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] flat = new double[rows * cols];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flat[index++] = matrix[i][j];
            }
        }
        return flat;
    }
}
