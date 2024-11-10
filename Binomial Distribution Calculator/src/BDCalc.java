import javax.swing.*;
import java.awt.*;

public class BDCalc extends JFrame {
    private JTextField nField, pField, kField;
    private JButton calcButton;
    private GraphPanel graphPanel;

    public BDCalc() {
        setTitle("Binomial Distribution Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());
        setResizable(true);

        JPanel inputPanel = new JPanel(new FlowLayout());
        nField = new JTextField(5);
        pField = new JTextField(5);
        kField = new JTextField(5);

        inputPanel.add(new JLabel("n:"));
        inputPanel.add(nField);
        inputPanel.add(new JLabel("p:"));
        inputPanel.add(pField);
        inputPanel.add(new JLabel("k:"));
        inputPanel.add(kField);

        calcButton = new CustomButton("Calculate");
        inputPanel.add(calcButton);

        add(inputPanel, BorderLayout.NORTH);

        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        ControlPanel controlPanel = new ControlPanel(graphPanel);
        add(controlPanel, BorderLayout.EAST);

        calcButton.addActionListener(e -> calculateAndPlot());

        setVisible(true);
    }

    private void calculateAndPlot() {
        try {
            int n = Integer.parseInt(nField.getText());
            double p = Double.parseDouble(pField.getText());
            int k = Integer.parseInt(kField.getText());

            if (p < 0 || p > 1) {
                JOptionPane.showMessageDialog(this, "p must be between 0 and 1.");
                return;
            }

            double binomProb = BDCalc.binomialProbability(n, p, k);
            JOptionPane.showMessageDialog(this, "Binomial Probability P(X = " + k + ") = " + binomProb);

            graphPanel.setParameters(n, p);
            graphPanel.repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers");
        }
    }

    static double binomialProbability(int n, double p, int k) {
        return combination(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    private static long combination(int n, int k) {
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - i + 1) / i;
        }
        return result;
    }
}
