class Trainer{
    private NeutralNetz neutralNetz;
    private double learningRate;
    
    private int epochs;

    private double[][] trainingInputs;
    private double[][] trainingLabels;

    public Trainer(NeuralNetwork network, double learningRate, int epochs,double[][] trainingInputs, double[][] trainingLabels) {
        this.network = network;
        this.learningRate = learningRate;
        this.epochs = epochs;
        this.trainingInputs = trainingInputs;
        this.trainingLabels = trainingLabels;
    }


    public double[] backP(double[] optimalOutputs){
        double errors = MathFunctions.meanSquaredError(optimalOutputs, outputs);
        double []  slopeOutputLayer = MathFunctions.reluDerivative(outputs);
         // Calculate Gradient for weightsHiddenOutput
        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < hiddenSize; j++) {
                weightsHiddenOutput[i][j] = outputErrors[i] * hiddenOutput[j];
            }
        }
    }
    
}
public class TrainerMain{
    public static void main(String[] args) {
        NeturalNetz netz = new NeturalNetz(5, 3, 2);
        double[] inputs = randomVektor(5);
        double [] output = netz.forwardP(inputs);
        for(double i : output) System.out.print(i + "  ");
        System.out.println();
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