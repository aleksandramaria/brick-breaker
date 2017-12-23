package com.brickbreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by ola on 17/12/2017.
 */
public class Game extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int player = 310;
    private int ballPositionA = 350;
    private int ballPositionB = 530;
    private int ballADirection = 2;
    private int ballBDirection = -2;

    private BrickGenerator map;

    public Game(){
        map = new BrickGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1, 692, 592);

        //drawing bricks
        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("dialog", Font.PLAIN, 25));
        g.drawString(""+ score, 590, 30);

        //the paddle
        g.setColor(Color.RED);
        g.fillRect(player, 550, 100, 8);

        //the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballPositionA, ballPositionB, 20, 20);

        if(totalBricks <= 0) {
            play = false;
            ballADirection = 0;
            ballBDirection = 0;
            g.setColor(Color.BLUE);
            g.setFont(new Font("dialog", Font.BOLD, 30));
            g.drawString("You won: ", 260, 300);

            g.setFont(new Font("dialog", Font.BOLD, 20));
            g.drawString("Press enter to restart ", 230, 350);
        }

        if(ballPositionB > 570) {
            play = false;
            ballADirection = 0;
            ballBDirection = 0;
            g.setColor(Color.WHITE);
            g.setFont(new Font("dialog", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font("dialog", Font.BOLD, 20));
            g.drawString("Press enter to restart ", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        timer.start();
        if(play) {
            if(new Rectangle(ballPositionA, ballPositionB, 20,20).intersects(new Rectangle(player, 550, 100, 8))) {
                ballBDirection = - ballBDirection;
            }

          A:  for(int i = 0; i < map.bricks.length; i++) {
                for(int j = 0; j<map.bricks[0].length; j++) {
                    if(map.bricks[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPositionA, ballPositionB, 20, 20);
                        Rectangle brickRect = new Rectangle(rectangle);

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPositionA + 19 <= brickRect.x || ballPositionA + 1 >= brickRect.x + brickRect.width) {
                                ballADirection = -ballADirection;
                            } else {
                                ballBDirection = -ballBDirection;
                            }

                            break A;
                        }
                    }
                }
            }

            ballPositionA += ballADirection;
            ballPositionB += ballBDirection;
            if(ballPositionA < 0) {
                ballADirection = -ballADirection;
            }
            if(ballPositionB < 0) {
                ballBDirection = -ballBDirection;
            }
            if(ballPositionA > 670) {
                ballADirection = -ballADirection;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(player >= 600) {
                player = 600;
            } else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(player < 10) {
                player = 10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballPositionA = 120;
                ballPositionB = 350;
                ballADirection = -1;
                ballBDirection = -2;
                player = 310;
                score = 0;
                totalBricks = 21;
                map = new BrickGenerator(3, 7);

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        player += 20;
    }

    public void moveLeft() {
        play = true;
        player -= 20;
    }

}
