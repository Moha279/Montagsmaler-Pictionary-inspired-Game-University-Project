package model;
import java.util.*;

import model.Trainer;
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