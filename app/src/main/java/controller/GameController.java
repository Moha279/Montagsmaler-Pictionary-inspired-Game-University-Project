package controller;

import model.MultiModelClassifier;

public class GameController {

    static MultiModelClassifier classifier = new MultiModelClassifier();
    static String[] categoryLabels = {"apple", "candle", "eyeglasses", "fork", "star"};

    /**
     * Converts a 28x28 int matrix (0/1 pixels) into a double[] input for the model,
     * then classifies and returns the rounded results to the GUI.
     * 
     * @param pixelMatrix 28x28 int matrix with pixel data (0 or 1)
     */
    public static double[] classifyFromPixelMatrix(double[][] pixelMatrix) {
        if (pixelMatrix == null || pixelMatrix.length != 28 || pixelMatrix[0].length != 28) {
            throw new IllegalArgumentException("Input must be a 28x28 int matrix.");
        }

        // 1. Convert int[][] to double[] (flatten)
        double[] input = downscale28to14(flatten(pixelMatrix));

        // 2. Classify with all 5 models
        double[] allProbabilities = classifier.getAllCategoryProbabilities(input);
        
        // 3. Find the best category (highest probability)
        int bestIndex = 0;
        double bestValue = allProbabilities[0];
        for (int i = 1; i < allProbabilities.length; i++) {
            if (allProbabilities[i] > bestValue) {
                bestValue = allProbabilities[i];
                bestIndex = i;
            }
        }
        
        // 4. Boost the best category and attenuate the others
        double[] adjustedProbabilities = new double[allProbabilities.length];
        
        // Ensure the best category is at least 60% and at most 85%
        double bestPercentage = Math.max(0.60, Math.min(0.85, bestValue + 0.3));
        adjustedProbabilities[bestIndex] = bestPercentage;
        
        // Distribute the remainder (100% - bestPercentage) among the others
        double remainingPercentage = 1.0 - bestPercentage;
        double sumOthers = 0.0;
        
        // Calculate the sum of the other probabilities
        for (int i = 0; i < allProbabilities.length; i++) {
            if (i != bestIndex) {
                sumOthers += allProbabilities[i];
            }
        }
        
        // Distribute the remaining share proportionally across the others
        if (sumOthers > 0.0001) {
            for (int i = 0; i < allProbabilities.length; i++) {
                if (i != bestIndex) {
                    adjustedProbabilities[i] = (allProbabilities[i] / sumOthers) * remainingPercentage;
                }
            }
        } else {
            // If all others are zero, split equally
            double equalShare = remainingPercentage / (allProbabilities.length - 1);
            for (int i = 0; i < allProbabilities.length; i++) {
                if (i != bestIndex) {
                    adjustedProbabilities[i] = equalShare;
                }
            }
        }
        
        // 5. Round results to 2 decimal places
        for (int i = 0; i < adjustedProbabilities.length; i++) {
            adjustedProbabilities[i] = Math.round(adjustedProbabilities[i] * 100.0) / 100.0;
        }

        return adjustedProbabilities;
    }

    /**
     * Downscales a 28x28 input vector to 14x14 using 2x2 max pooling.
     *
     * @param input28x28 a flat 784-element vector (28x28 image)
     * @return downscaled 196-element vector (14x14 image)
     */
    public static double[] downscale28to14(double[] input28x28) {
        if (input28x28.length != 784)
            throw new IllegalArgumentException("Input must contain 784 (28x28) elements.");

        double[] result = new double[14 * 14];

        for (int y = 0; y < 14; y++) {
            for (int x = 0; x < 14; x++) {
                double max = 0;
                for (int dy = 0; dy < 2; dy++) {
                    for (int dx = 0; dx < 2; dx++) {
                        int srcY = y * 2 + dy;
                        int srcX = x * 2 + dx;
                        double val = input28x28[srcY * 28 + srcX];
                        if (val > max) max = val;
                    }
                }
                result[y * 14 + x] = max;
            }
        }

        return result;
    }
    
    /**
     * Returns the name of the recognized category.
     * 
     * @param pixelMatrix 28x28 pixel matrix
     * @return Name of the recognized category
     */
    public static String getBestCategory(double[][] pixelMatrix) {
        double[] input = downscale28to14(flatten(pixelMatrix));
        return classifier.getBestMatch(input);
    }

    /**
     * Helper method to flatten a 28x28 int matrix into a 1D double array (784 elements)
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
