
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
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Waddle
 */
public class SequenceFrame extends Frame implements WindowListener, ActionListener,
    MouseListener{

    Button startBtn, cancelBtn;
    Choice numColoursChoice, sequenceLengthChoice;

    Color currentColor, taskArr[];
    ArrayList <Color> answerArr;

    GraphicButton redButton, yellowButton, greenButton, blueButton;
    
    int sequenceLength = 4;

    Writer sout;
    
    private final int INITIAL_STATE = 0;
    private final int SHOW_STATE = 1;
    private final int GETTIN_RESULT_STATE = 2;
    int state = INITIAL_STATE;
    

    public static void main(String[] args) {
        SequenceFrame myFrame = new SequenceFrame();
    }

    SequenceFrame(){
        setTitle("VisualMemorySequence");
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
        
        sequenceLengthChoice = new Choice();
        sequenceLengthChoice.add("1");
        sequenceLengthChoice.add("2");
        sequenceLengthChoice.add("3");
        sequenceLengthChoice.add("4");
        sequenceLengthChoice.add("5");

        taskArr = new Color[sequenceLength];
        for(int i=0; i<sequenceLength; i++){
            taskArr[i]= Color.gray;
        }
        
        answerArr = new ArrayList<Color>();
        
        startBtn = new Button("Старт");
        startBtn.addActionListener(this);
        cancelBtn = new Button("Отмена");
        cancelBtn.addActionListener(this);
        cancelBtn.setEnabled(false);

        Label numColoursLabel = new Label("Colours");
        Label sequenceLengthLabel = new Label("Length");

        
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        
        Panel southPanel = new Panel();
        southPanel.add(startBtn);
        southPanel.add(cancelBtn);
        add(southPanel, BorderLayout.SOUTH);
        
        Panel northPanel = new Panel();
        northPanel.add(sequenceLengthLabel);
        northPanel.add(sequenceLengthChoice);
        northPanel.add(numColoursLabel);
        northPanel.add(numColoursChoice);
        add(northPanel, BorderLayout.NORTH);
        
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
        drawBtns();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==startBtn){
            sequenceLength = sequenceLengthChoice.getSelectedIndex()+1;
            createTaskArr(sequenceLength, numColoursChoice.getSelectedIndex()+2);
//            repaint();
            startBtn.setEnabled(false);
            cancelBtn.setEnabled(true);
            state = SHOW_STATE;
            showSequence();
            addMouseListener(this);
//            state = GETTIN_RESULT_STATE;
        }
        if (e.getSource()==cancelBtn){
            
            state = INITIAL_STATE;
            startBtn.setEnabled(true);
            cancelBtn.setEnabled(false);
        }
    }

    private void createTaskArr(int sequenceLength, int colours){
        taskArr = new  Color [sequenceLength];
        int int_colour=0;
        for (int i = 0; i < sequenceLength; i++){
            switch (colours){
                case 2: int_colour = (int)(Math.random()/.5+1);
                break;
                case 3: int_colour = (int)(Math.random()/.33+1);
                break;
                case 4: int_colour = (int)(Math.random()/.25+1);
            }
                
            switch(int_colour){
                case 1: taskArr[i] = Color.red;
                break;
                case 2:taskArr[i] = Color.yellow;
                break;
                case 3:taskArr[i] = Color.green;
                break;
                case 4:taskArr[i] = Color.blue;
                break;
            } 
        }
    }

    private void drawArr(ArrayList<Color> array){
        Graphics g = getGraphics();
        for (int i = 0; i < array.size(); i++){
            g.setColor(array.get(i));
            g.fillRect((i+1)*25, getSize().height-70, 24, 24);  
        }
    }
    
    private void drawCentralCell(Color colour){
        Graphics g = getGraphics();
        g.setColor(colour);
        g.fillRect(getSize().width/2-15, getSize().height/2-15, 30, 30);
    }

    private void drawBtns(){
        Graphics g = getGraphics();

        redButton.draw(g);
        yellowButton.draw(g);
        greenButton.draw(g);
        blueButton.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        if (point.getX()>=(getSize().width-40) && state == GETTIN_RESULT_STATE){
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
            answerArr.add(currentColor);
            drawArr(answerArr);
            evalAnswers();
        }
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    private void evalAnswers() {
        
    }

    private void showSequence() {
        Runnable runnable = new Sequence(taskArr);
        Thread thread = new Thread(runnable);
        thread.start();
    }
    
    public class Sequence implements Runnable{
        Color[] array;
    
        public Sequence(Color [] array){
            this.array = array;
        }

        public void run() {
            
            for (Color c : array) {
                if (state != SHOW_STATE)
                    break;
                currentColor = c;
                drawCentralCell(currentColor);
                try {
                    sleep(1000);
                    currentColor = Color.WHITE;
                    drawCentralCell(currentColor);
                    sleep(500);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }    
            }
            state = GETTIN_RESULT_STATE;
        }
    }

}