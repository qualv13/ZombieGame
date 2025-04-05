package lab13;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Zombie implements Sprite {
    BufferedImage tape;
    int x = 1000;
    int y = 700;
    double scale = 1;

    int index = 0;  // numer wyświetlanego obrazka
    int HEIGHT = 312; // z rysunku;
    int WIDTH = 2000;// z rysunku;
    int frameHeight = 0;
    int frameWidth = 0;

    Zombie(int x, int y, double scale) throws IOException {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape = ImageIO.read(getClass().getResource("\\resources\\walkingdead.png"));

        this.frameWidth = tape.getWidth() / 10;
        this.frameHeight = tape.getHeight();
    }

    Zombie(int x, int y, double scale, BufferedImage tape) throws IOException {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape = tape;

        this.frameWidth = tape.getWidth() / 10;
        this.frameHeight = tape.getHeight();
    }


    /**
     * Pobierz odpowiedni podobraz klatki (odpowiadającej wartości zmiennej index)
     * i wyświetl go w miejscu o współrzędnych (x,y)
     *
     * @param g
     * @param parent
     */

    public void draw(Graphics g, JPanel parent) {
        Image img = tape.getSubimage(index*WIDTH/10,0,WIDTH/10,HEIGHT); // pobierz klatkę
        g.drawImage(img, x, y + 100 - (int) (frameWidth * scale) / 5, (int) (frameHeight * scale), (int) (frameWidth * scale), parent);
    }

    /**
     * Zmień stan - przejdź do kolejnej klatki
     */

    public void next() {
        x -= 20 * scale;
        index = (index + 1) % 10;
    }

    @Override
    public boolean isVisible() {
        return x*0.5 + frameWidth * scale > 0;
    }

    @Override
    public boolean isHit(int _x, int _y) {
        int scaledWidth = (int) (frameWidth * scale);
        int scaledHeight = (int) (frameHeight * scale);

        // bounding box of the zombie
        int left = x;
        int right = x + scaledWidth*2;
        int top = y;
        int bottom = y + scaledHeight*2;
        return _x >= left && _x <= right && _y >= top && _y <= bottom;
    }

    @Override
    public boolean isCloser(Sprite other) {
        if (other instanceof Zombie) {
            Zombie z = (Zombie) other;
            // Compare the scale of this zombie with the other zombie
            return this.scale > z.scale;
        }
        return false;
    }
}
