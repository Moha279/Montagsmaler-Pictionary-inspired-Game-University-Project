package model;

import java.util.*;

import model.Data.*;

/**
 * Trainer class for training the Neural Network using backpropagation.
 */
public class Trainer {

    private NeuralNetz neuralNetz;
    private double learningRate;
    private int epochs;

    private double[][] inputData;
    private double[][] targetData;

    /**
     * Constructs a Trainer object.
     * 
     * @param neuralNetz   the neural network to be trained
     * @param learningRate the learning rate used for weight updates
     * @param epochs       the number of training iterations
     * @param inputData    the input training data
     * @param targetData   the expected output training data
     */
    public Trainer(NeuralNetz neuralNetz, double learningRate, int epochs, double[][] inputData, double[][] targetData) {
        this.neuralNetz = neuralNetz;
        this.learningRate = learningRate;
        this.epochs = epochs;
        this.inputData = inputData;
        this.targetData = targetData;
    }

    /**
     * Backpropagation algorithm for updating the weights and biases of the neural network.
     * 
     * @param inputs          the input data vector
     * @param optimalOutputs  the expected output vector
     */
    public void train() {
        double bestError = Data.loadBestError("BestError");
        for (int epoch = 0; epoch < epochs; epoch++) {

            for (int dataIndex = 0; dataIndex < inputData.length; dataIndex++) {
                
                double[] inputs = inputData[dataIndex];
                double[] optimalOutputs = targetData[dataIndex];

                double[] outputs = neuralNetz.forward(inputs);
                double[] outputErrors = MathFunctions.errorVector(optimalOutputs, outputs);
                double currentError = model.MathFunctions.meanSquaredError(optimalOutputs, outputs);

                double[][] weightsHiddenOutput = neuralNetz.getWeightsHiddenOutput();
                double[] hiddenOutput = neuralNetz.getHiddenOutput();
                double[] biasOutput = neuralNetz.getBiasOutput();
                double[][] weightsInputHidden = neuralNetz.getWeightsInputHidden();
                double[] biasHidden = neuralNetz.getBiasHidden();

                if (epoch % 1000 == 0) {
                    System.out.println("Epoch: " + epoch + " Current Error: " + currentError);
                    System.out.println("Outputs at Epoch  " + epoch + " [" + outputs[0] +", "+ outputs[1] + "]");
                }

                if (currentError < bestError) {
                    bestError = currentError;
                   
                    Data.saveToFile(neuralNetz.getWeightsInputHidden(), "model/Data/M/apple/weightsInputHidden.txt");
                    Data.saveToFile(neuralNetz.getWeightsHiddenOutput(), "model/Data/M/apple/weightsHiddenOutput.txt");
                    Data.saveToFile(neuralNetz.getBiasHidden(), "model/Data/M/apple/biasHidden.txt");
                    Data.saveToFile(neuralNetz.getBiasOutput(), "model/Data/M/apple/biasOutput.txt");
                    Data.saveBestError(bestError,"model/Data/M/apple/BestError.txt");

                    System.out.println("New best model saved with error: " + bestError);
                }
                // Calculate delta for output layer
                double[] deltaOutputs = new double[outputs.length];
                for (int i = 0; i < outputs.length; i++) {
                    deltaOutputs[i] = outputErrors[i] * MathFunctions.reluDerivative(outputs[i]);
                }

                // Calculate delta for hidden layer
                double[] deltaHidden = new double[hiddenOutput.length];
                for (int i = 0; i < hiddenOutput.length; i++) {
                    double sum = 0;
                    for (int j = 0; j < deltaOutputs.length; j++) {
                        sum += weightsHiddenOutput[j][i] * deltaOutputs[j];
                    }
                    deltaHidden[i] = sum * MathFunctions.reluDerivative(hiddenOutput[i]);
                }

                // Update weights between input and hidden layer
                for (int i = 0; i < weightsInputHidden.length; i++) {
                    for (int j = 0; j < inputs.length; j++) {
                        weightsInputHidden[i][j] -= learningRate * deltaHidden[i] * inputs[j];
                    }
                }
                neuralNetz.setWeightsInputHidden(weightsInputHidden);

                // Update weights between hidden and output layer
                for (int i = 0; i < weightsHiddenOutput.length; i++) {
                    for (int j = 0; j < weightsHiddenOutput[i].length; j++) {
                        double gradient = deltaOutputs[i] * hiddenOutput[j];
                        weightsHiddenOutput[i][j] -= learningRate * gradient;
                    }
                }
                neuralNetz.setWeightsHiddenOutput(weightsHiddenOutput);

                // Update biases for output layer
                for (int i = 0; i < biasOutput.length; i++) {
                    biasOutput[i] -= learningRate * deltaOutputs[i];
                }
                neuralNetz.setBiasOutput(biasOutput);

                // Update biases for hidden layer
                for (int i = 0; i < biasHidden.length; i++) {
                    biasHidden[i] -= learningRate * deltaHidden[i];
                }
                neuralNetz.setBiasHidden(biasHidden);
            }
        }
    }

}