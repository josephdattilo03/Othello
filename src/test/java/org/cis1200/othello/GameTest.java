package org.cis1200.othello;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class GameTest {
    private int[][] getBoardTemplate() {
        int[][] board = new int[8][8];
        int[] filler = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        int[] center1 = new int[] { 0, 0, 0, 2, 1, 0, 0, 0 };
        int[] center2 = new int[] { 0, 0, 0, 1, 2, 0, 0, 0 };
        for (int i = 0; i < 8; i++) {
            if (i == 3) {
                board[i] = Arrays.copyOf(center1, 8);
                i++;
                board[i] = Arrays.copyOf(center2, 8);
                i++;
            }
            board[i] = Arrays.copyOf(filler, 8);
        }
        return board;
    }

    public void testBoardEquality(int[][] expected, int[][] result) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(expected[i][j], result[i][j]);
            }
        }
    }

    @Test
    public void testInit() {
        int[][] expected = getBoardTemplate();
        Othello oth = new Othello();
        testBoardEquality(expected, oth.getBoard());
    }

    @Test
    public void testInvalidMoveIsZero() {
        int[][] expected = getBoardTemplate();
        Othello oth = new Othello();
        oth.playTurn(0, 0);
        testBoardEquality(expected, oth.getBoard());
    }

    @Test
    public void testInvalidMoveIsPlacedAlready() {
        int[][] expected = getBoardTemplate();
        Othello oth = new Othello();
        oth.playTurn(3, 3);
        testBoardEquality(expected, oth.getBoard());
    }

    @Test
    public void testValidHorizontalMove() {
        int[][] expected = getBoardTemplate();
        expected[3][2] = 1;
        expected[3][3] = 1;
        Othello othello = new Othello();
        othello.playTurn(2, 3);
        testBoardEquality(expected, othello.getBoard());
    }

    @Test
    public void testValidHorizontalandDiagonalMove() {
        int[][] expected = getBoardTemplate();
        expected[4][5] = 1;
        expected[4][4] = 1;
        Othello oth = new Othello();
        oth.playTurn(5, 4);
        testBoardEquality(expected, oth.getBoard());
        oth.playTurn(5, 5);
        expected[5][5] = 2;
        expected[4][4] = 2;
        expected[3][3] = 2;
        testBoardEquality(expected, oth.getBoard());
    }

    @Test
    public void testOneMoveMultipleChanges() {
        int[][] expected = getBoardTemplate();
        expected[3][3] = 1;
        expected[4][4] = 2;
        expected[4][3] = 2;
        expected[3][4] = 1;
        expected[2][3] = 1;
        expected[2][2] = 2;
        expected[3][2] = 2;
        expected[4][2] = 2;
        Othello oth = new Othello();
        oth.playTurn(3, 2);
        oth.playTurn(2, 2);
        oth.playTurn(2, 3);
        oth.playTurn(2, 4);
        testBoardEquality(expected, oth.getBoard());
    }

    @Test
    public void testVictory() {
        Othello oth = new Othello();
        oth.playTurn(3, 2);
        oth.playTurn(4, 2);
        oth.playTurn(5, 2);
        oth.playTurn(4, 1);
        oth.playTurn(5, 1);
        oth.playTurn(2, 5);
        oth.playTurn(3, 1);
        oth.playTurn(3, 0);
        oth.playTurn(2, 2);
        oth.playTurn(5, 3);
        oth.playTurn(4, 0);
        oth.playTurn(2, 0);
        oth.playTurn(2, 1);
        oth.playTurn(2, 3);
        oth.playTurn(2, 4);
        oth.playTurn(6, 2);
        oth.playTurn(1, 1);
        oth.playTurn(1, 0);
        oth.playTurn(1, 3);
        oth.playTurn(1, 4);
        oth.playTurn(1, 5);
        oth.playTurn(6, 1);
        oth.playTurn(6, 0);
        oth.playTurn(5, 0);
        oth.playTurn(0, 0);
        oth.playTurn(0, 1);
        oth.playTurn(0, 2);
        oth.playTurn(1, 2);
        oth.playTurn(0, 3);
        oth.playTurn(0, 4);
        oth.playTurn(0, 5);
        oth.playTurn(0, 6);
        oth.playTurn(1, 6);
        oth.playTurn(1, 7);
        oth.playTurn(0, 7);
        oth.playTurn(2, 6);
        oth.playTurn(2, 7);
        oth.playTurn(3, 7);
        oth.playTurn(3, 6);
        oth.playTurn(3, 5);
        oth.playTurn(4, 5);
        oth.playTurn(4, 6);
        oth.playTurn(5, 6);
        oth.playTurn(5, 5);
        oth.playTurn(5, 4);
        oth.playTurn(6, 4);
        oth.playTurn(6, 6);
        oth.playTurn(6, 5);
        oth.playTurn(6, 3);
        oth.playTurn(7, 3);
        oth.playTurn(7, 2);
        oth.playTurn(7, 1);
        oth.playTurn(7, 0);
        oth.playTurn(7, 5);
        oth.playTurn(4, 7);
        oth.playTurn(5, 7);
        oth.playTurn(6, 7);
        oth.playTurn(7, 7);
        oth.playTurn(7, 6);
        oth.playTurn(7, 4);
        assertTrue(oth.checkWinner());
        assertEquals(1, oth.getWinner());
    }
}
