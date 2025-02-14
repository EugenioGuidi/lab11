package it.unibo.oop.reactivegui02;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

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

    private static final long serialVersionUID = 2L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;

    private final JLabel display = new JLabel();
    private final JButton up = new JButton("up");
    private final JButton down = new JButton("down");
    private final JButton stop = new JButton("stop");

    private final transient Agent agent;

    /**
     * defencive method to stop agent.
     */
    public void stopAgent() {
        this.agent.stopCounting();
    }

    /**
     * Builds a new CGUI.
     */

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

        this.agent = new Agent();
        new Thread(agent).start();

        up.addActionListener((e) -> agent.setInc());
        down.addActionListener((e) -> agent.setDec());
        stop.addActionListener((e) -> agent.stopCounting());
    }

    /**
     * creation of an agent the count incrementing or decrementing filed count and printing that
     * on GUI.
     */
    public final class Agent implements Runnable {

        private volatile boolean stop;
        private volatile boolean inc;
        private int count;
        private String nextText;

        Agent() {
            this.inc = true;
        }

        @Override
        public void run() {
            while (!this.stop) {
                try {
                    nextText = Integer.toString(count);
                    SwingUtilities.invokeAndWait(() -> ConcurrentGUI.this.display.setText(nextText));
                    if (this.inc) {
                        this.count++;
                    } else {
                        this.count--;
                    }
                    Thread.sleep(100);
                } catch (InvocationTargetException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        /**
         * set field stop to true to stop the count.
         */
        public void stopCounting() {
            this.stop = true;
        }

        private void setInc() {
            this.inc = true;
        }

        private void setDec() {
            this.inc = false;
        }
    }
}
