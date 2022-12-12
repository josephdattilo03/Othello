package org.cis1200.othello;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Othello {
    private int[][] board;
    private boolean isWhite;
    private boolean gameOver;
    private BufferedReader br;
    private BufferedWriter bw;

    public Othello() {
        // Initializes the game board and game states
        this.board = new int[8][8];
        this.isWhite = true;
        this.gameOver = false;
        int[] filler = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        int[] center1 = new int[] { 0, 0, 0, 2, 1, 0, 0, 0 };
        int[] center2 = new int[] { 0, 0, 0, 1, 2, 0, 0, 0 };
        for (int i = 0; i < 8; i++) {
            if (i == 3) {
                this.board[i] = Arrays.copyOf(center1, 8);
                i++;
                this.board[i] = Arrays.copyOf(center2, 8);
                i++;
            }
            this.board[i] = Arrays.copyOf(filler, 8);
        }
    }

    public Othello(int[][] gameBoard, boolean isWhite) {
        this.board = gameBoard;
        this.isWhite = isWhite;
    }

    public int getCell(int x, int y) {
        if (x > 8 || x < 0 || y > 8 || y < 0) {
            return -1;
        } else {
            return this.board[y][x];
        }
    }

    public void printGame() {
        for (int i = 0; i < 8; i++) {
            System.out.println("\n" + Arrays.toString(this.board[i]));
        }
        System.out.println("\n\n");
    }

    private ArrayList<int[]> getTilesInDirection(int x, int y, int dx, int dy, int playerColor) {
        ArrayList<int[]> returnVal = new ArrayList<>();
        int tempX = x + dx;
        int tempY = y + dy;
        int currCell;
        boolean didTerminate = false;
        while (tempY < 8 && tempY >= 0 && tempX < 8 && tempX >= 0) {
            currCell = getCell(tempX, tempY);
            if (currCell == 0) {
                returnVal.clear();
                break;
            } else if (currCell == playerColor) {
                didTerminate = true;
                break;
            } else {
                returnVal.add(new int[] { tempX, tempY });
            }
            tempY = tempY + dy;
            tempX = tempX + dx;
        }
        if (!didTerminate) {
            returnVal.clear();
        }
        return returnVal;
    }

    private Collection<int[]> getTilesToFlip(int x, int y, int playerColor) {
        ArrayList<int[]> toReturn = new ArrayList<>();
        toReturn.addAll(getTilesInDirection(x, y, 0, 1, playerColor));
        toReturn.addAll(getTilesInDirection(x, y, 0, -1, playerColor));
        toReturn.addAll(getTilesInDirection(x, y, 1, 0, playerColor));
        toReturn.addAll(getTilesInDirection(x, y, -1, 0, playerColor));
        toReturn.addAll(getTilesInDirection(x, y, 1, 1, playerColor));
        toReturn.addAll(getTilesInDirection(x, y, -1, -1, playerColor));
        toReturn.addAll(getTilesInDirection(x, y, 1, -1, playerColor));
        toReturn.addAll(getTilesInDirection(x, y, -1, 1, playerColor));
        return toReturn;
    }

    // Saves game in a file that can be read by the program later
    public void saveGame() {
        try {
            bw = new BufferedWriter(new FileWriter(new File("files/save.txt")));
            String[] dump = gameDump();
            bw.flush();
            bw.write(dump[0]);
            bw.newLine();
            bw.write(dump[1]);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int[][] getOthBoard(String boardDump) {
        int[][] returnVal = new int[8][8];
        String[] firstStep = boardDump.split("n");
        for (int i = 0; i < 8; i++) {
            String[] secondStep = firstStep[i].split(",");
            for (int j = 0; j < 8; j++) {
                int currInt = Integer.parseInt(secondStep[j]);
                returnVal[i][j] = currInt;
            }
        }
        return returnVal;
    }

    // Method responsible for reading text from the save file and set the gamestate
    // as such
    public boolean loadGame() {
        try {
            br = new BufferedReader(new FileReader(new File("files/save.txt")));
            String stringBoard = br.readLine();
            int[][] board = getOthBoard(stringBoard);
            this.board = Arrays.copyOf(board, 8);
            this.isWhite = Boolean.parseBoolean(br.readLine());
            if (checkWinner()) {
                reset();
            }
            br.close();
            return true;

        } catch (IOException | NullPointerException e) {
            return false;
        }
    }

    private boolean isPlayable() {
        int playerColor;
        if (isWhite) {
            playerColor = 1;
        } else {
            playerColor = 2;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int currCell = getCell(j, i);
                if (currCell == 0 && !getTilesToFlip(j, i, playerColor).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWinner() {
        if (isPlayable()) {
            this.isWhite = !this.isWhite;
            if (isPlayable()) {
                try {
                    bw = new BufferedWriter(new FileWriter(new File("files/save.txt")));
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException();
                }
                this.gameOver = true;
            }
            this.isWhite = !this.isWhite;
        }
        return this.gameOver;
    }

    public int getWinner() {
        int whiteTiles = 0;
        int blackTiles = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (getCell(j, i) == 1) {
                    whiteTiles++;
                }
                if (getCell(j, i) == 2) {
                    blackTiles++;
                }
            }
        }
        if (whiteTiles > blackTiles) {
            return 1;
        } else if (blackTiles > whiteTiles) {
            return 2;
        } else {
            return 3;
        }
    }

    public boolean playTurn(int x, int y) {
        if (isPlayable()) {
            this.isWhite = !this.isWhite;
        }
        int playerColor;
        if (isWhite) {
            playerColor = 1;
        } else {
            playerColor = 2;
        }
        Collection<int[]> tilesToFlip = getTilesToFlip(x, y, playerColor);
        if (getCell(x, y) == 0 && !tilesToFlip.isEmpty()) {
            this.board[y][x] = playerColor;
            for (int[] coordinate : tilesToFlip) {
                this.board[coordinate[1]][coordinate[0]] = playerColor;
            }
            this.isWhite = !this.isWhite;
            return true;
        }
        return false;
    }

    public String[] gameDump() {
        String[] returnVal = new String[2];
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (j == 7) {
                    board.append(this.board[i][j]);
                } else {
                    board.append(this.board[i][j]).append(",");
                }
            }
            if (i != 7) {
                board.append("n");
            }
        }
        returnVal[0] = board.toString();
        returnVal[1] = String.valueOf(isWhite);
        return returnVal;
    }

    public void reset() {
        this.board = new int[8][8];
        this.isWhite = true;
        this.gameOver = false;
        int[] filler = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        int[] center1 = new int[] { 0, 0, 0, 2, 1, 0, 0, 0 };
        int[] center2 = new int[] { 0, 0, 0, 1, 2, 0, 0, 0 };
        for (int i = 0; i < 8; i++) {
            if (i == 3) {
                this.board[i] = Arrays.copyOf(center1, 8);
                i++;
                this.board[i] = Arrays.copyOf(center2, 8);
                i++;
            }
            this.board[i] = Arrays.copyOf(filler, 8);
        }
    }

    public boolean getPlayer() {
        return this.isWhite;
    }

    public int[][] getBoard() {
        return Arrays.copyOf(this.board, 8);
    }

}
