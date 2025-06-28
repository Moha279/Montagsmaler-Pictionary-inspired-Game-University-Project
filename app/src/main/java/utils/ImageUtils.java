package utils;

import java.util.logging.Logger;

/**
 * Utility class for image processing tasks such as resizing or normalization.
 */
public class ImageUtils {

    private static final Logger logger = Logger.getLogger(ImageUtils.class.getName());

    /**
     * Downscale a 28x28 pixel matrix (2D byte array) to 14x14 by averaging each 2x2 block.
     *
     * @param input 2D byte array of size 28x28 representing the input image.
     * @return 2D byte array of size 14x14 with the downscaled image.
     * @throws IllegalArgumentException if input is null or not 28x28.
     */
    public static byte[][] downscale28x28To14x14(byte[][] input) {
        if (input == null || input.length != 28 || input[0].length != 28) {
            throw new IllegalArgumentException("Input must be a 28x28 matrix.");
        }

        byte[][] output = new byte[14][14];

        for (int row = 0; row < 14; row++) {
            for (int col = 0; col < 14; col++) {
                int sum = 0;
                for (int dy = 0; dy < 2; dy++) {
                    for (int dx = 0; dx < 2; dx++) {
                        sum += Byte.toUnsignedInt(input[row * 2 + dy][col * 2 + dx]);
                    }
                }
                int average = sum / 4;
                output[row][col] = (byte) average;
            }
        }

        logger.info("Downscaled 28x28 matrix to 14x14.");

        return output;
    }
}