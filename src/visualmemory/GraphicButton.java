package visualmemory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Waddle
 */
public class GraphicButton {

    final int HEIGHT = 30;
    final int LENGTH = 30;
    int posX, posY;
    boolean isPressed;
    Color colour;

    GraphicButton(int x, int y, Color colour) {
        posX = x;
        posY = y;
        this.colour = colour;
    }

    public boolean isWithin(Point p) {
        if ((posX <= p.getX()) & (p.getX() <= (posX + LENGTH))
                & (posY <= p.getY()) & (p.getY() <= (posY + HEIGHT))) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(colour);
        g.fillRect(posX, posY, LENGTH, HEIGHT);
    }

    public void isPressBtn(Color colour, Graphics g) {
        if (colour == this.colour) {
            g.setColor(Color.black);
            g.drawRect(posX - 1, posY - 1, LENGTH + 1, HEIGHT + 1);
        } else {
            g.setColor(Color.white);
            g.drawRect(posX - 1, posY - 1, LENGTH + 1, HEIGHT + 1);
        }
    }
}
