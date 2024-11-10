import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

public class GraphPanel extends JPanel {
    private int n;
    private double p;
    private GraphMode currentMode = GraphMode.BAR;

    public void setParameters(int n, double p) {
        this.n = n;
        this.p = p;
        repaint();
    }

    public void setGraphMode(GraphMode mode) {
        this.currentMode = mode;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (n > 0 && p >= 0 && p <= 1) {
            double[] probabilities = new double[n + 1];
            for (int k = 0; k <= n; k++) {
                probabilities[k] = BDCalc.binomialProbability(n, p, k);
            }

            double maxProbability = 0;
            for (double prob : probabilities) {
                if (prob > maxProbability) {
                    maxProbability = prob;
                }
            }

            int width = getWidth();
            int height = getHeight();
            int margin = 60;
            int barWidth = Math.max((width - 2 * margin) / (n + 1), 5);
            int maxHeight = height - 2 * margin;

            drawAxes(g2, width, height, maxProbability, margin);

            switch (currentMode) {
                case BAR:
                    drawBarChart(g2, probabilities, barWidth, maxHeight, maxProbability, margin);
                    break;
                case BELL_CURVE:
                    drawBellCurve(g2, n, p, width, height, maxProbability, margin);
                    break;
                case PLOT_POINTS:
                    drawPlotPoints(g2, probabilities, barWidth, maxHeight, maxProbability, margin);
                    break;
            }
        }
    }


    private void drawAxes(Graphics2D g2, int width, int height, double maxProbability, int margin) {
        g2.drawLine(margin, height - margin, margin, margin);
        g2.drawString("Probability", 10, margin);

        for (int i = 0; i <= 5; i++) {
            double prob = maxProbability * i / 5;
            int y = height - margin - (int) ((prob / maxProbability) * (height - 2 * margin));
            g2.drawString(String.format("%.2f", prob), margin - 40, y + 5);
        }

        g2.drawLine(margin, height - margin, width - margin, height - margin);
        g2.drawString("k", width - margin + 10, height - margin + 20);

        int step = Math.max(1, n / 10);
        for (int k = 0; k <= n; k += step) {
            int x = margin + (int) ((double) k / n * (width - 2 * margin));
            g2.drawString("k=" + k, x - 5, height - margin + 15);
        }
    }

    private void drawBarChart(Graphics2D g2, double[] probabilities, int barWidth, int maxHeight, double maxProbability, int margin) {
        for (int k = 0; k <= n; k++) {
            int barHeight = (int) ((probabilities[k] / maxProbability) * maxHeight);
            int x = margin + k * barWidth;
            int y = getHeight() - barHeight - margin;

            g2.setColor(Color.BLACK);
            g2.fill(new Rectangle2D.Double(x, y, barWidth - 2, barHeight));
        }
    }

    private void drawPlotPoints(Graphics2D g2, double[] probabilities, int barWidth, int maxHeight, double maxProbability, int margin) {
        for (int k = 0; k <= n; k++) {
            int pointX = margin + k * barWidth + (barWidth - 2) / 2;
            int pointY = getHeight() - (int) ((probabilities[k] / maxProbability) * maxHeight) - margin;

            g2.setColor(Color.BLACK);
            g2.fillOval(pointX - 3, pointY - 3, 6, 6);
        }
    }

    private void drawBellCurve(Graphics2D g2, int n, double p, int width, int height, double maxProbability, int margin) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        double xScale = (double) (width - 2 * margin) / n;
        double yScale = (height - 2 * margin) / maxProbability;

        GeneralPath bellCurvePath = new GeneralPath();

        for (double k = 0; k <= n; k += 0.1) {
            double prob = BDCalc.binomialProbability(n, p, (int) k);
            int x = margin + (int) (k * xScale);
            int y = (int) (height - (prob * yScale) - margin);
            if (k == 0) {
                bellCurvePath.moveTo(x, y);
            } else {
                bellCurvePath.lineTo(x, y);
            }
        }

        g2.draw(bellCurvePath);
    }

    public enum GraphMode {
        BAR, BELL_CURVE, PLOT_POINTS
    }
}
