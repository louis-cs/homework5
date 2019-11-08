package edu.postech.csed332.homework5;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;

public class GameUI {
    private static final int unitSize = 10;
    private static final int width = 45;
    private static final int height = 45;

    final JFrame top;
    private Insets insets = new Insets(0,0,0,0);


    public GameUI(Board board) {
        top = new JFrame("Even/Odd Sudoku");
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top.setLayout(new GridLayout(3, 3));

        Dimension dim = new Dimension(unitSize * width, unitSize * height);
        top.setMinimumSize(dim);
        top.setPreferredSize(dim);

        createCellUI(board);

        top.pack();
        top.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board(GameInstanceFactory.createGameInstance());
            new GameUI(board);
        });
    }

    private void addComponent(Container container, Component component, int gridx, int gridy, int anchor, int fill){
        GridBagConstraints gbc = new GridBagConstraints(gridx,gridy,1,1,1.0,1.0,anchor,fill,insets,0,0);
        container.add(component,gbc);
    }

    /**
     * Create UI for cells
     * @param board
     */
    private void createCellUI(Board board) {
        CellUI[][] cells = new CellUI[9][9];
        JPanel[][] squares = new JPanel[3][3];

        //TODO: implement this. Create cells and squares, to add them to top, and to define layouts for them.

        for(int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                squares[i][j] = panel;
                top.add(panel);
            }
        }

        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++){
                CellUI cui = new CellUI(board.getCell(i,j));
                cells[i][j] = cui;
                addComponent(squares[i/3][j/3], cui, j%3, i%3, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
            }
        }
    }

}