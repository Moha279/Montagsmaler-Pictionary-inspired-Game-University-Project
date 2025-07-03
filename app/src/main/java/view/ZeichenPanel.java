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

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                initCanvas();
                repaint();
            }
        });

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
                g2.drawLine(lastX, lastY, e.getX(), e.getY());
                g2.dispose();

                lastX = e.getX();
                lastY = e.getY();
                repaint();
            }
        });

        initCanvas();
    }

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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (canvasImage != null) {
            g.drawImage(canvasImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

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


    public void clear() {
        if (canvasImage == null) return;
        Graphics2D g2 = canvasImage.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());
        g2.dispose();
        repaint();
    }
}
