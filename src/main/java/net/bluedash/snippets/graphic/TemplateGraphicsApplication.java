package net.bluedash.snippets.graphic;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: weli
 * Date: 4/17/12
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class TemplateGraphicsApplication extends JFrame {
    public TemplateGraphicsApplication() {
        super("Template Graphics Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        setVisible(true);

        Insets insets = getInsets();
        DISPLAY_X = insets.left;
        DISPLAY_Y = insets.top;
        resizeToInternalSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    }


    public void resizeToInternalSize(int internalWidth, int
            internalHeight) {
        Insets insets = getInsets();
        final int newWidth = internalWidth + insets.left +
                insets.right;
        final int newHeight = internalHeight + insets.top +
                insets.bottom;

        Runnable resize = new Runnable() {
            public void run() {
                setSize(newWidth, newHeight);
            }
        };

        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(resize);
            } catch (Exception e) {
            }
        } else
            resize.run();

        validate();
    }


    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.translate(DISPLAY_X, DISPLAY_Y);

        g2D.setColor(Color.blue);
        g2D.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

        g2D.setColor(Color.white);
        g2D.fillRect(DISPLAY_WIDTH / 4, DISPLAY_HEIGHT / 4,
                DISPLAY_WIDTH / 2, DISPLAY_HEIGHT / 2);
    }

    public static void main(String[] args) {
        new TemplateGraphicsApplication();
    }

    private final int DISPLAY_X; // value assigned in constructor
    private final int DISPLAY_Y; // value assigned in constructor
    private static final int DISPLAY_WIDTH = 400;
    private static final int DISPLAY_HEIGHT = 400;
}

