package model;

import java.util.*;

/**
 * this class represents a  feedforward neural network.
 */
public class NeuralNetz{

    private double[][] weightsInputHidden;
    private double[] biasHidden;

    private double[][] weightsHiddenOutput;
    private double[] biasOutput;

    private int inputSize;
    private int hiddenSize;
    private int outputSize;

    /**
     * constructor to initialize the neural network with random weights and biases.
     *
     * @param inputs  number of input nodes
     * @param hidden  number of hidden nodes
     * @param outputs number of output nodes
     */
    public NeuralNetz(int inputs, int hidden, int outputs) {
        inputSize = inputs;
        hiddenSize = hidden;
        outputSize = outputs;
        biasHidden = randomVector(hidden);
        weightsInputHidden = randomMatrix(hiddenSize, inputSize);
        weightsHiddenOutput = randomMatrix(outputSize, hiddenSize);
        biasOutput = randomVector(outputSize);
    }

    /**
     * sets the weights between input and hidden layer.
     *
     * @param updateWeightsInputHidden new weights to set
     */
    public void setWeightsInputHidden(double[][] updateWeightsInputHidden) {
        this.weightsInputHidden = updateWeightsInputHidden;
    }

    /**
     * sets the biases for the hidden layer.
     *
     * @param updateBiasHidden new biases to set
     */
    public void setBiasHidden(double[] updateBiasHidden) {
        this.biasHidden = updateBiasHidden;
    }

    /**
     * sets the weights between hidden and output layer.
     *
     * @param updateWeightsHiddenOutput new weights to set
     */
    public void setWeightsHiddenOutput(double[][] updateWeightsHiddenOutput) {
        this.weightsHiddenOutput = updateWeightsHiddenOutput;
    }

    /**
     * sets the biases for the output layer.
     *
     * @param updateBiasOutput new biases to set
     */
    public void setBiasOutput(double[] updateBiasOutput) {
        this.biasOutput = updateBiasOutput;
    }

    /**
     * creates a random matrix with values between 0 and 1.
     *
     * @param size1 number of rows
     * @param size2 number of columns
     * @return generated random matrix
     */
    public double[][] randomMatrix(int size1, int size2) {
        Random randm = new Random();
        double result[][] = new double[size1][size2];
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                result[i][j] = randm.nextDouble(0.0, 1.0);
            }
        }
        return result;
    }

    /**
     * creates a random vector with values between 0 and 1.
     *
     * @param number size of the vector
     * @return generated random vector
     */
    public double[] randomVector(int number) {
        Random randm = new Random();
        double[] result = new double[number];
        for (int i = 0; i < number; i++) {
            result[i] = randm.nextDouble(0.0, 1.0);
        }
        return result;
    }

    /**
     * performs the forward propagation through the network.
     *
     * @param inputs input vector
     * @return output vector after softmax
     */
    public double[] forward(double[] inputs) {
        double[] hiddenInput = MathFunctions.add(MathFunctions.multiply(weightsInputHidden, inputs), biasHidden);
        double[] hiddenOutput = MathFunctions.applyReLU(hiddenInput);
        double[] finalInput = MathFunctions.add(MathFunctions.multiply(weightsHiddenOutput, hiddenOutput), biasOutput);
        return MathFunctions.softmax(finalInput);
    }

}
