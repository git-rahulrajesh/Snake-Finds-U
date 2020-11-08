
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements 
ActionListener {
    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 600;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = (B_WIDTH * B_HEIGHT)/ (DOT_SIZE * DOT_SIZE);
    private final int MAX_LIFE = 3;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int delay = 50;
    private int dots;
    private int apple_x;
    private int apple_y;
    private int lives = MAX_LIFE;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    // Sumeet
    public Board() {
        
    }
    
    //Hakim
    private void initBoard() {

    }

    // Sumeet
    private void loadImages() {

        
    }

    // Sumeet
    private void initGame() {

       
    }

    @Override
    public void paintComponent(Graphics g) {
       
    }
    
    //Hakim
    private void doDrawing(Graphics g) {
        
    
    }

    private void gameOver(Graphics g) {
        
      
    }

    private void checkApple() {
      if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            delay =- 10;
            locateApple();
        }
       
    }

    private void move() {

        
    }

    //Check if the snake has collided with anything
    private void checkCollision() {
      //Check if it has hit itself
       for (int z = dots; z > 0; z--) {

            if ((x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        //If you go out of bounds, wrap around to the otherwise
        if (y[0] >= B_HEIGHT) {
            y[0] = B_HEIGHT - y[0];
        }

        if (y[0] < 0) {
            y[0] = B_HEIGHT + y[0];
        }

        if (x[0] >= B_WIDTH) {
            x[0] = B_WIDTH - x[0];
        }

        if (x[0] < 0) {
            x[0] = B_WIDTH + x[0];
        }
        
        if (!inGame) {
            timer.stop();
        }
        
    }

    private void locateApple() {

      
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

  //Gets the user input in this class
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
           int key = e.getKeyCode();
            //Checks the direction, then updates the current direction travelling
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
           
        }
    }
}
