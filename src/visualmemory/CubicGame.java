package visualmemory;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Random;
import org.json.simple.JSONObject;

/**
 *
 * @author Waddle
 */
public final class CubicGame extends VisualGame implements ActionListener {

    Button answerBtn, okBtn, startBtn, cancelBtn, nextBtn;
    Choice numColoursChoice;
    Choice userChoice;

    Color taskArr[][], answerArr[][];

    private Label scoreLabel;

    int cols = 2, rows = 2;
    int numColours = 0;
    int maxColours = 0;
    int maxCols = 0;
    int maxRows = 0;

    int score;

    int state;

    CubicGame(MainFrame frame) {
        this.frame = frame;

        userChoice = new Choice();
        userChoice.add(" ");
        userChoice.add("Андрей");
        userChoice.add("Стас");

        numColoursChoice = new Choice();
        numColoursChoice.add("2");
        numColoursChoice.add("3");
        numColoursChoice.add("4");

        startBtn = new Button("Старт");
        startBtn.addActionListener(this);
        answerBtn = new Button("Отвечать");
        answerBtn.addActionListener(this);
        okBtn = new Button("Ok");
        okBtn.addActionListener(this);
        cancelBtn = new Button("Стоп");
        cancelBtn.addActionListener(this);
        nextBtn = new Button("Следующий");
        nextBtn.addActionListener(this);

        Label numColoursLabel = new Label("Colours");
        scoreLabel = new Label("       ");

        Panel southPanel = new Panel();
        southPanel.add(startBtn);
        southPanel.add(answerBtn);
        southPanel.add(okBtn);
        southPanel.add(nextBtn);
        southPanel.add(cancelBtn);
        frame.add(southPanel, BorderLayout.SOUTH);

        frame.northPanel.add(userChoice);
        frame.northPanel.add(numColoursLabel);
        frame.northPanel.add(numColoursChoice);
        frame.northPanel.add(scoreLabel);

        setAppState(INITIAL_STATE);

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
        if (e.getSource() == nextBtn) {
            setAppState(SHOW_STATE);
        }
        if (e.getSource() == cancelBtn) {
            outputResult(prepareJSON());
            setAppState(INITIAL_STATE);
        }
    }

    boolean checkCell(int i, int j, Color currentColor) {
        if ((i >= 0) & (i < cols) & (j >= 0) & (j < rows)) {
            answerArr[i][j] = currentColor;
            return true;
        }
        return false;
    }

    private boolean checkResult() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (taskArr[i][j] != answerArr[i][j]) {
                    return false;
                }
            }
        }
        score += rows * cols * numColours;
        scoreLabel.setText(Integer.toString(score));
        return true;
    }

    @Override
    JSONObject prepareJSON() {
        Date date = new Date();
        JSONObject resultJSONObject = new JSONObject();
        resultJSONObject.put("game", "CubicFrame");
        resultJSONObject.put("date", date.toString());
        resultJSONObject.put("score", Integer.toString(score));
        resultJSONObject.put("maxCols", Integer.toString(maxCols));
        resultJSONObject.put("maxRows", Integer.toString(maxRows));
        resultJSONObject.put("maxColours", Integer.toString(maxColours));
        resultJSONObject.put("user", userChoice.getSelectedItem());
        return resultJSONObject;
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
        Graphics g = frame.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, (cols + 2) * 25, (rows + 4) * 25);
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
    
    @Override
    void drawContent(){
        if (state == SHOW_STATE) {
            drawArr(taskArr);
        }
        if (state == GETTIN_RESULT_STATE) {
            drawArr(answerArr);
        }
        if (state == SHOWIN_RESULT_STATE) {
            drawArr(taskArr);
        }
    }

    @Override
    void setAppState(int state) {
        if (state == INITIAL_STATE) {
            frame.repaint();
            this.state = state;
            this.cols = 2;
            this.rows = 2;
            this.score = 0;
            scoreLabel.setText(Integer.toString(score));
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

            numColours = numColoursChoice.getSelectedIndex() + 2;
            if (maxColours < numColours) {
                maxColours = numColours;
            }
            createTaskArr(cols, rows, numColours);
//            frame.repaint();
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
//            frame.repaint();
            drawArr(answerArr);

            startBtn.setVisible(false);
            cancelBtn.setVisible(true);
            okBtn.setVisible(true);
            answerBtn.setVisible(false);
            nextBtn.setVisible(false);
            
        }
        if (state == SHOWIN_RESULT_STATE) {
            this.state = state;

            startBtn.setVisible(false);
            cancelBtn.setVisible(true);
            okBtn.setVisible(false);
            answerBtn.setVisible(false);
            nextBtn.setVisible(true);

//            frame.repaint();
            drawArr(taskArr); 
            boolean result = checkResult();
            if (cols > rows) {
                if (!result && cols > 2) {
                    cols--;
                } else {
                    rows++;
                    if (maxRows < rows) {
                        maxRows = rows;
                    }
                }
            } else {
                if (!result && rows > 2) {
                    rows--;
                } else {
                    cols++;
                    if (maxCols < cols) {
                        maxCols = cols;
                    }
                }
            }
        }
//        frame.repaint();
        frame.validate();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = e.getPoint();
        int i = (int) point.getX() / 25 - 1;
        int j = (int) point.getY() / 25 - 3;

        if (this.checkCell(i, j, frame.getCurrentColor())) {
            Graphics g = frame.getGraphics();
            g.setColor(frame.getCurrentColor());
            g.fillRect((i + 1) * 25, (j + 3) * 25, 24, 24);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        int i = (int) point.getX() / 25 - 1;
        int j = (int) point.getY() / 25 - 3;

        if (this.checkCell(i, j, frame.getCurrentColor())) {

            Graphics g = frame.getGraphics();
            g.setColor(frame.getCurrentColor());
            g.fillRect((i + 1) * 25, (j + 3) * 25, 24, 24);
        }
    }

    @Override
    void clearGame() {
        
    }
}
