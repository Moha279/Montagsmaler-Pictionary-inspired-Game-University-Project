<<<<<<< HEAD
package model;
import java.util.*;
=======
class Trainer{
    private NeuralNetwork neuralNetwork;
    private double learningRate;
    
    private int epochs;
>>>>>>> 298e964c612efd3f953e6a1823ebb990bea82970

import model.Trainer;

<<<<<<< HEAD
=======
    public Trainer(NeuralNetwork neuralNetwork, double learningRate, int epochs,double[][] inputData, double[][] targetData) {
        this.neuralNetwork = neuralNetwork;
        this.learningRate = learningRate;
        this.epochs = epochs;
        this.inputData = inputData;
        this.targetData = targetData;
    }


    public void back(double[] inputs,double[] optimalOutputs){
        double [] outputs = neuralNetwork.feedforward(inputs);
        double [] outputErrors = MathFunctions.meanSquaredError(optimalOutputs, outputs);
        double[] hiddenOutput = neuralNetwork.getHiddenOutput();

        for (int i = 0; i < weightsHiddenOutput.length; i++) {
            for (int j = 0; j < weightsHiddenOutput[i].length; j++) {
                weightsHiddenOutput[i][j] += learningRate * outputErrors[i] * hiddenOutput[j];
            }
        }
        neuralNetwork.setWeightsHiddenOutput(weightsHiddenOutput);

        double[] biasOutput = neuralNetwork.getBiasOutput();
        for (int i = 0; i < biasOutput.length; i++) {
            biasOutput[i] += learningRate * outputErrors[i];
        }
        neuralNetwork.setBiasOutput(biasOutput);
    }
    
}
>>>>>>> 298e964c612efd3f953e6a1823ebb990bea82970
public class TrainerMain{
    public static void main(String[] args) {
        double[][] inputs = randomMatrix(3,3);
        double[][] outputs = randomMatrix(2, 2);
        NeuralNetz neuralNetz = new NeuralNetz(3,2, 2);
        Trainer trainer = new Trainer(neuralNetz,0.1, 10, inputs, outputs);
        trainer.train();
    }
    public static double[][] randomMatrix(int size1, int size2) {
        Random randm = new Random();
        double result[][] = new double[size1][size2];
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                result[i][j] = randm.nextDouble(0.0, 1.0);
            }
        }
        return result;
    }

}