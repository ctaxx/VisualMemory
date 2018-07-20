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
import java.util.Date;
import java.util.Random;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

/**
 *
 * @author Waddle
 */
public class CubicFrame extends Frame implements WindowListener, ActionListener,
        MouseListener {

    Button answerBtn, okBtn, startBtn, cancelBtn, nextBtn;
    Choice numColoursChoice;
//            , numColsChoice, numRowsChoice;

    Color currentColor, taskArr[][], answerArr[][];

    GraphicButton redButton, yellowButton, greenButton, blueButton;

    int cols = 2, rows = 2;
    int maxCols = 0;
    int maxRows = 0;

    int score;

    Writer sout;

    private final int INITIAL_STATE = 0;
    private final int SHOW_STATE = 1;
    private final int GETTIN_RESULT_STATE = 2;
    private final int SHOWIN_RESULT_STATE = 3;
    int state;

    public static void main(String[] args) {
        CubicFrame myFrame = new CubicFrame();
    }

    CubicFrame() {
        setTitle("VisualMemoryCubes");
        setSize(350, 300);
        setResizable(false);

        redButton = new GraphicButton(getSize().width - 40, 65, Color.red);
        yellowButton = new GraphicButton(getSize().width - 40, 100, Color.orange);
        greenButton = new GraphicButton(getSize().width - 40, 135, Color.green);
        blueButton = new GraphicButton(getSize().width - 40, 170, Color.blue);

        numColoursChoice = new Choice();
        numColoursChoice.add("2");
        numColoursChoice.add("3");
        numColoursChoice.add("4");

//        numColsChoice = new Choice();
//        numColsChoice.add("1");
//        numColsChoice.add("2");
//        numColsChoice.add("3");
//        numColsChoice.add("4");
//        numColsChoice.add("5");
//
//        numRowsChoice = new Choice();
//        numRowsChoice.add("1");
//        numRowsChoice.add("2");
//        numRowsChoice.add("3");
//        numRowsChoice.add("4");
//        numRowsChoice.add("5");
        taskArr = new Color[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                taskArr[i][j] = Color.gray;
            }
        }

        startBtn = new Button("Старт");
        startBtn.addActionListener(this);
        answerBtn = new Button("Отвечать");
        answerBtn.addActionListener(this);
        okBtn = new Button("Ok");
        okBtn.addActionListener(this);
        cancelBtn = new Button("Отмена");
        cancelBtn.addActionListener(this);
        nextBtn = new Button("Следующий");
        nextBtn.addActionListener(this);

        Label numColoursLabel = new Label("Colours");
//        Label numColsLabel = new Label("Cols");
//        Label numRowsLabel = new Label("Rows");

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        Panel p_south = new Panel();
        p_south.add(startBtn);
        p_south.add(answerBtn);
        p_south.add(okBtn);
        p_south.add(nextBtn);
        p_south.add(cancelBtn);     
        add(p_south, BorderLayout.SOUTH);

        Panel p_north = new Panel();
//        p_north.add(numColsLabel);
//        p_north.add(numColsChoice);
//        p_north.add(numRowsLabel);
//        p_north.add(numRowsChoice);
        p_north.add(numColoursLabel);
        p_north.add(numColoursChoice);
        add(p_north, BorderLayout.NORTH);

        addWindowListener(this);

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
//        drawArr(taskArr);
        drawBtns();
        drawBtnsRects(currentColor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startBtn) {
            setAppState(SHOW_STATE);
        }
        if (e.getSource() == answerBtn) {
            setAppState(GETTIN_RESULT_STATE);
        }
        if (e.getSource() == okBtn) {
            setAppState(SHOWIN_RESULT_STATE);
        }
        if (e.getSource() == nextBtn){
            setAppState(SHOW_STATE);
        }
        if (e.getSource() == cancelBtn) {
            setAppState(INITIAL_STATE);
        }
    }

    private boolean checkResult() {
        boolean check = true;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (taskArr[i][j] != answerArr[i][j]) {
                    check = false;
                }
            }
        }
        return check;
    }

    private void outputResult(boolean isChecked) {
        Date date = new Date();
//        JSONStreamAware jSONStreamAware;
//        JSONObject resultJSONObject = new JSONObject();
//        resultJSONObject.put("game", "ColorSequence");
//        resultJSONObject.put("date", date.toString());
//        resultJSONObject.put("score", Integer.toString(score));
//        resultJSONObject.put("maxSequenceLength", Integer.toString(maxSequenceLength));
//        resultJSONObject.put("colours", numColoursChoice.getSelectedItem());
//        jSONStreamAware = resultJSONObject;

        String result = date.toString() + " " + Integer.toString(cols) + "X"
                + Integer.toString(rows)
                + " colours=" + numColoursChoice.getSelectedItem()
                + " result=" + Boolean.toString(isChecked);

        try {
            sout = new FileWriter("d:/VMresult.txt", true);
            for (int i = 0; i < result.length(); i++) {
                sout.write(result.charAt(i));
            }
//            sout.write('\n');
//            jSONStreamAware.writeJSONString(sout);
            sout.write('\n');
            sout.flush();
            sout.close();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void createTaskArr(int cols, int rows, int colours) {
        Random random = new Random();
        taskArr = new Color[cols][rows];
        int currentColour;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                currentColour = random.nextInt(colours) + 1;

                switch (currentColour) {
                    case 1:
                        taskArr[i][j] = Color.red;
                        break;
                    case 2:
                        taskArr[i][j] = Color.orange;
                        break;
                    case 3:
                        taskArr[i][j] = Color.green;
                        break;
                    case 4:
                        taskArr[i][j] = Color.blue;
                        break;
                }
            }
        }
    }

    private void drawArr(Color[][] array) {
        Graphics g = getGraphics();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (array[i][j] == Color.gray) {
                    g.setColor(Color.white);
                    g.fillRect((i + 1) * 25, (j + 3) * 25, 24, 24);
                    g.setColor(Color.gray);
                    g.drawRect((i + 1) * 25, (j + 3) * 25, 23, 23);
                } else {
                    g.setColor(array[i][j]);
                    g.fillRect((i + 1) * 25, (j + 3) * 25, 24, 24);
                }
            }
        }
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

            if ((i >= 0) & (i < cols) & (j >= 0) & (j < rows)) {

                Graphics g = getGraphics();
                g.setColor(currentColor);
                g.fillRect((i + 1) * 25, (j + 3) * 25, 24, 24);
                answerArr[i][j] = currentColor;
            }
        }
    }

    private void setAppState(int state) {
        System.out.println("goin' to set state " + state);
        if (state == INITIAL_STATE) {
            this.state = state;
            this.cols = 2;
            this.rows = 1;
            this.score = 0;
            startBtn.setVisible(true);
            cancelBtn.setVisible(false);
            okBtn.setVisible(false);
            answerBtn.setVisible(false);
            nextBtn.setVisible(false);
           
        }
        if (state == SHOW_STATE) {
            this.state = state;
            startBtn.setVisible(false);
            cancelBtn.setVisible(true);
            okBtn.setVisible(false);
            answerBtn.setVisible(true);
            nextBtn.setVisible(false);
            
            if(cols > rows){
                rows++;
            }else{
                cols++;
            }
           
            createTaskArr(cols, rows, numColoursChoice.getSelectedIndex() + 2);
            drawArr(taskArr);
        }
        if (state == GETTIN_RESULT_STATE) {
            this.state = state;
            answerArr = new Color[cols][rows];
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    answerArr[i][j] = Color.gray;
                }
            }
            drawArr(answerArr);

            startBtn.setVisible(false);
            cancelBtn.setVisible(true);
            okBtn.setVisible(true);
            answerBtn.setVisible(false);
            nextBtn.setVisible(false);
            
            addMouseListener(this);
        }
        if (state == SHOWIN_RESULT_STATE) {
            this.state = state;
            removeMouseListener(this);

            startBtn.setVisible(false);
            cancelBtn.setVisible(true);
            okBtn.setVisible(false);
            answerBtn.setVisible(false);
            nextBtn.setVisible(true);

            drawArr(taskArr);

            outputResult(checkResult());
        }
        validate();
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

}
