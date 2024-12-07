package it.unibo.oop.reactivegui03;

import it.unibo.oop.reactivegui02.ConcurrentGUI;
import javax.swing.JFrame;

/**
 * Third experiment with reactive gui.
 */
@SuppressWarnings("PMD.AvoidPrintStackTrace")
public final class AnotherConcurrentGUI extends JFrame {

    private static final int TIME_TO_SLEEP = 10_000;

    private static final long serialVersionUID = 2L;

    /**
     * Builds a new CGUI.
     */

    public AnotherConcurrentGUI() {
        final ConcurrentGUI concurrentGUI = new ConcurrentGUI();
        new Thread(() -> {
            try {
                Thread.sleep(TIME_TO_SLEEP);
                concurrentGUI.stopAgent();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
