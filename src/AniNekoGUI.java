import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AniNekoGUI extends JFrame {

    public AniNekoGUI() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(640, 480);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(245, 245, 248));
                g2.fillRoundRect(50, 20, 540, 440, 40, 40);

                g2.setStroke(new BasicStroke(2f));
                g2.setColor(new Color(255, 255, 255, 200));
                g2.fillRoundRect(120, 40, 400, 80, 80, 80);

                g2.setColor(new Color(200, 200, 200, 150));
                g2.drawRoundRect(120, 40, 400, 80, 80, 80);

                g2.setColor(Color.white);
                g2.fillRoundRect(230, 260, 180, 50, 25, 25);

                g2.setFont(new Font("SansSerif", Font.BOLD, 45));
                GradientPaint rainbow = new GradientPaint(200, 0, Color.PINK, 400, 0, Color.CYAN);
                g2.setPaint(rainbow);
                g2.drawString("AniNeko", 230, 95);

                g2.setColor(Color.BLUE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 15));
                g2.drawString("Begin UwU", 280, 288);
            }
        };

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (x >= 230 && x <= 410 && y >= 260 && y <= 310) {
                    dispose();
                    new AniNeko().setVisible(true);
                }
            }
        });

        mainPanel.setOpaque(false);
        this.add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AniNekoGUI().setVisible(true));
    }
}