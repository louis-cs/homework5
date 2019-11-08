package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.events.*;
import edu.postech.csed332.homework5.events.Event;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CellUI extends JTextField implements Observer {

    /**
     * create a cell UI
     *
     * @param cell a cell model
     */
    private static final int width = 45;
    private static final int height = 45;

    CellUI(Cell cell) {
        cell.addObserver(this);
        initCellUI(cell);

        Dimension dim = new Dimension(width, height);
        this.setMinimumSize(dim);
        this.setPreferredSize(dim);

        if (cell.getNumber().isEmpty()) {
            //TODO: whenever the content is changed, cell.setNumber() or cell.unsetNumber() is accordingly invoked.
            // You may use an action listener, a key listener, a document listener, etc.
//            this.addKeyListener(new KeyListener() {
//                @Override
//                public void keyTyped(KeyEvent e) {
//                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
//                        setForeground(Color.BLUE);
//                        setText(cell.getNumber().get().toString());
//                        setEditable(false);
//                        cell.setNumber(e.getKeyChar());
//                    }
//                }
//
//                @Override
//                public void keyPressed(KeyEvent e) {
//
//                }
//
//                @Override
//                public void keyReleased(KeyEvent e) {
//
//                }
//            });
            this.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                private void warn(){
                    if (getText().length()==0)
                        cell.unsetNumber();
                    else if (!((getText().equals("1")) || (getText().equals("2")) || (getText().equals("3")) || (getText().equals("4")) || (getText().equals("5")) || (getText().equals("6")) || (getText().equals("7")) || (getText().equals("8")) || (getText().equals("9")))){
                        JOptionPane.showMessageDialog(null,"Error : illegal argument", "error message", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        cell.setNumber(Integer.parseInt(getText()));
                        //paintImmediately(getVisibleRect());
                    }

                }
            });
        }
    }

    /**
     * Mark this cell UI as active
     */
    public void setActivate() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setEditable(true);
    }

    /**
     * Mark this cell UI as inactive
     */
    public void setDeactivate() {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setEditable(false);
    }

    /**
     * Whenever a cell is changed, this function is called. EnabledEvent and DisabledEvent are handled here.
     * If the underlying cell is enabled, mark this cell UI as active. If the underlying cell is disabled, mark
     * this cell UI as inactive.
     *
     * @param caller the subject
     * @param arg    an argument passed to caller
     */
    @Override
    public void update(Subject caller, Event arg) {
        //TODO: implement this
        if (arg instanceof EnabledEvent) {
            setActivate();
        }
        if (arg instanceof DisabledEvent) {
            setDeactivate();
        }
    }

    /**
     * Initialize this cell UI
     *
     * @param cell a cell model
     */
    private void initCellUI(Cell cell) {
        setFont(new Font("Times", Font.BOLD, 30));
        setHorizontalAlignment(JTextField.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (cell.getType() == Cell.Type.EVEN)
            setBackground(Color.LIGHT_GRAY);

        if (cell.getNumber().isPresent()) {
            setForeground(Color.BLUE);
            setText(cell.getNumber().get().toString());
            setEditable(false);
        }
    }
}
