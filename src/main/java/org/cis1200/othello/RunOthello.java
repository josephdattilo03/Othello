package org.cis1200.othello;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

public class RunOthello implements Runnable {
    public void run() {
        startMenu();
    }

    private void startMenu() {
        // Sets up the main menu
        final JFrame frame = new JFrame("Othello");
        frame.setLocation(100, 100);
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Please select an option");
        status_panel.add(status);
        final JPanel button_panel = new JPanel();
        button_panel.setLayout(new GridLayout(0, 1));
        JButton button1 = new JButton("New Game");
        JButton button2 = new JButton("Load Game");
        JButton button3 = new JButton("Instructions");
        JButton button4 = new JButton("Exit");
        button_panel.add(button1);
        button_panel.add(button2);
        button_panel.add(button3);
        button_panel.add(button4);
        frame.add(button_panel);
        frame.pack();
        frame.setSize(590, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // Exits the game
        button4.addActionListener(e -> {
            System.exit(0);
        });
        // Loads game from save.txt if able to
        button2.addActionListener(e -> {
            frame.remove(button_panel);
            frame.repaint();
            final GameBoard board = new GameBoard(status);
            board.getOthello().loadGame();
            initBoard(frame, board, true);

        });
        // Starts new game and wipes save.txt
        button1.addActionListener(e -> {
            frame.remove(button_panel);
            frame.repaint();
            final GameBoard board = new GameBoard(status);
            initBoard(frame, board, false);
        });
        button3.addActionListener(e -> {
            instructions();
        });
    }

    // initializes the game and adds button with the capability to save the game
    // state to save.txt
    private void initBoard(JFrame frame, GameBoard board, boolean isSavedGame) {
        frame.add(board, BorderLayout.CENTER);
        final JButton saveAndClose = new JButton("Save and Exit");
        saveAndClose.addActionListener(f -> {
            board.getOthello().saveGame();
            System.exit(0);
        });
        board.reset(isSavedGame);
        frame.add(saveAndClose, BorderLayout.EAST);
        frame.repaint();
    }

    private void instructions() {
        JFrame frame = new JFrame();
        JButton button = new JButton("Close");
        JEditorPane instructions = new JEditorPane();
        instructions.setEditable(false);
        HTMLEditorKit kit = new HTMLEditorKit();
        instructions.setEditorKit(kit);
        StyleSheet ss = kit.getStyleSheet();
        ss.addRule("h1 {text-align: center;}");
        ss.addRule("h2 {text-align: center;}");
        ss.addRule("p {text-align: center; font-size: 15px;}");
        String instructionString = "<html>\n" +
                "<h1>" + "Welcome to Othello" + "</h1>\n" + "<br>" + "<h2>"
                + "(Turn up your volume." +
                " This game has sounds)" + "</h2>" +
                "<h2>" + "Feel free to play with this window open ;)" + "</h2>"
                + "<p>" + "Objective: To have the most tiles on the board by the end of the game." +
                "<br>" + "<br>" +
                "Starting with the player using the white pieces, " +
                "players will place tiles one at a time "
                +
                "in turn order that flank " +
                "one or more of the opposing players tiles in one or more direction. " +
                "All tiles flanked in this " +
                "way must be flipped to the current players color. If a player is unable to play a"
                +
                " tile on their " +
                "turn, they are skipped. The game ends when neither player can make a move."
                + "</p>" +
                "</html>";
        Document doc = kit.createDefaultDocument();
        instructions.setDocument(doc);
        instructions.setText(instructionString);
        frame.add(instructions, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
        frame.setSize(590, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        button.addActionListener(e -> {
            frame.setVisible(false);
        });
    }
}
