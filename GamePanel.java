import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    // number of units
    static final int UNIT_SIZE = 25;
    // number of components in the screen simultaneously
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;
    // how slow is the game
    static final int DELAY = 85;

    //array with x coordinates
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    //initial size of the snake
    int bodyParts = 3;
    int foodEaten;

    //coordinates of the food - Random
    int foodX;
    int foodY;

    //Direction. By default, is set to move left
    char direction = 'R';

    boolean running = false;

    // Declare Timer
    Timer timer;
    //Declare Random
    Random random;


    GamePanel() {

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        startGame();
    }

    public void startGame() {

        deployFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics graphs) {
        super.paintComponent(graphs);
        draw(graphs);
    }

    public void draw(Graphics graphs) {
        //Create grid on the screen
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            graphs.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            graphs.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
        graphs.setColor(Color.red);
        graphs.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                graphs.setColor(Color.green);
            } else {
                graphs.setColor(new Color(45, 180, 0));
            }
            graphs.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }

    public void deployFood() {
        //X coordinate of food will be in the grid
        foodX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        //x[0] and y[0] are the unit coordinates of the head of the snake
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkFood() {
        //if head collides with food
        if (x[0] == foodX && y[0] == foodY){
            foodEaten++;
            bodyParts++;
            deployFood();
        }
    }

    public void checkCollision() {
        //if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                //trigger game over
                running = false;
                break;
            }
        }

        //if head collides boarder
        if ((x[0] < 0) || (x[0] >= SCREEN_WIDTH) || (y[0] < 0) || (y[0] >= SCREEN_HEIGHT)) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void quit(Graphics graphs) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent key) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
