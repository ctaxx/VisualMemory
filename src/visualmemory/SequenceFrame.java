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
import java.io.IOException;
import java.io.Writer;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

/**
 *
 * @author Waddle
 */
public class SequenceFrame extends Frame implements WindowListener, ActionListener,
        MouseListener {

    private final Button startBtn, cancelBtn;
    private final Choice numColoursChoice;
//    , sequenceLengthChoice;

    private Color currentColor, taskArr[];
    private ArrayList<Color> answerArr;

    private final GraphicButton redButton, orangeButton, greenButton, blueButton;
    private Label scoreLabel;

    private int sequenceLength;
    private int maxSequenceLength = 0;

    private int score;

    private Writer sout;

    private final int arrayHeight = 70;

    private final int INITIAL_STATE = 0;
    private final int SHOW_STATE = 1;
    private final int GETTIN_RESULT_STATE = 2;
    int state;

    public static void main(String[] args) {
        SequenceFrame myFrame = new SequenceFrame();
    }

    SequenceFrame() {
        setTitle("VisualMemorySequence");
        setSize(350, 300);
        setResizable(false);

        redButton = new GraphicButton(getSize().width - 40, 65, Color.red);
        orangeButton = new GraphicButton(getSize().width - 40, 100, Color.orange);
        greenButton = new GraphicButton(getSize().width - 40, 135, Color.green);
        blueButton = new GraphicButton(getSize().width - 40, 170, Color.blue);

        numColoursChoice = new Choice();
        numColoursChoice.add("2");
        numColoursChoice.add("3");
        numColoursChoice.add("4");

        startBtn = new Button("Старт");
        startBtn.addActionListener(this);
        cancelBtn = new Button("Отмена");
        cancelBtn.addActionListener(this);
        cancelBtn.setEnabled(false);

        Label numColoursLabel = new Label("Colours");
        scoreLabel = new Label("       ");

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        Panel southPanel = new Panel();
        southPanel.add(startBtn);
        southPanel.add(cancelBtn);
        add(southPanel, BorderLayout.SOUTH);

        Panel northPanel = new Panel();
        northPanel.add(numColoursLabel);
        northPanel.add(numColoursChoice);
        northPanel.add(scoreLabel);
        add(northPanel, BorderLayout.NORTH);

        addWindowListener(this);
        addMouseListener(this);

        answerArr = new ArrayList<Color>();
        setAppState(INITIAL_STATE);

        setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void paint(Graphics g) {
        drawBtns();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startBtn) {
            setAppState(SHOW_STATE);
        }
        if (e.getSource() == cancelBtn) {
            outputResult();
            clearAnswerArr();
            setAppState(INITIAL_STATE);
        }
    }

    private void createTaskArr(int sequenceLength, int colours) {
        Random random = new Random();
        taskArr = new Color[sequenceLength];
        int currentColour;
        for (int i = 0; i < sequenceLength; i++) {
            currentColour = random.nextInt(colours) + 1;

            switch (currentColour) {
                case 1:
                    taskArr[i] = Color.red;
                    break;
                case 2:
                    taskArr[i] = Color.orange;
                    break;
                case 3:
                    taskArr[i] = Color.green;
                    break;
                case 4:
                    taskArr[i] = Color.blue;
                    break;
            }
        }
    }

    private void drawArr(ArrayList<Color> array) {
        Graphics g = getGraphics();
        for (int i = 0; i < array.size(); i++) {
            g.setColor(array.get(i));
            g.fillRect((i + 1) * 25, getSize().height - arrayHeight, 24, 24);
        }
    }

    private void drawCentralCell(Color colour) {
        Graphics g = getGraphics();
        g.setColor(colour);
        g.fillRect(getSize().width / 2 - 15, getSize().height / 2 - 15, 30, 30);
    }

    private void drawBtns() {
        Graphics g = getGraphics();

        redButton.draw(g);
        orangeButton.draw(g);
        greenButton.draw(g);
        blueButton.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        if (point.getX() >= (getSize().width - 40) && state == GETTIN_RESULT_STATE) {
            if (redButton.isWithin(point)) {
                currentColor = Color.red;
            }
            if (orangeButton.isWithin(point)) {
                currentColor = Color.orange;
            }
            if (greenButton.isWithin(point)) {
                currentColor = Color.green;
            }
            if (blueButton.isWithin(point)) {
                currentColor = Color.blue;
            }
            answerArr.add(currentColor);
            drawArr(answerArr);
            checkResult();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // todo optimize
    private void checkResult() {
        if (answerArr.get(answerArr.size() - 1) != taskArr[answerArr.size() - 1]) {
            if (sequenceLength > 3) {
                sequenceLength--;
            }
            drawResultMark(false);
            System.out.println("sequence length is " + sequenceLength);
            setAppState(SHOW_STATE);
        } else {
            drawResultMark(true);
            score++;
            scoreLabel.setText(Integer.toString(score));
        }
        if (taskArr.length == answerArr.size()) {
            // todo show result to user
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            sequenceLength++;
            if (sequenceLength > maxSequenceLength) {
                maxSequenceLength = sequenceLength;
            }
            System.out.println("sequence length is " + sequenceLength);
            setAppState(SHOW_STATE);
        }
    }

    private void showSequence() {
        Runnable runnable = new Sequence(taskArr);
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void setAppState(int state) {
        if (state == INITIAL_STATE) {
            this.state = state;
            sequenceLength = 3;
            score = 0;
            startBtn.setEnabled(true);
            cancelBtn.setEnabled(false);
        }
        if (state == SHOW_STATE) {
            this.state = state;
            clearAnswerArr();
            startBtn.setEnabled(false);
            cancelBtn.setEnabled(true);
            createTaskArr(sequenceLength, numColoursChoice.getSelectedIndex() + 2);
            showSequence();
        }
        if (state == GETTIN_RESULT_STATE) {
            this.state = state;
        }
    }

    private void outputResult() {
        Date date = new Date();
        JSONStreamAware jSONStreamAware;
        JSONObject resultJSONObject = new JSONObject();
        resultJSONObject.put("game", "ColorSequence");
        resultJSONObject.put("date", date.toString());
        resultJSONObject.put("score", Integer.toString(score));
        resultJSONObject.put("maxSequenceLength", Integer.toString(maxSequenceLength));
        resultJSONObject.put("colours", numColoursChoice.getSelectedItem());
        jSONStreamAware = resultJSONObject;

//        String result = date.toString()
//                +" score="+ Integer.toString(score)
//                +" maxSequenceLength=" + Integer.toString(maxSequenceLength)
//                +" colours="+ numColoursChoice.getSelectedItem();
        try {
            sout = new FileWriter("d:/VMresult.txt", true);
//            for (int i = 0; i< result.length(); i++){
//                sout.write(result.charAt(i));
//            }
//            sout.write('\n');
            jSONStreamAware.writeJSONString(sout);
            sout.write('\n');
            sout.flush();
            sout.close();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void drawResultMark(boolean mark) {
        Graphics g = getGraphics();
        if (mark) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.RED);
        }
        g.fillOval(getSize().width - 30, getSize().height - 70, 20, 20);
    }

    private void clearAnswerArr() {
        for (int i = 0; i < answerArr.size(); i++) {
            answerArr.set(i, Color.white);
        }
        drawArr(answerArr);
        answerArr = new ArrayList<Color>();
    }

    public class Sequence implements Runnable {

        Color[] array;

        public Sequence(Color[] array) {
            this.array = array;
        }

        @Override
        public void run() {
            for (Color c : array) {
                if (state != SHOW_STATE) {
                    break;
                }
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
            if (state != INITIAL_STATE) {
                setAppState(GETTIN_RESULT_STATE);
            }
        }
    }
}
