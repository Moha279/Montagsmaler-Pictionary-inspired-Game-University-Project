package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ImageUtils} utility class.
 *
 * <p>This test class verifies the correctness and robustness of the method
 * {@code downscale28x28To14x14(byte[] input)}, which downsamples a flat 28x28
 * image (stored in a 1D byte array of length 784) to a 14x14 version (1D byte array of length 196).</p>
 *
 * <p>The tests ensure:</p>
 * <ul>
 *   <li>The output has the correct size (196 values for 14x14)</li>
 *   <li>The pixel averaging logic works as expected</li>
 *   <li>An exception is thrown if the input size is invalid</li>
 * </ul>
 *
 * <p>These tests help prevent regressions and verify that the method works correctly under normal and edge cases.</p>
 */
public class ImageUtilsTest {

    /**
     * Verifies that the output of downscaling a 28x28 matrix returns a 14x14 result,
     * and that values are averaged correctly.
     *
     * <p>This test uses an input filled entirely with the value 4.
     * Each 2x2 block will have the sum 16, and the average 4, so every pixel in the
     * result should also be 4.</p>
     */
    @Test
    public void testDownscaleReturnsCorrectSize() {
        byte[] input = new byte[784];
        for (int i = 0; i < input.length; i++) {
            input[i] = 4;
        }

        byte[] output = ImageUtils.downscale28x28To14x14(input);

        assertNotNull(output, "Output should not be null.");
        assertEquals(196, output.length, "Output length should be 196 (14x14).");

        for (byte b : output) {
            assertEquals(4, b, "Each averaged value should be 4.");
        }
    }

    /**
     * Verifies that the method throws an IllegalArgumentException
     * if the input is not of length 784.
     */
    @Test
    public void testDownscaleThrowsExceptionForWrongSize() {
        byte[] wrongSizeInput = new byte[100];

        assertThrows(IllegalArgumentException.class, () -> {
            ImageUtils.downscale28x28To14x14(wrongSizeInput);
        }, "Expected IllegalArgumentException for input not of size 784.");
    }
}
