package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.events.Event;
import edu.postech.csed332.homework5.events.SetNumberEvent;
import edu.postech.csed332.homework5.events.UnsetNumberEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A group that observes a set of cells, and maintains the invariant: if one of the members has a particular value,
 * none of its other members can have the value as a possibility.
 */
public class Group implements Observer {
    //TODO: add private member variables for Board
    static enum GroupType {LINE,COLUMN,SQUARE};

    private int groupNb;
    private GroupType type;
    private List<Cell> cells;
    /**
     * Creates an empty group.
     */
    Group() {
        //TODO: implement this
        cells = new ArrayList<>();
    }

    public void setTypeNb(GroupType type, int nb){
        this.type=type;
        this.groupNb=nb;
    }

    public GroupType getType(){
        return this.type;
    }

    public int getGroupNb(){
        return this.groupNb;
    }
    /**
     * Adds a cell to this group. Use cell.addGroup to register this group.
     *
     * @param cell a cell to be added
     */
    void addCell(Cell cell) {
        //TODO: implement this
        if(!this.contains(cell)) {
            cells.add(cell);
            cell.addGroup(this);
        }
    }

    /**
     * Returns true if a given cell is belong to this group
     *
     * @param cell a cell
     * @return true if this group contains cell
     */
    @NotNull
    Boolean contains(@NotNull Cell cell) {
        //TODO: implement this
        if(!cells.contains(cell)){
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given number is available in the group
     *
     * @param number a number
     * @return true if no cell in the group has a given number.
     */
    @NotNull
    public Boolean isAvailable(int number) {
        //TODO: implement this
        for(Cell c : cells) {
            if(c.getNumber().isPresent()){
                int num = c.getNumber().get();
                if(num == number)
                return false;
            }
        }
        return true;
    }

    /**
     * Whenever a cell is changed, this function is called. Two kinds of events, SetNumberEvent and UnsetNumberEvent,
     * should be handled here.
     *
     * @param caller the subject
     * @param arg    an argument
     */
    @Override
    public void update(Subject caller, Event arg) {
        //TODO: implement this
        if (arg instanceof SetNumberEvent) {
            for(Cell c: cells) {
                c.removePossibility(((SetNumberEvent) arg).getNumber());
            }
        }
        if (arg instanceof UnsetNumberEvent) {
            for(Cell c: cells) {
                c.addPossibility(((UnsetNumberEvent) arg).getNumber());
            }
        }
    }
}
