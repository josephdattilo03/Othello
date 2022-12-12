package org.cis1200.othello;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class GameBoard extends JPanel {
    private final Othello oth;
    private final JLabel status;
    private Clip placeClip;
    private Clip victoryClip;
    private AudioInputStream as;
    private AudioInputStream as2;

    public GameBoard(JLabel initialStatus) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        this.status = initialStatus;
        initAudio();
        this.oth = new Othello();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                if (oth.playTurn((p.x / 50) - 1, (p.y / 50) - 1)) {
                    placeClip.loop(1);
                    placeClip.stop();
                    placeClip.start();
                }
                updateStatus();
                repaint();
            }
        });
    }

    public void initAudio() {
        try {
            File f = new File("files/tileAudio.wav");
            File f2 = new File("files/victory.wav");
            this.as = AudioSystem.getAudioInputStream(f);
            this.as2 = AudioSystem.getAudioInputStream(f2);
            AudioFormat format = as.getFormat();
            AudioFormat format2 = as2.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            DataLine.Info info2 = new DataLine.Info(Clip.class, format2);
            this.placeClip = (Clip) AudioSystem.getLine(info);
            this.victoryClip = (Clip) AudioSystem.getLine(info2);
            this.placeClip.open(this.as);
            this.victoryClip.open(this.as2);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            this.placeClip = null;
            this.victoryClip = null;
        }
    }

    public Othello getOthello() {
        return this.oth;
    }

    public void reset(boolean isSavedGame) {
        if (!isSavedGame) {
            oth.reset();
        }
        repaint();
        status.setText("Player 1's Turn");
    }

    public void updateStatus() {
        boolean gameOver = oth.checkWinner();
        if (!gameOver) {
            if (oth.getPlayer()) {
                status.setText("Player 1's Turn");
            } else {
                status.setText("Player 2's Turn");
            }
        } else {
            this.placeClip.close();
            try {
                this.victoryClip.stop();
                this.victoryClip.start();
                this.as.close();
                this.as2.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            int winner = oth.getWinner();
            if (winner == 1) {
                status.setText("Player 1 Wins!!");
            } else if (winner == 2) {
                status.setText("Player 2 Wins!!");
            } else {
                status.setText("It's a tie :(");
            }
        }
    }

    public String[] gameStateDump() {
        return oth.gameDump();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 110, 0));
        g.fillRect(50, 50, 400, 400);
        g.setColor(Color.BLACK);
        for (int i = 0; i <= 400; i = i + 50) {
            g.drawLine(50 + i, 50, 50 + i, 450);
            g.drawLine(50, 50 + i, 450, +i + 50);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (oth.getCell(j, i) == 1) {
                    g.setColor(Color.white);
                    g.fillOval(50 + j * 50, 50 + i * 50, 50, 50);
                }
                if (oth.getCell(j, i) == 2) {
                    g.setColor(Color.black);
                    g.fillOval(50 + j * 50, 50 + i * 50, 50, 50);
                }
            }
        }
    }
}
