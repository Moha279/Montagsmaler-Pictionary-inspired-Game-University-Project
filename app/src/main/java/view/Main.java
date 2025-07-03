import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Montagsmaler");
        frame.setSize(850, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(true);

        // Zeichenfeld (etwas größer & mittig links)
        ZeichenPanel panel = new ZeichenPanel();
        panel.setBounds(50, 50, 350, 350);
        frame.add(panel);

        // Diagramm (kompakter & schön rechts oben)
        DiagramPanel diagramm = new DiagramPanel();
        diagramm.setBounds(450, 50, 300, 250);
        frame.add(diagramm);

        // TEST Button unten rechts
        JButton testButton = new JButton("TEST");
        testButton.setBounds(650, 400, 100, 35);
        testButton.setBackground(new Color(180, 220, 250));
        // testButton.addActionListener(e -> {
        //     double[] werte = {0.78, 0.22, 0.95, 0.40, 0.66};
        //     diagramm.setWerte(werte);
        //     panel.printArray();
        // });
        testButton.addActionListener(e -> {
            double[] werte = panel.printArray();
            diagramm.setWerte(werte);
        });
        frame.add(testButton);

        // CLEAR Button neben TEST
        JButton clearButton = new JButton("CLEAR");
        clearButton.setBounds(530, 400, 100, 35);
        clearButton.setBackground(new Color(220, 220, 220));
        clearButton.addActionListener(e -> {
            double[] werte = {0.00, 0.00, 0.00, 0.00, 0.00};
            diagramm.setWerte(werte);
            panel.clear();
        });
        frame.add(clearButton);

        // Hintergrundfarbe dezent
        frame.getContentPane()
        .setBackground(new Color(50, 50, 50));
        frame.setVisible(true);
    }
}
