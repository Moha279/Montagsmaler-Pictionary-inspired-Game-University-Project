<<<<<<< HEAD
package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import controller.GameController;

/**
 * A custom drawing panel where the user can draw with the mouse.
 * The panel supports resizing, clearing, and converts drawings into 28x28 grayscale matrix input.
 */
public class ZeichenPanel extends JPanel {

    private BufferedImage canvasImage;
    private final int outputSize = 28; // Size for network input
    private int lastX, lastY;
    private boolean isDrawing = false;
    private final float strokeWidth = 16.0f;

    /**
     * Constructor sets up mouse listeners and initializes canvas.
     */
    public ZeichenPanel() {
        setBackground(Color.WHITE);
=======
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ZeichenPanel extends JPanel {

    private BufferedImage canvasImage;
    private final int outputGroesse = 28;
    private int lastX, lastY;

    public ZeichenPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
>>>>>>> 46871be1f16e490c74ac009418c12507e3b7d58b

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                initCanvas();
                repaint();
            }
        });

<<<<<<< HEAD
        // Handle mouse press to begin drawing
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                isDrawing = true;
                lastX = e.getX();
                lastY = e.getY();

                Graphics2D g2 = canvasImage.createGraphics();
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.fillOval(lastX - (int)(strokeWidth / 2), lastY - (int)(strokeWidth / 2),
                            (int) strokeWidth, (int) strokeWidth);
                g2.dispose();
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
            }
        });

        // Handle mouse drag to draw lines
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!isDrawing) return;

                Graphics2D g2 = canvasImage.createGraphics();
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
=======
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Graphics2D g2 = canvasImage.createGraphics();
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
>>>>>>> 46871be1f16e490c74ac009418c12507e3b7d58b
                g2.drawLine(lastX, lastY, e.getX(), e.getY());
                g2.dispose();

                lastX = e.getX();
                lastY = e.getY();
                repaint();
            }
        });

        initCanvas();
    }

<<<<<<< HEAD
    /**
     * Initializes or resizes the canvas image buffer.
     */
    private void initCanvas() {
        int w = Math.max(getWidth(), 1);
        int h = Math.max(getHeight(), 1);

        if (canvasImage == null || canvasImage.getWidth() != w || canvasImage.getHeight() != h) {
            BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = newImage.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, w, h);

            if (canvasImage != null) {
                g.drawImage(canvasImage, 0, 0, null);
            }

            g.dispose();
            canvasImage = newImage;
        }
    }

    /**
     * Paints the canvas image onto the panel.
     */
    @Override
=======
    private void initCanvas() {
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) return;
        canvasImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = canvasImage.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);
        g2.dispose();
    }

>>>>>>> 46871be1f16e490c74ac009418c12507e3b7d58b
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (canvasImage != null) {
            g.drawImage(canvasImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

<<<<<<< HEAD
    /**
     * Converts the current canvas drawing into a double array of classification probabilities.
     * @return probabilities returned by the classifier
     */
    public double[] printArray() {
        if (canvasImage == null) return new double[] {0, 0, 0, 0, 0};

        double[][] pixelMatrix = getPixelMatrix();
        return GameController.classifyFromPixelMatrix(pixelMatrix);
    }

    /**
     * Scales the canvas image to 28x28 and converts it into a grayscale pixel matrix.
     * @return a 28x28 matrix of normalized grayscale values
     */
    public double[][] getPixelMatrix() {
        if (canvasImage == null) return new double[outputSize][outputSize];

        Image scaledImage = canvasImage.getScaledInstance(outputSize, outputSize, Image.SCALE_AREA_AVERAGING);
        BufferedImage smallImage = new BufferedImage(outputSize, outputSize, BufferedImage.TYPE_INT_RGB);

        Graphics2D gSmall = smallImage.createGraphics();
        gSmall.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gSmall.drawImage(scaledImage, 0, 0, outputSize, outputSize, null);
        gSmall.dispose();

        double[][] pixelMatrix = new double[outputSize][outputSize];
        for (int y = 0; y < outputSize; y++) {
            for (int x = 0; x < outputSize; x++) {
                int rgb = smallImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                double gray = 1.0 - (r + g + b) / (3.0 * 255.0); // Inverted grayscale
                pixelMatrix[y][x] = gray;
            }
        }

        return pixelMatrix;
    }

    /**
     * Clears the canvas by painting it white.
     */
=======
    // public void printArray() {
    //     if (canvasImage == null) return;

    //     Image scaledImage = canvasImage.getScaledInstance(outputGroesse, outputGroesse, Image.SCALE_SMOOTH);
    //     BufferedImage smallImage = new BufferedImage(outputGroesse, outputGroesse, BufferedImage.TYPE_INT_RGB);

    //     Graphics2D gSmall = smallImage.createGraphics();
    //     gSmall.drawImage(scaledImage, 0, 0, outputGroesse, outputGroesse, null);
    //     gSmall.dispose();

    //     int[][] pixelArray = new int[outputGroesse][outputGroesse];
    //     for (int y = 0; y < outputGroesse; y++) {
    //         for (int x = 0; x < outputGroesse; x++) {
    //             int color = smallImage.getRGB(x, y);
    //             pixelArray[y][x] = (color != Color.WHITE.getRGB()) ? 1 : 0;
    //         }
    //     }

    //     System.out.println("28x28 Array:");
    //     for (int y = 0; y < outputGroesse; y++) {
    //         for (int x = 0; x < outputGroesse; x++) {
    //             System.out.print(pixelArray[y][x] + " ");
    //         }
    //         System.out.println();
    //     }
    // }

    // ZeichenPanel.java - printArray jetzt mit Rückgabe
    public double[] printArray() {
        if (canvasImage == null) return new double[] {0,0,0,0,0};


        Image scaledImage = canvasImage.getScaledInstance(outputGroesse, outputGroesse, Image.SCALE_SMOOTH);
        BufferedImage smallImage = new BufferedImage(outputGroesse, outputGroesse, BufferedImage.TYPE_INT_RGB);

        Graphics2D gSmall = smallImage.createGraphics();
        gSmall.drawImage(scaledImage, 0, 0, outputGroesse, outputGroesse, null);
        gSmall.dispose();

        double[][] pixelArray = new double[outputGroesse][outputGroesse];
        for (int y = 0; y < outputGroesse; y++) {
            for (int x = 0; x < outputGroesse; x++) {
                int color = smallImage.getRGB(x, y);
                pixelArray[y][x] = (color != Color.WHITE.getRGB()) ? 1 : 0;
            }
        }

        // Aufruf der statischen Methode 'finden' aus externer Klasse
        return AndereKlasse.finden(pixelArray);
    }


>>>>>>> 46871be1f16e490c74ac009418c12507e3b7d58b
    public void clear() {
        if (canvasImage == null) return;
        Graphics2D g2 = canvasImage.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());
        g2.dispose();
        repaint();
    }
}
