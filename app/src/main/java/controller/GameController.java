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
}