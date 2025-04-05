package lab13;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ZombieFactory implements SpriteFactory{
    private static ZombieFactory instance;
    BufferedImage tape;

    private ZombieFactory() {
        try{
            tape = ImageIO.read(getClass().getResource("\\resources\\walkingdead.png"));//new DrawPanel(Main.class.getResource("\\resources\\walkingdead.png"), );
        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load zombie sprite sheet");
        }
    }

    public Sprite newSprite(int x,int y){
        double scale = ( Math.random() * (2. - 0.2) + 0.2 );// wylosuj liczbę z zakresu 0.2 do 2.0
        try {
            return new Zombie(x, y, scale, tape);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ZombieFactory getInstance() {
        if (instance == null) {
            synchronized (ZombieFactory.class) {
                if (instance == null) {
                    instance = new ZombieFactory();
                }
            }
        }
        return instance;
    }
}
