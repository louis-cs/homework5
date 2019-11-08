package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.events.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

/**
 * A cell that has a number and a set of possibilities. Even cells can contain only even numbers, and odd cells can
 * contain only odd numbers. A cell may have a number of observers, and notifies events to the observers.
 */
public class Cell extends Subject {
    enum Type {EVEN, ODD}

    //TODO: add private member variables for Board
    private int number = 0;
    private List<Integer> possibility;
    private Type type;
    private List<Group> groups;
    private int[] position = new int[2];

    /**
     * Creates an empty cell with a given type. Initially, no number is assigned.
     *
     * @param type EVEN or ODD
     */
    public Cell(@NotNull Type type) {
        //TODO: implement this
        this.type = type;
        possibility = new ArrayList<>();
        groups = new ArrayList<>();

        for (int i=1; i<10; i++){
            if(this.type == Type.EVEN && i%2 == 0)
                possibility.add(i);
            else if (this.type == Type.ODD && i%2 == 1)
                possibility.add(i);
        }
    }

    public void setPosition(int line, int column){
        position[0] = line;
        position[1] = column;
    }

    public int[] getPosition(){
        return position;
    }

    /**
     * Returns the type of this cell.
     *
     * @return the type
     */
    @NotNull
    public Type getType() {
        //TODO: implement this
        return type;
    }

    /**
     * Returns the number of this cell.
     *
     * @return the number; Optional.empty() if no number assigned
     */
    @NotNull
    public Optional<Integer> getNumber() {
        //TODO: implement this
        if (number > 0)
            return Optional.of(number);
        else
            return Optional.empty();
    }

    /**
     * Sets a number of this cell and notifies a SetNumberEvent, provided that the cell has previously no number
     * and the given number to be set is in the set of possibilities.
     *
     * @param number the number
     */
    public void setNumber(int number) {
        //TODO: implement this

        if(!possibility.contains(number) || this.number >0){
            return;
        }
        this.number = number;
        notifyObservers(new SetNumberEvent(number));
    }

    /**
     * Removes the number of this cell and notifies an UnsetNumberEvent, provided that the cell has a number.
     */
    public void unsetNumber() {
        //TODO: implement this
        int temp = this.number;
        this.number = 0;
        notifyObservers(new UnsetNumberEvent(temp));
    }

    /**
     * Adds a group for this cell. This methods should also call addObserver(group).
     *
     * @param group
     */
    public void addGroup(@NotNull Group group) {
        addObserver(group);

        //TODO: implement this
        groups.add(group);
    }

    /**
     * Returns true if a given number is in the set of possibilities
     *
     * @param n a number
     * @return true if n is in the set of possibilities
     */
    @NotNull
    public Boolean containsPossibility(int n) {
        //TODO: implement this
        for (int i : possibility) {
            if (i == n)
                return true;
        }
        return false;
    }

    /**
     * Returns true if the cell has no possibility
     *
     * @return true if the set of possibilities is empty
     */
    @NotNull
    public Boolean emptyPossibility() {
        //TODO: implement this
        return (possibility.size()==0);
    }

    /**
     * Adds the possibility of a given number, if possible, and notifies an EnabledEvent if the set of possibilities,
     * previously empty, becomes non-empty. Even (resp., odd) cells have only even (resp., odd) possibilities. Also,
     * if this number is already used by another cell in the same group with this cell, the number cannot be added to
     * the set of possibilities.
     *
     * @param number the number
     */
    public void addPossibility(int number) {
        //TODO: implement this
        if(number %2 == 0) {
            if (type == Type.ODD){
                System.out.print("Even number in Odd cell. ");
                return;
            }
        }
        else {
            if (type == Type.EVEN){
                System.out.print("Odd number in Even cell. ");
                return;
            }
        }

        for (Group g : groups){
            if(!g.isAvailable(number)) {
                System.out.print("Number not available. ");
                return;
            }
        }

        if(possibility.size() == 0)
            notifyObservers(new EnabledEvent());

        possibility.add(number);
    }

    /*
     * Removes the possibility of a given number. Notifies a DisabledEvent if the set of possibilities becomes empty.
     * Note that even (resp., odd) cells have only even (resp., odd) possibilities.
     *
     * @param number the number
     */
    public void removePossibility(int number) {
        //TODO: implement this
        boolean found =false;
        int i = 0;
        if(number %2 == 0) {
            if (type == Type.ODD){
                System.out.print("Even number in Odd cell.");
                return;
            }
        }
        else {
            if (type == Type.EVEN){
                System.out.print("Odd number in Even cell. ");
                return;
            }
        }

        while (!found && i < possibility.size()) {
            if(number == possibility.get(i)){
                possibility.remove(i);
                found = true;
            }
            i++;
        }

        if(possibility.size() == 0 && !this.getNumber().isPresent())
            notifyObservers(new DisabledEvent());
    }
}
