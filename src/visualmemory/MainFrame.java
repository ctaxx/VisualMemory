/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package visualmemory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Bykov_SP
 */
public class MainFrame extends Frame implements WindowListener, MouseListener{
    
    GraphicButton redButton, yellowButton, greenButton, blueButton;
    
    CubicGame cubicGame;
    
    Color currentColor;
    
    public static void main(String [] args){
        Frame frame  = new MainFrame();
    }
    
    public MainFrame(){
        setTitle("VisualMemory");
        setSize(350, 300);
        setResizable(false);
        
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        
        redButton = new GraphicButton(getSize().width - 40, 65, Color.red);
        yellowButton = new GraphicButton(getSize().width - 40, 100, Color.orange);
        greenButton = new GraphicButton(getSize().width - 40, 135, Color.green);
        blueButton = new GraphicButton(getSize().width - 40, 170, Color.blue);
        
        cubicGame = new CubicGame(this);
        
        addMouseListener(this);
        addWindowListener(this);
        setVisible(true);
    }
    
    @Override
    public void paint(Graphics g) {
        drawBtns();
        drawBtnsRects(currentColor);
    }
    
        private void drawBtns() {
        Graphics g = getGraphics();

        redButton.draw(g);
        yellowButton.draw(g);
        greenButton.draw(g);
        blueButton.draw(g);
    }

    private void drawBtnsRects(Color currentClr) {
        Graphics g = getGraphics();
        redButton.isPressBtn(currentClr, g);
        yellowButton.isPressBtn(currentClr, g);
        greenButton.isPressBtn(currentClr, g);
        blueButton.isPressBtn(currentClr, g);
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        System.exit(0);}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        if (point.getX() >= (getSize().width - 40)) {
            if (redButton.isWithin(point)) {
                currentColor = Color.red;
            }
            if (yellowButton.isWithin(point)) {
                currentColor = Color.orange;
            }
            if (greenButton.isWithin(point)) {
                currentColor = Color.green;
            }
            if (blueButton.isWithin(point)) {
                currentColor = Color.blue;
            }
            drawBtnsRects(currentColor);
        } else {
            int i = (int) point.getX() / 25 - 1;
            int j = (int) point.getY() / 25 - 3;

            if (cubicGame.checkCell(i, j, currentColor)) {

                Graphics g = getGraphics();
                g.setColor(currentColor);
                g.fillRect((i + 1) * 25, (j + 3) * 25, 24, 24);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
