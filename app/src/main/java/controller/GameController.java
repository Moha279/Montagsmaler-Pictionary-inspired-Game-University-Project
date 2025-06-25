package controller;

import utils.ImageUtils;

/**
 * GameController connects GUI input with model processing logic.
 */
public class GameController {

    public void handleDrawingInput(byte[] rawInput) {
        try {
            byte[] downscaled = ImageUtils.downscale28x28To14x14(rawInput);
            classifyDrawing(downscaled);
        } catch (Exception e) {
            System.err.println("Error processing drawing: " + e.getMessage());
        }
    }

    public void classifyDrawing(byte[] input) {
        // TODO: Replace with real network/model call
        String mockResult = "tree";
        updateUIWithResult(mockResult);
    }

    public void updateUIWithResult(String result) {
        // TODO: Notify the GUI view with the result
        System.out.println("Predicted object: " + result);
    }
}
