package controller;

import model.NeuralNetz;
import view.ExampleView;

public class GameController {

    /**
     * Rounds each double value in the input array to two decimal places
     * and forwards the result to the GUI.
     *
     * @param rawOutput An array of 5 double values from the model.
     */
    public void forwardRoundedResults(double[] rawOutput) {
        if (rawOutput == null || rawOutput.length != 5) {
            return;
        }

        double[] rounded = new double[5];
        for (int i = 0; i < 5; i++) {
            rounded[i] = Math.round(rawOutput[i] * 100.0) / 100.0;
        }

        ExampleView.showRawProbabilities(rounded);
    }

    /**
     * Flattens a 2D matrix (28x28) into a 1D vector (784).
     *
     * @param matrix 2D double array (28x28)
     * @return 1D double array (flattened)
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
