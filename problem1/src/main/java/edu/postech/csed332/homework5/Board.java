package edu.postech.csed332.homework5;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * An even-odd Sudoku board
 */
public class Board {
    //TODO: add private member variables for Board
    private GameInstance game;
    private List<Group> groups;
    private List<Cell> cells;
    /**
     * Creates an even-odd Sudoku board
     *
     * @param game an initial configuration
     */
    Board(@NotNull GameInstance game) {
        //TODO: implement this
        this.game = game;
        for(int i=0;i<9;i++) {
            Group temp = new Group();
            temp.setTypeNb(Group.GroupType.LINE, i);
            groups.add(temp);
            Group temp2 = new Group();
            temp2.setTypeNb(Group.GroupType.COLUMN, i);
            groups.add(temp2);
            Group temp3 = new Group();
            temp3.setTypeNb(Group.GroupType.SQUARE, i);
            groups.add(temp3);
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Cell cell = new Cell(game.isEven(i, j) ? Cell.Type.EVEN : Cell.Type.ODD);
                this.getRowGroup(i).addCell(cell);
                this.getColGroup(j).addCell(cell);
                this.getSquareGroup(i,j).addCell(cell);
            }
        }

    }

    /**
     * Returns a cell in the (i+1)-th row of (j+1)-th column, where 0 <= i, j < 9.
     *
     * @param i a row index
     * @param j a column index
     * @return a cell in the (i+1)-th row of (j+1)-th column
     */
    @NotNull
    Cell getCell(int i, int j) {
        //TODO: implement this
        if(i > 8 || j > 8) {
            System.out.print("Invalid argument.");
        }
        for (Cell c : cells) {
            int [] a = c.getPosition();
            if(a[0] == i && a[1] == j)
                return c;
        }
        return  null;
    }

    /**
     * Returns a group for the (i+1)-th row, where 0 <= i < 9.
     *
     * @param i a row index
     * @return a group for the (i+1)-th row
     */
    @NotNull
    Group getRowGroup(int i) {
        //TODO: implement this
        if(i > 8) {
            System.out.print("Invalid argument.");
        }
        for (Group g : groups){
            if(g.getGroupNb()==i){
                if(g.getType()==Group.GroupType.LINE){
                    return g;
                }
            }
        }
        return null;
    }

    /**
     * Returns a group for the (j+1)-th column, where 0 <= j < 9.
     *
     * @param j a column index
     * @return a group for the (j+1)-th column
     */
    @NotNull
    Group getColGroup(int j) {
        //TODO: implement this
        if(j > 8) {
            System.out.print("Invalid argument. ");
        }
        for (Group g : groups){
            if(g.getGroupNb()==j){
                if(g.getType()==Group.GroupType.COLUMN){
                    return g;
                }
            }
        }
        return null;
    }

    /**
     * Returns a square group for the (n+1)-th row of (m+1)-th column, where 1 <= n, m <= 3
     *
     * @param n a square row index
     * @param m a square column index
     * @return a square group for the (n+1)-th row of (m+1)-th column
     */
    @NotNull
    Group getSquareGroup(int n, int m) {
        //TODO: implement this
        if(n > 3 || m >3) {
            System.out.print("Invalid argument. ");
        }
        int grpNum = n%3 + (m/3)*3;
        for (Group g : groups){
            if(g.getGroupNb()==grpNum){
                if(g.getType()==Group.GroupType.SQUARE){
                    return g;
                }
            }
        }
        return null;
    }
}
