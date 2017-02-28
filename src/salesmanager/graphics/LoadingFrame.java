/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Lorenzo
 */
public class LoadingFrame extends JFrame {

    private static int step;
    private static LoadingFrame frame;
    private static JPanel panel;
    private static ThreadPainter threadPainter;

    public static void setPercent(int i) {
        step = i*360/100;
        panel.repaint();
    }

    private LoadingFrame() throws HeadlessException {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(200, 200);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        panel = new JPanel() {

            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setComposite(AlphaComposite.Clear);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setComposite(AlphaComposite.Src);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.PLAIN, 30));
                g.drawString("Caricamento", getWidth()/2-g2d.getFontMetrics().stringWidth("Caricamento")/2, g2d.getFontMetrics().getHeight()+5);
                g.setColor(new Color(255, 0, 0));
                ((Graphics2D) g).rotate((double) step / 360 * Math.PI * 2, getWidth() / 2, getHeight() / 2);
                g.fillOval(getWidth() / 2, 50, 10, 10);
            }

        };
        panel.setBackground(new Color(0, 0, 0, 0));
        this.add(panel);
    }

    public static void makeLoadingFrame() {
        if (frame == null) {
            frame = new LoadingFrame();
        }
        threadPainter = new ThreadPainter();
        threadPainter.start();
        frame.setVisible(true);
    }

    public static void destroyLoadingFrame() {
        threadPainter.end();
        frame.setVisible(false);
    }

    private static class ThreadPainter extends Thread {

        private boolean stopped;

        public ThreadPainter() {
            stopped = false;
        }

        @Override
        public void run() {
            step = 0;
            while (!stopped) {
                panel.repaint();
                //step = (step + 10) % 360;
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ex) {
                }
            }
        }

        public void end() {
            stopped = true;
        }
    }

}
