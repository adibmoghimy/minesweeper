package org.bihe.soduko;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {

    Date startDate = new Date();
    Date endDate;

    public boolean resetter = false;

    int spacing = 5;

    String vicMes = "Nothing yet!";

    public int mx = -100;
    public int my = -100;

    public int smileX = 605;
    public int smileY = 5;

    public int smileyCenterX = smileX + 35;
    public int smileyCenterY = smileY + 70;

    public int flaggerX = 445;
    public int flaggerY = 5;

    public int flaggerCenterX = flaggerX + 35;
    public int flaggerCenterY = flaggerY + 70;

    public boolean flagger = false;

    public int timeX = 1100;
    public int timeY = 5;

    public int vicMesX = 740;
    public int vicMesY = -50;

    public long sec = 0;

    Random rand = new Random();

    int[][] mines = new int[9][16];
    int[][] neighbours = new int[9][16];
    boolean[][] revealed = new boolean[9][16];
    boolean[][] flagged = new boolean[9][16];
//    int[][] mines = new int[5][5];
//    int[][] neighbours = new int[5][5];
//    boolean[][] revealed = new boolean[5][5];
//    boolean[][] flagged = new boolean[5][5];
    boolean mineClicked = false;
    int row = -1;
    int col = -1;

    public boolean victory = false;

    public boolean defeat = false;

    public GUI() {
        this.setTitle("Minesweeper");
        this.setSize(1286, 829);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        defineMines();


        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);
    }

    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 1280, 800);
            for (int i = 0; i < mines.length; i++) {
                for (int j = 0; j < mines[i].length; j++) {
                    g.setColor(Color.gray);


                    if (revealed[i][j]) {
                        g.setColor(Color.white);
                    }
                    if (mineClicked) {
                        if (mines[i][j] == 1) {
                            g.setColor(Color.red);

                        }
                    }

                    if (mx >= spacing + j * 80 && mx < j * 80 + 80 - spacing && my >= spacing + i * 80 + 80 + 40 && my < i * 80 + 80 + 80 - spacing + 40) {
                        g.setColor(Color.lightGray);
                        row = i;
                        col = j;

                    }
                    g.fillRect(spacing + j * 80, spacing + i * 80 + 80, 80 - 2 * spacing, 80 - 2 * spacing);
                    if (revealed[i][j]) {
                        g.setColor(Color.black);
                        if (mines[i][j] == 0 && neighbours[i][j] != 0) {
                            switch (neighbours[i][j]) {
                                case 1:
                                    g.setColor(Color.blue);
                                    break;
                                case 2:
                                    g.setColor(Color.green);
                                    break;
                                case 3:
                                    g.setColor(Color.red);
                                    break;
                                case 4:
                                    g.setColor(new Color(0, 0, 128));
                                    break;
                                case 5:
                                    g.setColor(new Color(178, 34, 34));
                                    break;
                                case 6:
                                    g.setColor(new Color(72, 209, 204));
                                    break;
                                case 8:
                                    g.setColor(Color.darkGray);
                                    break;
                                default:
                                    break;
                            }
                            g.setFont(new Font("Tahoma", Font.BOLD, 40));
                            g.drawString(Integer.toString(neighbours[i][j]), j * 80 + 27, i * 80 + 80 + 55);
                        } else if (mines[i][j] == 1) {
                            g.fillRect(j * 80 + 10 + 20, i * 80 + 80 + 20, 20, 40);
                            g.fillRect(j * 80 + 20, i * 80 + 80 + 10 + 20, 40, 20);
                            g.fillRect(j * 80 + 5 + 20, i * 80 + 80 + 5 + 20, 30, 30);
                            g.fillRect(j * 80 + 38, i * 80 + 80 + 15, 4, 50);
                            g.fillRect(j * 80 + 15, i * 80 + 80 + 38, 50, 4);
                        }
                    }
                    if (flagged[i][j]) {
                        g.setColor(Color.BLACK);
                        g.fillRect(j * 80 + 32, i * 80 + 80 + 15, 5, 40);
                        g.fillRect(j * 80 + 20, i * 80 + 80 + 50, 30, 10);
                        g.setColor(Color.red);
                        g.fillRect(j * 80 + 12, i * 80 + 80 + 15, 20, 15);
                        g.setColor(Color.BLACK);
                        g.fillRect(j * 80 + 12, i * 80 + 80 + 15, 3, 15);
                        g.fillRect(j * 80 + 12, i * 80 + 80 + 15, 20, 3);
                        g.fillRect(j * 80 + 12, i * 80 + 80 + 30, 20, 3);
                    }

                }

            }

            g.setColor(Color.yellow);
            g.fillOval(smileX, smileY, 70, 70);
            g.setColor(Color.BLACK);
            g.fillOval(smileX + 15, smileY + 20, 10, 10);
            g.fillOval(smileX + 45, smileY + 20, 10, 10);

            if (!mineClicked) {
                g.fillRect(smileX + 20, smileY + 50, 30, 5);
                g.fillRect(smileX + 17, smileY + 45, 5, 5);
                g.fillRect(smileX + 48, smileY + 45, 5, 5);
            } else {
                g.fillRect(smileX + 20, smileY + 45, 30, 5);
                g.fillRect(smileX + 17, smileY + 50, 5, 5);
                g.fillRect(smileX + 48, smileY + 50, 5, 5);
            }

            g.setColor(Color.BLACK);
            g.fillRect(flaggerX + 32, flaggerY + 15, 5, 40);
            g.fillRect(flaggerX + 20, flaggerY + 50, 30, 10);
            g.setColor(Color.red);
            g.fillRect(flaggerX + 12, flaggerY + 15, 20, 15);
            g.setColor(Color.BLACK);
            g.fillRect(flaggerX + 12, flaggerY + 15, 3, 15);
            g.fillRect(flaggerX + 12, flaggerY + 15, 20, 3);
            g.fillRect(flaggerX + 12, flaggerY + 30, 20, 3);

            if (flagger) {
                g.setColor(Color.red);
            }

            g.drawOval(flaggerX, flaggerY, 70, 70);
            g.drawOval(flaggerX + 1, flaggerY + 1, 68, 68);
            g.drawOval(flaggerX + 2, flaggerY + 2, 66, 66);


            g.setColor(Color.black);
            g.fillRect(timeX, timeY, 155, 70);
            if (!defeat && !victory) {
                sec = (new Date().getTime() - startDate.getTime()) / 1000;
            }
            if (sec > 999) {
                sec = 999;
            }
            g.setColor(Color.white);
            if (victory) {
                g.setColor(Color.green);
            } else if (defeat) {
                g.setColor(Color.red);
            }
            g.setFont(new Font("Tahoma", Font.PLAIN, 80));
            if (sec < 10) {
                g.drawString("00" + Long.toString(sec), timeX, timeY + 65);
            } else if (sec < 100) {
                g.drawString("0" + Long.toString(sec), timeX, timeY + 65);
            } else {
                g.drawString(Long.toString(sec), timeX, timeY + 65);
            }

            if (victory) {
                g.setColor(Color.GREEN);
                vicMes = "You Win";
            } else if (defeat) {
                g.setColor(Color.red);
                vicMes = "You lose";
            }

            if (victory || defeat) {
                vicMesY = -50 + (int) (new Date().getTime() - endDate.getTime()) / 10;
                if (vicMesY > 70) {
                    vicMesY = 70;
                }
                g.setFont(new Font("Tahoma", Font.PLAIN, 70));
                g.drawString(vicMes, vicMesX, vicMesY);
            }


        }

    }

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            mx = mouseEvent.getX();
            my = mouseEvent.getY();
        }
    }

    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            mx = mouseEvent.getX();
            my = mouseEvent.getY();
            System.out.println("Row: " + mx + ", Col: " + my);
            if (my > 121) {
                if (flagger && !revealed[row][col]) {
                    flagged[row][col] = !flagged[row][col];
                } else {
                    if (!flagged[row][col]) {
                        if (mines[row][col] == 1) {
                            System.out.println("You click a mine");
                            mineClicked = true;
                        }
                        setNeighbors();
                    }
                }
            }

            if (inSmiley()) {
                resetAll();
            }
            if (inFlagger()) {
                flagger = !flagger;
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    public ArrayList<Integer> findRowNeighbors() {
        ArrayList<Integer> rowNeighs = new ArrayList<>();
        if (row != 0 && row != (mines.length - 1)) {
            rowNeighs.add(row - 1);
            rowNeighs.add(row);
            rowNeighs.add(row + 1);
        } else if (row == 0) {
            rowNeighs.add(row);
            rowNeighs.add(row + 1);
        } else {
            rowNeighs.add(row - 1);
            rowNeighs.add(row);
        }
        return rowNeighs;
    }

    public ArrayList<Integer> findColNeighbors() {
        ArrayList<Integer> colNeighs = new ArrayList<>();
        if (col != 0 && col != (mines[1].length - 1)) {
            colNeighs.add(col - 1);
            colNeighs.add(col);
            colNeighs.add(col + 1);
        } else if (col == 0) {
            colNeighs.add(col);
            colNeighs.add(col + 1);
        } else {
            colNeighs.add(col - 1);
            colNeighs.add(col);
        }
        return colNeighs;
    }

    public void setNeighbors() {
        ArrayList<Integer> testRow = findRowNeighbors();
        ArrayList<Integer> colRow = findColNeighbors();

        ArrayList<Neighbers> neighbersArrayList = new ArrayList<>();

        if (revealed[row][col]) {
            System.out.println("This already clicked");
        } else {
            for (Integer rowInteger : testRow) {
                for (Integer colInteger : colRow) {
                    if (rowInteger == row && colInteger == col) {
                        continue;
                    }
                    if (mines[rowInteger][colInteger] == 1) {
                        neighbours[row][col]++;
                    }
                    Neighbers testNeigh = new Neighbers(rowInteger, colInteger);
                    neighbersArrayList.add(testNeigh);
                }
            }
            revealed[row][col] = true;
        }
        if (neighbours[row][col] == 0 ) {
            revealedNeighbors(neighbersArrayList);
        }

        System.out.println("The mines in the neighbors is " + neighbours[row][col]);
    }

    public void revealedNeighbors(ArrayList<Neighbers> neighbersArrayList){
        for (Neighbers neighbers : neighbersArrayList) {
            row = neighbers.rowNeigh;
            col = neighbers.colNeigh;
            setNeighbors();
        }
    }

    public void defineMines() {
        for (int i = 0; i < mines.length; i++) {
            for (int j = 0; j < mines[i].length; j++) {
                if (rand.nextInt(100) < 20) {
                    mines[i][j] = 1;
                } else {
                    mines[i][j] = 0;
                }
                revealed[i][j] = false;
            }

        }
    }

    public void resetAll() {
        resetter = true;
        mineClicked = false;
        victory = false;
        defeat = false;
        vicMesY = -50;
        startDate = new Date();
        vicMes = "Nothing yet!";
        flagger = false;

        defineMines();
        resetNeighbors();
        resetFlagged();

        resetter = false;
    }

    public boolean inSmiley() {
        int dif = (int) Math.sqrt((mx - smileyCenterX) * (mx - smileyCenterX) + (my - smileyCenterY) * (my - smileyCenterY));
        return dif < 35;
    }

    public boolean inFlagger() {
        int dif = (int) Math.sqrt((mx - flaggerCenterX) * (mx - flaggerCenterX) + (my - flaggerCenterY) * (my - flaggerCenterY));
        return dif < 35;
    }

    public void resetNeighbors() {
        for (int[] neighbour : neighbours) {
            Arrays.fill(neighbour, 0);
        }
    }

    public void resetFlagged() {
        for (boolean[] flag : flagged) {
            Arrays.fill(flag, false);
        }
    }

    public void checkVictoryStatus() {

        if (!defeat) {
            for (int i = 0; i < mines.length; i++) {
                for (int j = 0; j < mines[i].length; j++) {
                    if (mines[i][j] == 1 && revealed[i][j]) {
                        defeat = true;
                        mineClicked = true;
                        endDate = new Date();
                        break;
                    }
                }
            }
        }

        if (totalBoxesRevealed() >= (mines.length * mines[1].length) - totalMines() && !victory) {
            victory = true;
            endDate = new Date();
        }

    }

    public int totalMines() {
        int total = 0;
        for (int[] mine : mines) {
            for (int i : mine) {
                if (i == 1) {
                    total++;
                }
            }
        }
        return total;
    }

    public int totalBoxesRevealed() {
        int total = 0;
        for (boolean[] mine : revealed) {
            for (boolean i : mine) {
                if (i) {
                    total++;
                }
            }
        }
        return total;
    }
}
