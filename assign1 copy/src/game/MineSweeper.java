package game;

import java.util.Random;

public class MineSweeper {
    public enum CellState {UNEXPOSED, EXPOSED, SEALED }

    public enum GameStatus { INPROGRESS, LOST, WON }

    private final int MAX_SIZE = 10;
    private CellState[][] cells = new CellState[MAX_SIZE][MAX_SIZE];
    private boolean[][] mines = new boolean[MAX_SIZE][MAX_SIZE];


    public MineSweeper() {
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                cells[i][j] = CellState.UNEXPOSED;
                mines[i][j] = false;
            }
        }
    }

    public void expose(int row, int column) {
        if (cells[row][column] == CellState.UNEXPOSED) {
            cells[row][column] = CellState.EXPOSED;
            if (adjacentMinesCount(row, column) == 0) {
                exposeNeighbors(row, column);
            }
        }
    }

    public void exposeNeighbors(int xCord, int yCord) {
        for (int rowVar = xCord - 1; rowVar <= xCord + 1; rowVar++) {
            for (int colVar = yCord - 1; colVar <= yCord + 1; colVar++) {
                if (rowVar != xCord || colVar != yCord) {
                    if (checkBounds(rowVar, colVar)) {
                        expose(rowVar, colVar);
                    }
                }
            }
        }
    }

    public int adjacentMinesCount(int xCord, int yCord) {
        int minesAroundCell = 0;
        for (int rowVar = xCord - 1; rowVar <= xCord + 1; rowVar++) {
            for (int colVar = yCord - 1; colVar <= yCord + 1; colVar++) {
                if (rowVar != xCord || colVar != yCord) {
                    if (checkBounds(rowVar, colVar)) {
                        if (isMineAt(rowVar, colVar)) {
                            minesAroundCell++;
                        }
                    }
                }
            }
        }
        return minesAroundCell;
    }


    public CellState getCellStatus(int row, int column) {
        return cells[row][column];
    }

    public void toggleSeal(int row, int column) {
        if (cells[row][column] == CellState.EXPOSED) return;

        if (cells[row][column] == CellState.SEALED) {
            cells[row][column] = CellState.UNEXPOSED;
        } else {
            cells[row][column] = CellState.SEALED;
        }
    }

    public void setMine(int row, int column) {
        mines[row][column] = true;
    }

    public boolean isMineAt(int row, int column) {
        return (!checkBounds(row, column)) ? false : mines[row][column];
    }

    public boolean checkBounds(int row, int column) {
        return (row >= 0 && row < MAX_SIZE && column >= 0 && column < MAX_SIZE);
    }

    public int setMines(int seed) {
        int row, column;
        int currentMines = 0;

        Random randomNum = new Random();
        randomNum.setSeed(seed);
        while (currentMines != 10) {
            row = randomNum.nextInt(10);
            column = randomNum.nextInt(10);

            if (!isMineAt(row, column)) {
                mines[row][column] = true;
                currentMines++;
            }
        }
        return currentMines;
    }

    public GameStatus getGameStatus() {
        GameStatus currentGameStatus = GameStatus.WON;
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                if ((mines[i][j] == false && cells[i][j] != CellState.EXPOSED)) {
                    currentGameStatus = GameStatus.INPROGRESS;
                }
                if (mines[i][j] == true && cells[i][j] == CellState.EXPOSED) {
                    currentGameStatus = GameStatus.LOST;
                    return currentGameStatus;
                }
            }
        }
        return currentGameStatus;
    }
}