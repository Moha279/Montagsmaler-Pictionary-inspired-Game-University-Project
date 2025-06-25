package utils;

/**
 * Utility class for image processing tasks such as resizing or normalization.
 */
public class ImageUtils {

    /**
     * Downscale a 28x28 pixel matrix (as a 1D byte array) to 14x14 by averaging each 2x2 block.
     *
     * @param input byte array of length 784
     * @return byte array of length 196
     */
    public static byte[] downscale28x28To14x14(byte[] input) {
        if (input == null || input.length != 784) {
            throw new IllegalArgumentException("Input must be a 28x28 (784 bytes) array.");
        }

        byte[] output = new byte[196];

        for (int row = 0; row < 14; row++) {
            for (int col = 0; col < 14; col++) {
                int sum = 0;
                for (int dy = 0; dy < 2; dy++) {
                    for (int dx = 0; dx < 2; dx++) {
                        int inputRow = row * 2 + dy;
                        int inputCol = col * 2 + dx;
                        int index = inputRow * 28 + inputCol;
                        sum += Byte.toUnsignedInt(input[index]);
                    }
                }
                int average = sum / 4;
                output[row * 14 + col] = (byte) average;
            }
        }

        return output;
    }
}
