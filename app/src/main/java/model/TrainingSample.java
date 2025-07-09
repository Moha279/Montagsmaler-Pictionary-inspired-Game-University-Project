package model;

/**
 * Represents a single training example consisting of an input vector
 * and a corresponding target output vector.
 */
public class TrainingSample {

    /** The input feature vector */
    public double[] input;

    /** The target output vector (e.g., one-hot encoded label) */
    public double[] target;

    /**
     * Constructs a TrainingSample with the given input and target.
     *
     * @param input the input feature vector
     * @param target the expected output vector
     */
    public TrainingSample(double[] input, double[] target) {
        this.input = input;
        this.target = target;
    }
}
