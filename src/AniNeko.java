import javax.swing.*;
import java.awt.*;

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
    static final int SLEEP_THRESHOLD = 20;

    static double catX = 0;
    static double catY = 0;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setSize(200, 200);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String path = "C:\\Users\\yanir\\IdeaProjects\\neko\\catimages\\";

        walkFrames = new ImageIcon[]{
                new ImageIcon(path + "cat1.png"), new ImageIcon(path + "cat2.png"),
                new ImageIcon(path + "cat3.png"), new ImageIcon(path + "cat4.png")
        };

        idleFrames = new ImageIcon[]{
                new ImageIcon(path + "cat5.png"),
                new ImageIcon(path + "cat6.png")
        };

        sleepFrame = new ImageIcon(path + "cat7.png");

        label = new JLabel(walkFrames[0]);
        frame.add(label);
        frame.setVisible(true);

        startCATLoop();
    }

    public static void startCATLoop() {
        new Timer(50, e -> {
            Point p = MouseInfo.getPointerInfo().getLocation();

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

    private static void handleAnimation(ImageIcon[] frames) {
        animationCounter++;
        if (animationCounter >= ANIMATION_SPEED) {
            currentFrame = (currentFrame + 1) % frames.length;
            label.setIcon(frames[currentFrame]);
            animationCounter = 0;
        }
    }
}