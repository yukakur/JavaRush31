package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    int score;
    int maxTile;
    private Stack previousStates = new Stack();
    private Stack previousScores = new Stack();
    boolean isSaveNeeded = true;

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public Model() {
        resetGameTiles();
    }

    public void resetGameTiles() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        score = 0;
        maxTile = 2;
        addTile();
        addTile();
    }

    private void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if (emptyTiles.size() != 0)
            emptyTiles.get((int) (Math.random() * emptyTiles.size())).value
                    = ((Math.random() < 0.9) ? 2 : 4);
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                Tile tile = gameTiles[i][j];
                if (tile.isEmpty()) emptyTiles.add(tile);
            }
        }
        return emptyTiles;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean compress = false;

        for (int i = 1; i < tiles.length; i++) {
            for (int j = 1; j < tiles.length; j++) {
                if (tiles[j - 1].isEmpty() && !tiles[j].isEmpty()) {
                    compress = true;
                    tiles[j - 1] = tiles[j];
                    tiles[j] = new Tile();
                }
            }
        }
        return compress;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean merge = false;

        for (int i = 1; i < tiles.length; i++) {
            if ((tiles[i - 1].value == tiles[i].value) && !tiles[i - 1].isEmpty() && !tiles[i].isEmpty()) {
                merge = true;
                tiles[i - 1].value *= 2;
                score += tiles[i - 1].value;
                maxTile = Math.max(maxTile, tiles[i - 1].value);
                tiles[i] = new Tile();
                compressTiles(tiles);
            }
        }
        return merge;
    }

    private void rotateCl() {
        Tile[][] rotatedTile = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                rotatedTile[j][FIELD_WIDTH - 1 - i] = gameTiles[i][j];
            }
        }
        gameTiles = rotatedTile;
    }

    public void left() {
        if(isSaveNeeded) saveState(gameTiles);

        int j = 0;
        for (Tile[] gameTile : gameTiles)
            if (compressTiles(gameTile) | mergeTiles(gameTile)) j++;
        if (j != 0) addTile();
        isSaveNeeded = true;
    }

    public void down() {
        saveState(gameTiles);
        rotateCl();
        left();
        rotateCl();
        rotateCl();
        rotateCl();
    }

    public void right() {
        saveState(gameTiles);
        rotateCl();
        rotateCl();
        left();
        rotateCl();
        rotateCl();
    }

    public void up() {
        saveState(gameTiles);
        rotateCl();
        rotateCl();
        rotateCl();
        left();
        rotateCl();
    }

    public boolean canMove() {
        if(!getEmptyTiles().isEmpty()) return true;

        for (Tile[] gameTile : gameTiles) {
            for (int j = 1; j < gameTiles.length; j++) {
                if (gameTile[j].value == gameTile[j - 1].value)
                    return true;
            }
        }
        for (int j = 0; j < gameTiles.length; j++) {
            for (int i = 1; i < gameTiles.length; i++) {
                if (gameTiles[i][j].value == gameTiles[i - 1][j].value)
                    return true;
            }
        }
        return false;
    }

    private void saveState(Tile[][] tiles) {
        Tile[][] newTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                newTiles [i][j] = new Tile();
            }
        }
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                newTiles[i][j].value = tiles[i][j].value;
            }
        }
        previousStates.push(newTiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        if(previousStates.isEmpty() | previousScores.isEmpty())  return;

        gameTiles = (Tile[][]) previousStates.pop();
        score = (int) previousScores.pop();

    }

    public void randomMove() {
        switch(((int)(Math.random() * 100)) % 4) {
            case (0) :
                left();
                break;
            case(1) :
                right();
                break;
            case(2):
                up();
                break;
            case(3):
                down();
                break;
            default:
                System.out.println("try again");
        }
    }

    public boolean hasBoardChanged() {
        int previousScore = 0;
        int nowScore = 0;
        if(!previousStates.isEmpty()) {
            Tile[][] previousTiles = (Tile[][]) previousStates.peek();
            for (int i = 0; i < FIELD_WIDTH; i++) {
                for (int j = 0; j < FIELD_WIDTH; j++) {
                    previousScore += previousTiles[i][j].value;
                    nowScore += gameTiles[i][j].value;
                }
            }
        }
        return previousScore != nowScore;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
       move.move();
       MoveEfficiency moveEfficiency = new MoveEfficiency((int)getEmptyTiles().size(), score, move);
       if(!hasBoardChanged()) moveEfficiency = new MoveEfficiency(-1, 0, move);
       rollback();
       return moveEfficiency;
    }

    public void autoMove() {
        PriorityQueue<MoveEfficiency> priorityQueue = new PriorityQueue<>(4, Collections.reverseOrder());
        priorityQueue.add(getMoveEfficiency(this:: up));
        priorityQueue.add(getMoveEfficiency(this:: down));
        priorityQueue.add(getMoveEfficiency(this:: left));
        priorityQueue.add(getMoveEfficiency(this:: right));

        priorityQueue.peek().getMove().move();

    }


}
