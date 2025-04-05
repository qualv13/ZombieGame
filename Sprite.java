package lab13;

import javax.swing.*;
import java.awt.*;

public interface Sprite {
    /**
     * Rysuje postać
     * @param g
     * @param parent
     */
    void draw(Graphics g, JPanel parent);

    /**
     * Przechodzi do następnej klatki
     */
    void next();

    /**
     * Czy już zniknął z ekranu
     * @return
     */
    boolean isVisible();

    /**
     * Czy punkt o współrzędnych _x, _y leży w obszarze postaci -
     * czyli czy trafiliśmy ją strzelając w punkcie o tych współrzednych
     * @param _x
     * @param _y
     * @return
     */
    boolean isHit(int _x,int _y);

    /** Czy jest bliżej widza niż other, czyli w naszym przypadku czy jest większy,
     * czyli ma wiekszą skalę...
     *
     * @param other
     * @return
     */
    boolean isCloser(Sprite other);

}
