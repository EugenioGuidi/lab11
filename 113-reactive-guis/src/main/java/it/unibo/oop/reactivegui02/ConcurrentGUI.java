package it.unibo.oop.reactivegui02;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Second example of reactive GUI.
 */
@SuppressWarnings("PMD.AvoidPrintStackTrace")
public final class ConcurrentGUI extends JFrame {

    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;

    private final JLabel display = new JLabel();
    private final JButton up = new JButton("up");
    private final JButton down = new JButton("down");
    private final JButton stop = new JButton("stop");

    public ConcurrentGUI() {
        super();
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screensize.getWidth() * WIDTH_PERC), (int) (screensize.getHeight() * HEIGHT_PERC));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final JPanel panel = new JPanel();
        panel.add(display);
        panel.add(up);
        panel.add(down);
        panel.add(stop);
        this.getContentPane().add(panel);
        this.setVisible(true);
    }
}
