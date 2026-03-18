import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class AniNeko {

    static JFrame frame;
    static JLabel label;

    static ImageIcon[] walkFrames;
    static ImageIcon[] idleFrames;
    static ImageIcon sleepFrame;

    static int currentFrame = 0;
    static int animationCounter = 0;
    static int idleCounter = 0;

    static final int ANIMATION_SPEED = 4;
    static final int SLEEP_THRESHOLD = 40;

    static double catX = 0;
    static double catY = 0;

    static boolean isManualSleep = false;

    public static void main(String[] args) {
        frame = new JFrame("AniNeko");
        frame.setSize(200, 200);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String path = "catimages" + File.separator;
        walkFrames = new ImageIcon[]{
                new ImageIcon(path + "cat1.png"), new ImageIcon(path + "cat2.png"),
                new ImageIcon(path + "cat3.png"), new ImageIcon(path + "cat4.png")
        };
        idleFrames = new ImageIcon[]{
                new ImageIcon(path + "cat5.png"), new ImageIcon(path + "cat6.png")
        };
        sleepFrame = new ImageIcon(path + "cat7.png");

        label = new JLabel(walkFrames[0]);
        frame.add(label);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
            }
        });

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    isManualSleep = true;
                    catX = 20;
                    catY = Toolkit.getDefaultToolkit().getScreenSize().height - 120;
                    frame.setLocation((int)catX, (int)catY);
                    label.setIcon(sleepFrame);
                }
            }
        });

        frame.setVisible(true);
        startCATLoop();
    }

    public static void startCATLoop() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        new Timer(50, e -> {
            Point p = MouseInfo.getPointerInfo().getLocation();

            if (isLikelyFullscreen() || isManualSleep) {
                catX = 10;
                catY = screenHeight - 110;
                frame.setLocation((int)catX, (int)catY);
                label.setIcon(sleepFrame);

                double distToCorner = Math.sqrt(Math.pow(p.x - catX, 2) + Math.pow(p.y - catY, 2));
                if (distToCorner < 50) {
                    isManualSleep = false;
                }
                return;
            }

            double dx = p.x - catX;
            double dy = p.y - catY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < 5) {
                idleCounter++;
                if (idleCounter >= SLEEP_THRESHOLD) {
                    label.setIcon(sleepFrame);
                } else {
                    handleAnimation(idleFrames);
                }
            } else {
                idleCounter = 0;
                catX += (dx * 0.05);
                catY += (dy * 0.05);
                frame.setLocation((int) catX, (int) catY);
                handleAnimation(walkFrames);
            }
        }).start();
    }

    private static boolean isLikelyFullscreen() {
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
        return insets.bottom == 0;
    }

    private static void handleAnimation(ImageIcon[] frames) {
        animationCounter++;
        if (animationCounter >= ANIMATION_SPEED) {
            currentFrame = (currentFrame + 1) % frames.length;
            label.setIcon(frames[currentFrame]);
            animationCounter = 0;
        }
    }
}
