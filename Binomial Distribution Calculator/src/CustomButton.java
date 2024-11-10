import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class CustomButton extends JButton {
    private Color backgroundColor = Color.DARK_GRAY;
    private Color hoverColor = Color.GRAY;
    private Color clickColor = Color.LIGHT_GRAY;

    public CustomButton(String s){
        super(s);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorder(new EmptyBorder(0,0,0,0));

        setPreferredSize(new Dimension(100,  50));
        setForeground(Color.white);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backgroundColor = hoverColor;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backgroundColor = Color.DARK_GRAY;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                backgroundColor = clickColor;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                backgroundColor = hoverColor;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Area area = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
        g2.setColor(backgroundColor);
        g2.fill(area);

        FontMetrics fm = g2.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

        g2.setColor(getForeground());
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }
}
