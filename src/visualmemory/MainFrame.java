/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package visualmemory;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Bykov_SP
 */
public class MainFrame extends Frame implements WindowListener{
    
    GraphicButton redButton, yellowButton, greenButton, blueButton;
    
    public static void main(String [] args){
        Frame frame  = new MainFrame();
    }
    
    public MainFrame(){
        setTitle("VisualMemory");
        setSize(350, 300);
        setResizable(false);
        
        redButton = new GraphicButton(getSize().width - 40, 65, Color.red);
        yellowButton = new GraphicButton(getSize().width - 40, 100, Color.orange);
        greenButton = new GraphicButton(getSize().width - 40, 135, Color.green);
        blueButton = new GraphicButton(getSize().width - 40, 170, Color.blue);
        
        new CubicFrame(this);
        
        addWindowListener(this);
        setVisible(true);
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
    
}
