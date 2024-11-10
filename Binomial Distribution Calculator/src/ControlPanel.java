import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private GraphPanel graphPanel;

    public ControlPanel(GraphPanel gPanel) {
        this.graphPanel = gPanel;

        GridLayout cPanelLayout = new GridLayout(3, 1);
        cPanelLayout.setVgap(25);
        setLayout(cPanelLayout);

        CustomButton barChartButton = new CustomButton("Bar Chart");
        CustomButton bellCurveButton = new CustomButton("Bell Curve");
        CustomButton plotPointsButton = new CustomButton("Plot Points");

        barChartButton.addActionListener(e -> this.graphPanel.setGraphMode(GraphPanel.GraphMode.BAR));
        bellCurveButton.addActionListener(e -> this.graphPanel.setGraphMode(GraphPanel.GraphMode.BELL_CURVE));
        plotPointsButton.addActionListener(e -> this.graphPanel.setGraphMode(GraphPanel.GraphMode.PLOT_POINTS));
        add(barChartButton);
        add(bellCurveButton);
        add(plotPointsButton);


    }
}
