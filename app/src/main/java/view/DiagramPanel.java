import javax.swing.*;
import java.awt.*;

class DiagramPanel extends JPanel {

    private double[] zielWerte;
    private double[] animierteWerte;
    private final Color[] farben = {
            new Color(70, 130, 180),
            new Color(255, 165, 0),
            new Color(100, 200, 100),
            new Color(220, 20, 60),
            new Color(128, 0, 128)
    };

    private Timer timer;

    public DiagramPanel() {
        this.zielWerte = new double[] {0, 0, 0, 0, 0};
        this.animierteWerte = new double[] {0, 0, 0, 0, 0};
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public void setWerte(double[] neueWerte) {
        if (neueWerte.length != 5) throw new IllegalArgumentException("Genau 5 Werte erwartet!");
        System.arraycopy(neueWerte, 0, zielWerte, 0, 5);

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(20, e -> animateStep());
        timer.start();
    }

    private void animateStep() {
        boolean fertig = true;

        for (int i = 0; i < zielWerte.length; i++) {
            double diff = zielWerte[i] - animierteWerte[i];
            if (Math.abs(diff) > 0.01) {
                animierteWerte[i] += diff * 0.1;  // 10% des Unterschieds pro Schritt
                fertig = false;
            } else {
                animierteWerte[i] = zielWerte[i];
            }
        }

        repaint();

        if (fertig) {
            timer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int breite = getWidth();
        int hoehe = getHeight();
        int saulenBreite = breite / 7;
        int abstand = saulenBreite;

        g.setFont(new Font("Arial", Font.PLAIN, 12));

        for (int i = 0; i < animierteWerte.length; i++) {
            double wert = Math.max(0.0, Math.min(animierteWerte[i], 1.0));
            int saulenHoehe = (int) (wert * (hoehe - 50));

            int x = abstand + i * (saulenBreite + 10);
            int y = hoehe - saulenHoehe - 20;

            g.setColor(farben[i]);
            g.fillRect(x, y, saulenBreite, saulenHoehe);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, saulenBreite, saulenHoehe);

            String text = (int)(wert * 100) + "%";
            g.drawString(text, x + 5, hoehe - 5);
        }
    }
}
