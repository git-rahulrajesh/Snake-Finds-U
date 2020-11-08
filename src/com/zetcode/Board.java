package com.zetcode;

//Import Statements to draw the game
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
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 @author Hakim Bashe
 @author Rahul Rajesh
 @author Sumeet Sara
 This is the board class, it contains the grid that the snake game is played on
 The snake moves by the user pressing the arrow keys, and the snake grows if you eat any apples. There are also mines scattered across the board, if you come into contact with any of them, the game ends. The game also ends if the snake touches itself when moving
 */
public class Board extends JPanel implements ActionListener {

    // List of private consants
    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 600;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = (B_WIDTH * B_HEIGHT)/ (DOT_SIZE^2);
    private final int RAND_POS = B_HEIGHT / DOT_SIZE - 1;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    // List of private variables initalized
    private int delay = 50;
    private int dots;
    private int apple_x;
    private int apple_y;

    // Landmines initialized
    private int landmine1_x;
    private int landmine1_y;
    private int landmine2_x;
    private int landmine2_y;
    private int landmine3_x;
    private int landmine3_y;
    private int landmine4_x;
    private int landmine4_y;
    private int landmine5_x;
    private int landmine5_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    // Images
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    private Image landMine;

    public Board() {

        initBoard();
    }

    // Initiate the game by creating game window, and loading images of assets
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    // Get the images of the snake body, snake food, snake body, and landmines
    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();

        // Get the landmine image
        ImageIcon iim = new ImageIcon(("src/resources/landmines.png"));
        landMine = iim.getImage();
    }

    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();
        insertMines();

        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            // draw the landmines
            g.drawImage(landMine, landmine1_x, landmine1_y, this);
            g.drawImage(landMine, landmine2_x, landmine2_y, this);
            g.drawImage(landMine, landmine3_x, landmine3_y, this);
            g.drawImage(landMine, landmine4_x, landmine4_y, this);
            g.drawImage(landMine, landmine5_x, landmine5_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }



    //Prints the  gameover message and then ends the game afterwards
    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    //Check if the current location is an apple, if so add to the snake and increase the game speed
    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            delay =- 10;
            locateApple();
        }
    }

    // Check is user lands on a mine
    private void checkMine() {
        if ((x[0] == landmine1_x) && (y[0] == landmine1_y)) {
            inGame = false;
        } else if ((x[0] == landmine2_x) && (y[0] == landmine2_y)) {
            inGame = false;
        } else if ((x[0] == landmine3_x) && (y[0] == landmine3_y)) {
            inGame = false;
        } else if ((x[0] == landmine4_x) && (y[0] == landmine4_y)) {
            inGame = false;
        } else if ((x[0] == landmine5_x) && (y[0] == landmine5_y)) {
            inGame = false;
        }
    }

    //Update the location of each of the dots, depending on the direction add or increase dot size
    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    //If the snake eats itself, end the game
    //Allow the snake to wrap over
    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

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

    //Find a location for the next apple
    private void locateApple() {
        Random rand = new Random();
        int r = rand.nextInt(60);
        apple_x = ((r * DOT_SIZE));
        r = rand.nextInt(60);
        apple_y = ((r * DOT_SIZE));
    }

    //Loop through these three options to play the game
    //Check if spot is an apple, is a mine, is a collision, the move, the repaint the board
    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkMine();
            checkCollision();
            move();
        }

        repaint();
    }

    // Insert landmines on the window
    // Check if landmine have a valid location
    public void insertMines() {
        Random random = new Random();
        int xMine = random.nextInt(60);
        landmine1_x = ((xMine * DOT_SIZE));
        int yMine = random.nextInt(60);
        landmine1_y = ((yMine * DOT_SIZE));

        while ((landmine1_x == apple_x) && (landmine1_y == apple_y)) {
            xMine = random.nextInt(60);
            landmine1_x = ((xMine * DOT_SIZE));
            yMine = random.nextInt(60);
            landmine1_y = ((yMine * DOT_SIZE));
        }

        xMine = random.nextInt(60);
        landmine2_x = ((xMine * DOT_SIZE));
        yMine = random.nextInt(60);
        landmine2_y = ((yMine * DOT_SIZE));

        while ((landmine2_x == apple_x) && (landmine2_y == apple_y)) {
            xMine = random.nextInt(60);
            landmine2_x = ((xMine * DOT_SIZE));
            yMine = random.nextInt(60);
            landmine2_y = ((yMine * DOT_SIZE));
        }

        xMine = random.nextInt(60);
        landmine3_x = ((xMine * DOT_SIZE));
        yMine = random.nextInt(60);
        landmine3_y = ((yMine * DOT_SIZE));

        while ((landmine3_x == apple_x) && (landmine3_y == apple_y)) {
            xMine = random.nextInt(60);
            landmine3_x = ((xMine * DOT_SIZE));
            yMine = random.nextInt(60);
            landmine3_y = ((yMine * DOT_SIZE));
        }

        xMine = random.nextInt(60);
        landmine4_x = ((xMine * DOT_SIZE));
        yMine = random.nextInt(60);
        landmine4_y = ((yMine * DOT_SIZE));

        while ((landmine4_x == apple_x) && (landmine4_y == apple_y)) {
            xMine = random.nextInt(60);
            landmine4_x = ((xMine * DOT_SIZE));
            yMine = random.nextInt(60);
            landmine4_y = ((yMine * DOT_SIZE));
        }

        xMine = random.nextInt(60);
        landmine5_x = ((xMine * DOT_SIZE));
        yMine = random.nextInt(60);
        landmine5_y = ((yMine * DOT_SIZE));

        while ((landmine5_x == apple_x) && (landmine5_y == apple_y)) {
            xMine = random.nextInt(60);
            landmine5_x = ((xMine * DOT_SIZE));
            yMine = random.nextInt(60);
            landmine5_y = ((yMine * DOT_SIZE));
        }
    }

    //This lets the user get keyboard input to move the snake
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            //check the direction
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
