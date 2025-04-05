package lab13;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        JFrame frame = new JFrame("Zombie");
        SpriteFactory factory = ZombieFactory.getInstance();
        DrawPanel panel = new DrawPanel(Main.class.getResource("\\resources\\background_img.jpg"), factory);
        frame.setContentPane(panel);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // tu zatrzymaj watek
                // ale nie za pomocą metody stop() !!!
                System.exit(0);

                //zatrzymaj też timer celownika za pomocą cancel(). Timer to także wątek...

            }
        });
    }
}
