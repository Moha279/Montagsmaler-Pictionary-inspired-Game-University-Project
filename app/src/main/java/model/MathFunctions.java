package model;
import java.util.Arrays;

/**
 * MathFunctions interface provides essential mathematical operations
 * used in neural networks, including activation functions, their derivatives,
 * loss functions, dot product, softmax, and normalization.
 */
public interface MathFunctions {

    /**
     * Sigmoid activation function.
     *
     * @param x Input value.
     * @return Activated output between 0 and 1.
     */
    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    /**
     * Derivative of the sigmoid function.
     *
     * @param x Input value.
     * @return Derivative of the sigmoid function at x.
     */
    public static double sigmoidDerivative(double x) {
        double sig = sigmoid(x);
        return sig * (1 - sig);
    }

    /**
     * Applies an activation function element-wise to a vector.
     *
     * @param vector Input vector.
     * @return Vector after applying the sigmoid function to each element.
    */
    public static double[] applySigmoid(double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = sigmoid(vector[i]);
        }
        return result;
    }


    /**
     * ReLU activation function.
     *
     * @param x Input value.
     * @return Output: x if x > 0, otherwise 0.
     */
    public static double relu(double x) {
        return Math.max(0, x);
    }

    /**
     * Derivative of the ReLU function.
     *
     * @param x Input value.
     * @return 1 if x > 0, otherwise 0.
     */
    static double reluDerivative(double x) {
        return x > 0 ? 1 : 0;
    }

    /**
     * Applies the ReLU function element-wise to a vector.
     *
     * @param vector Input vector.
     * @return Vector after applying ReLU to each element.
     */
    public static double[] applyReLU(double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = Math.max(0, vector[i]);
        }
        return result;
    }

    /**
     * Applies the derivative of the ReLU function element-wise to a vector.
     *
     * @param vector Input vector.
     * @return Vector of derivatives.
     */
    public static double[] reluDerivative(double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] > 0 ? 1 : 0;
        }
        return result;
    }

    /**
     * Calculates the dot product of two vectors.
     *
     * @param a First vector.
     * @param b Second vector.
     * @return Dot product of vectors a and b.
     * @throws IllegalArgumentException if vectors have different lengths.
     */
    public static double dot(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have the same length.");
        }
        double result = 0.0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    /**
     * Calculates the cross-entropy loss for binary classification.
     *
     * @param yTrue Expected label (0 or 1).
     * @param yPred Predicted probability (between 0 and 1).
     * @return Cross-entropy loss value.
     */
    public static double crossEntropyLoss(double yTrue, double yPred) {
        return - (yTrue * Math.log(yPred + 1e-15) + (1 - yTrue) * Math.log(1 - yPred + 1e-15));
    }

    /**
     * Calculates the Mean Squared Error (MSE) between predicted and true values.
     *
     * @param yTrue Array of true labels.
     * @param yPred Array of predicted labels.
     * @return Mean Squared Error.
     * @throws IllegalArgumentException if arrays have different lengths.
     */
    public static double meanSquaredError(double[] yTrue, double[] yPred) {
        if (yTrue.length != yPred.length) {
            throw new IllegalArgumentException("Arrays must have the same length.");
        }
        double sum = 0.0;
        for (int i = 0; i < yTrue.length; i++) {
            sum += Math.pow(yTrue[i] - yPred[i], 2);
        }
        return sum / yTrue.length;
    }

   /**
     * Calculates the error vector for backpropagation (difference between predicted and true values).
     *
     * @param yTrue Array of true labels.
     * @param yPred Array of predicted labels.
     * @return Error vector.
     * @throws IllegalArgumentException if arrays have different lengths.
     */
    public static double[] errorVector(double[] yTrue, double[] yPred) {
        if (yTrue.length != yPred.length) {
            throw new IllegalArgumentException("Arrays must have the same length.");
        }
        double[] errors = new double[yPred.length];
        for (int i = 0; i < yTrue.length; i++) {
            errors[i] = yPred[i] - yTrue[i]; 
        }
        return errors;
    }


    /**
     * Multiplies a matrix by a vector.
     *
     * @param matrix 2D array representing the matrix.
     * @param vector 1D array representing the vector.
     * @return Resulting vector.
     * @throws IllegalArgumentException if dimensions do not match.
    */
    public static double[] multiply(double[][] matrix, double[] vector) {
        if (matrix[0].length != vector.length) {
            throw new IllegalArgumentException("Matrix columns must match vector size.");
        }
        double[] result = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = 0.0;
            for (int j = 0; j < vector.length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    /**
     * Adds two vectors element-wise.
     *
     * @param a First vector.
     * @param b Second vector.
     * @return Resulting vector after addition.
     * @throws IllegalArgumentException if vectors have different lengths.
     */
    public static double[] add(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vectors must have the same length.");
        }
        double[] result = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] + b[i];
        }
        return result;
    }
    
    /**
     * Applies the Softmax function to a vector.
     *
     * @param outputs Raw outputs from the last layer
     * @return Softmax probabilities
     */
    public static double[] softmax(double[] outputs) {
        double max = Arrays.stream(outputs).max().orElse(0.0); // für Stabilität
        double sum = 0.0;
        double[] exp = new double[outputs.length];

        for (int i = 0; i < outputs.length; i++) {
            exp[i] = Math.exp(outputs[i] - max);
            sum += exp[i];
        }

        for (int i = 0; i < outputs.length; i++) {
            exp[i] /= sum;
        }

        return exp;
    }


    /**
     * Normalizes a pixel value from the range 0-255 to the range 0-1.
     *
     * @param pixelValue Raw pixel value (0-255).
     * @return Normalized value between 0 and 1.
     */
    static double normalize(double pixelValue) {
        return pixelValue / 255.0;
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
