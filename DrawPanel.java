package lab13;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrawPanel  extends JPanel implements CrossHairListener{
    BufferedImage background;
    //Zombie zombie;
    final List<Sprite> sprites = new ArrayList<>();
    SpriteFactory factory;
    CrossHair crossHair;
    int hitZombies = 0;
    int missedZombies = 0;
    int escapedZombies = 0;
    DrawPanel(URL backgroundImagageURL, SpriteFactory factory){
        try {
            this.factory=factory;
            crossHair = new CrossHair(this);
            crossHair.addCrossHairListener(this);
            background = ImageIO.read(backgroundImagageURL);

            //factory.newSprite(800, 500); factory.newSprite(1000, 600);
            //Zombie zombie = new Zombie(800, 500, 0.5);
            //sprites.add(zombie); sprites.add(new Zombie(1000, 600, 0.5));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        new AnimationThread().start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        crossHair.draw(g2d);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g2d.drawString("You've hit: " + hitZombies + " Zombies and let escape " + escapedZombies,50,45);
        synchronized (sprites) {
            for(Sprite sprite : sprites){
                sprite.draw(g2d, this);
            }
        }
        //zombie.draw(g2d, this);
    }

    @Override
    public void onShotsFired(int x, int y) {
        Iterator<Sprite> iterator = sprites.iterator();
        while (iterator.hasNext()) {
            Sprite sprite = iterator.next();
            if (sprite.isHit(x, y)) {
                iterator.remove(); // Remove the first hit sprite
                hitZombies++;
                break;
            }
        }
        repaint();
    }

    class AnimationThread extends Thread{
        public void run(){
            for(int i=0;;i++) {
                synchronized (sprites) {
                    if(sprites.removeIf(sprite -> !sprite.isVisible())){
                        escapedZombies++;
                    }
                    for (Sprite sprite : sprites) {
                        sprite.next();
                    }
//                    if (i % 30 == 0) {
//                        sprites.add(factory.newSprite(getWidth(), (int) (Math.random() * getHeight())));
//                    }
                }
                repaint();
//                zombie.next();
//                repaint();
                try {
                    sleep(1000 / 30);  // 30 klatek na sekundę
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i%30==0) {
                    synchronized (sprites) {
                        int yPosition = (int) (Math.random() * (350 - getHeight()/2) + getHeight()/2 );
                        sprites.add(factory.newSprite(getWidth(),yPosition));
                        sprites.sort((o1,o2) -> {
                            if(o1.isCloser(o2)){
                                return 1;
                            }else if(o2.isCloser(o1)){
                                return -1;
                            }else{
                                return 0;
                            }
                        });
                    }

                }
            }
        }
    }
}