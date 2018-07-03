
package visualmemory;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;

/**
 *
 * @author Waddle
 */
public class CubicFrame extends Frame implements WindowListener, ActionListener,
    MouseListener{

    Button answerBtn, okBtn, startBtn;
    Choice numColoursChoice, numColsChoice, numRowsChoice;

    Color currentColor, taskArr[][], answerArr[][];

    GraphicButton redButton, yellowButton, greenButton, blueButton;
    
    int cols = 4, rows = 4;

    Writer sout;

    public static void main(String[] args) {
        CubicFrame myFrame = new CubicFrame();
    }

    CubicFrame(){
        setTitle("VisualMemoryCubes");
        setSize(350,300);
        setResizable(false);

        redButton = new GraphicButton(getSize().width-40, 65, Color.red);
        yellowButton = new GraphicButton(getSize().width-40, 100, Color.yellow);
        greenButton = new GraphicButton(getSize().width-40, 135, Color.green);
        blueButton = new GraphicButton(getSize().width-40, 170, Color.blue);
        
        numColoursChoice = new Choice();
        numColoursChoice.add("2");
        numColoursChoice.add("3");
        numColoursChoice.add("4");
        
        numColsChoice = new Choice();
        numColsChoice.add("1");
        numColsChoice.add("2");
        numColsChoice.add("3");
        numColsChoice.add("4");
        numColsChoice.add("5");
        
        numRowsChoice = new Choice();
        numRowsChoice.add("1");
        numRowsChoice.add("2");
        numRowsChoice.add("3");
        numRowsChoice.add("4");
        numRowsChoice.add("5");

        taskArr = new Color[cols][rows];
        for(int i=0; i<cols; i++){
            for(int j=0; j<rows; j++){
                taskArr[i][j]= Color.gray;
            }
        }
        
        startBtn = new Button("Старт");
        startBtn.addActionListener(this);
        answerBtn = new Button("Отвечать");
        answerBtn.addActionListener(this);
        answerBtn.setEnabled(false);
        okBtn = new Button("Ok");
        okBtn.addActionListener(this);
        okBtn.setEnabled(false);

        Label numColoursLabel = new Label("Colours");
        Label numColsLabel = new Label("Cols");
        Label numRowsLabel = new Label("Rows");
        
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        
        Panel p_south = new Panel();
        p_south.add(startBtn);
        p_south.add(answerBtn);
        p_south.add(okBtn);
        add(p_south, BorderLayout.SOUTH);
        
        Panel p_north = new Panel();
        p_north.add(numColsLabel);
        p_north.add(numColsChoice);
        p_north.add(numRowsLabel);
        p_north.add(numRowsChoice);
        p_north.add(numColoursLabel);
        p_north.add(numColoursChoice);
        add(p_north, BorderLayout.NORTH);
        
        addWindowListener(this);

        setVisible(true);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        setVisible(false);
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void paint(Graphics g){
        drawArr(taskArr);
        drawBtns();
        drawBtnsRects(currentColor);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==startBtn){
            cols = numColsChoice.getSelectedIndex()+1;
            rows = numRowsChoice.getSelectedIndex()+1;
            createTaskArr(cols,rows,numColoursChoice.getSelectedIndex()+2);
            repaint();
            startBtn.setEnabled(false);
            answerBtn.setEnabled(true);
        }
        if (e.getSource()==answerBtn){
            answerArr = new Color[cols][rows];
            for (int i=0; i<cols; i++){
                for(int j=0; j<rows; j++){
                    answerArr[i][j]=Color.gray;
                }
            }
            drawArr(answerArr);
            answerBtn.setEnabled(false);
            okBtn.setEnabled(true);
            addMouseListener(this);
        }
        if (e.getSource()==okBtn){
            removeMouseListener(this);
            okBtn.setEnabled(false);
            startBtn.setEnabled(true);
            drawArr(taskArr);
            
            boolean check = true;
            for (int i = 0; i<cols; i++){
                for (int j = 0; j<rows; j++){
                    if (taskArr[i][j]!=answerArr[i][j]){
                        check = false;
                    }
                }
            }
            Date date = new Date();
            String result = new String();
            result = date.toString()+" "+Integer.toString(cols)+"X"
                    +Integer.toString(rows)
                    +" colours="+ numColoursChoice.getSelectedItem()
                    +" result="+Boolean.toString(check);
            try{
                sout = new FileWriter("d:/VMresult.txt", true);
                for (int i = 0; i< result.length(); i++){
                    sout.write(result.charAt(i));
                }
                sout.write('\n');
                sout.flush();
                sout.close();
               
            }catch(Exception ex){}

        }
    }

    private void createTaskArr(int cols, int rows, int colours){
        taskArr = new  Color [cols][rows];
        int int_colour=0;
        for (int i = 0; i < cols; i++){
            for (int j=0; j < rows; j++){
                switch (colours){
                    case 2: int_colour = (int)(Math.random()/.5+1);
                    break;
                    case 3: int_colour = (int)(Math.random()/.33+1);
                    break;
                    case 4: int_colour = (int)(Math.random()/.25+1);
                }
                
                switch(int_colour){
                    case 1: taskArr[i][j] = Color.red;
                    break;
                    case 2:taskArr[i][j] = Color.yellow;
                    break;
                    case 3:taskArr[i][j] = Color.green;
                    break;
                    case 4:taskArr[i][j] = Color.blue;
                    break;
                }
            }
        }
    }

    private void drawArr(Color [][] array){
        Graphics g = getGraphics();
        for (int i = 0; i<cols; i++){
            for(int j = 0; j<rows; j++){
                if (array[i][j]==Color.gray){
                    g.setColor(Color.white);
                    g.fillRect((i+1)*25, (j+3)*25, 24, 24);
                    g.setColor(Color.gray);
                    g.drawRect((i+1)*25, (j+3)*25, 23, 23);
                }else{
                    g.setColor(array[i][j]);
                    g.fillRect((i+1)*25, (j+3)*25, 24, 24);
                }
            }
        }
    }

    private void drawBtns(){
        Graphics g = getGraphics();

        redButton.draw(g);
        yellowButton.draw(g);
        greenButton.draw(g);
        blueButton.draw(g);
    }
    
    private void drawBtnsRects(Color currentClr){
        Graphics g = getGraphics();
        redButton.isPressBtn(currentClr, g);
        yellowButton.isPressBtn(currentClr, g);
        greenButton.isPressBtn(currentClr, g);
        blueButton.isPressBtn(currentClr, g);
    }

    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        if (point.getX()>=(getSize().width-40)){
            if (redButton.isWithin(point)){
                currentColor = Color.red;
            }
            if (yellowButton.isWithin(point)){
                currentColor = Color.yellow;
            }
            if (greenButton.isWithin(point)){
                currentColor = Color.green;
            }
            if (blueButton.isWithin(point)){
                currentColor = Color.blue;
            }
            drawBtnsRects(currentColor);
        }else{
            int i = (int)point.getX()/25 - 1;
            int j = (int)point.getY()/25 - 3;

            if ((i>=0)&(i<cols)&(j>=0)&(j<rows)){
            
                Graphics g = getGraphics();
                g.setColor(currentColor);
                g.fillRect((i+1)*25, (j+3)*25, 24, 24);
                answerArr[i][j] = currentColor;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

}