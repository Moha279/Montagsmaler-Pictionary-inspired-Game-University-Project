package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImageUtilsTest {

    @Test
    public void testDownscaleReturnsCorrectSize() {
        byte[] input = new byte[784];
        for (int i = 0; i < input.length; i++) {
            input[i] = 4;
        }

        byte[] output = ImageUtils.downscale28x28To14x14(input);

        assertNotNull(output);
        assertEquals(196, output.length);

        for (byte b : output) {
            assertEquals(4, b);
        }
    }

    @Test
    public void testDownscaleThrowsExceptionForWrongSize() {
        byte[] wrongSizeInput = new byte[100];
        assertThrows(IllegalArgumentException.class, () -> {
            ImageUtils.downscale28x28To14x14(wrongSizeInput);
        });
    }
}
