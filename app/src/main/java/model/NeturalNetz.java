package model;
import java.util.*;
public class NeturalNetz{
    private double[][] weightsInputHidden;
    private double[] biasHidden;

    private double[][] weightsHiddenOutput;
    private double[] biasOutput;

    private int inputSize;
    private int hiddenSize;
    private int outputSize;


    public NeturalNetz(int inputs,int hidden,int outputs){
        inputSize = inputs;
        hiddenSize = hidden;
        outputSize = outputs;
        biasHidden = randomVektor(hidden);
        weightsInputHidden = randomMatrix(hiddenSize,inputSize);
        weightsHiddenOutput = randomMatrix(outputSize,hiddenSize);
        biasOutput = randomVektor(outputSize);
    }

    public void setWeightsInputHidden(double[][] updateWeightsInputHidden){
        this.weightsInputHidden = updateWeightsInputHidden;
    }

    public void setBiasHidden(double[] updateBiasHidden){
        this.biasHidden = updateBiasHidden;
    }

    public void setWeightsHiddenOutput(double[][] updateWeightsHiddenOutput){
        this.weightsHiddenOutput = updateWeightsInputHidden;
    }
    public void setBiasOutput(double[] updateBiasOutput){
        this.biasOutput = updateBiasOutput;
    }

    public double[][] randomMatrix(int size1, int size2){
        Random randm = new Random();
        double result [][] = new double[size1][size2];
        for(int i = 0; i < size1; i++){
            for(int j = 0; j < size2; j++){
                result[i][j] = randm.nextDouble(0.0,1.0);
            } 
        }
        return result;
    }

    public double[] randomVektor(int number){
        Random randm = new Random();
        double  [] result = new double[number];
        for(int i = 0; i < number; i++){
            
            result[i] = randm.nextDouble(0.0,1.0);   
        }
        return result;
    }
    public double[] forwardP(double[] inputs){
        double[] hiddenInput = MathFunctions.add(MathFunctions.multiply(weightsInputHidden, inputs), biasHidden);
        
        double[] hiddenOutput = MathFunctions.applyReLU(hiddenInput);

        double[] finalInput = MathFunctions.add(MathFunctions.multiply(weightsHiddenOutput, hiddenOutput), biasOutput);
    
        return  MathFunctions.softmax(finalInput);
    }
    

}
