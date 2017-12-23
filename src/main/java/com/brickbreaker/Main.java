package com.brickbreaker;

import javax.swing.*;

/**
 * Created by ola on 17/12/2017.
 */
public class Main {

    public static void main(String[] args){
        JFrame obj = new JFrame();
        Game game = new Game();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Break a Brick");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
        obj.setVisible(true);
    }
}
