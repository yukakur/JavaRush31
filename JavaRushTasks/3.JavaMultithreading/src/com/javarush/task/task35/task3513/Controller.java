package com.javarush.task.task35.task3513;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static java.awt.event.KeyEvent.*;

public class Controller extends KeyAdapter {
    Model model;
    View view;
    private static final int WINNING_TILE = 2048;

    public Controller(Model model) {
        this.model = model;
        view = new View(this);
    }

    public View getView() {
        return view;
    }

    public Tile[][] getGameTiles() {
        return model.getGameTiles();
    }

    public int getScore() {
        return model.score;
    }

    public void resetGame() {
        model.score = 0;
        view.isGameWon = false;
        view.isGameLost = false;
        model.resetGameTiles();
        model.maxTile = 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == VK_ESCAPE) resetGame();
        else if(!model.canMove())view.isGameLost = true;
        else if(!view.isGameLost & !view.isGameWon) {
            if(e.getKeyCode() == VK_LEFT) model.left();
            else if(e.getKeyCode() == VK_RIGHT) model.right();
            else if(e.getKeyCode() == VK_UP) model.up();
            else if(e.getKeyCode() == VK_DOWN) model.down();
        }
        if(model.maxTile == WINNING_TILE) view.isGameWon = true;
        if(e.getKeyCode() == VK_Z) model.rollback();
        if(e.getKeyCode() == VK_R) model.randomMove();
        if(e.getKeyCode() == VK_A) model.autoMove();
        view.repaint();
    }
}
