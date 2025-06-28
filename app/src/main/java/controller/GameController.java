package controller;

import java.util.Map;
import java.util.logging.Logger;
import model.NeuralNetz;
import utils.ImageUtils;
import view.ExampleView;

public class GameController {

    private static final Logger logger = Logger.getLogger(GameController.class.getName());

    public GameController() {
        // Constructor can initialize components if needed
    }

    /**
     * Handles drawing input from the GUI.
     * @param matrix A 28x28 byte matrix representing the user's drawing.
     */
    public void handleDrawingInput(byte[][] matrix) {
        try {
            logger.info("Received drawing input from GUI.");
            byte[][] scaledMatrix = ImageUtils.downscale28x28To14x14(matrix);
            logger.info("Drawing successfully downscaled to 14x14.");
            sendToModel(scaledMatrix);
        } catch (Exception e) {
            handleError(e);
        }
    }

    /**
     * Sends the scaled matrix to the model for classification.
     * @param drawingMatrix A downscaled 14x14 byte matrix.
     */
    public void sendToModel(byte[][] drawingMatrix) {
        try {
            logger.info("Sending matrix to model for classification.");
            Map<String, Double> result = NeuralNetz.classify(drawingMatrix);
            logger.info("Classification result received from model.");
            updateUIWithResult(result);
        } catch (Exception e) {
            handleError(e);
        }
    }

    /**
     * Updates the GUI with classification results from the model.
     * @param predictions A map of label names to their predicted probabilities.
     */
    public void updateUIWithResult(Map<String, Double> predictions) {
        logger.info("Updating UI with classification result.");
        ExampleView.showPrediction(predictions);
    }

    /**
     * Informs the GUI to clear the drawing canvas.
     */
    public void clearDrawing() {
        logger.info("Clearing drawing canvas.");
        ExampleView.clearCanvas();
    }

    /**
     * Handles errors that occur during any controller operation.
     * @param e The exception to be logged and displayed.
     */
    public void handleError(Exception e) {
        logger.severe("[GameController] Error: " + e.getMessage());
        ExampleView.showError("An error occurred. Please try again.");
    }
}