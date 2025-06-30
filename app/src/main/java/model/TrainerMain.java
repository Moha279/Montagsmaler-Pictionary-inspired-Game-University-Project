package model;
import java.util.*;

import model.NeuralNetz;
import model.Trainer;
import model.Data.*;

public class TrainerMain{
    public static void main(String[] args) {
        double[][] inputs = Data.loadMatrix("model\\Data\\M\\quickdraw_project\\input_vectors\\apple_input.json");
        double[][] outputs = inistalier(100, 2);
        NeuralNetz neuralNetz = new NeuralNetz(inputs[0].length,2, 2);
        Trainer trainer = new Trainer(neuralNetz,0.1, 1000, inputs, outputs);
        trainer.train();
    }
    public static double[][] inistalier(int size1, int size2) {
        double result[][] = new double[size1][size2];
        for (int i = 0; i < size1; i++) {
            result[i][0] = 1.0;
        }
        return result;
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