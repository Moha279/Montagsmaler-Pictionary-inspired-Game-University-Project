class Trainer{
    private NeuralNetwork neuralNetwork;
    private double learningRate;
    
    private int epochs;

    private double[][] inputData;
    private double[][] targetData;

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
public class TrainerMain{
    public static void main(String[] args) {
        
    }
    public static double[] randomVektor(int number){
        Random randm = new Random();
        double  [] result = new double[number];
        for(int i = 0; i < number; i++){
            
            result[i] = randm.nextDouble(0.0,1.0);   
        }
        return result;
    }
}