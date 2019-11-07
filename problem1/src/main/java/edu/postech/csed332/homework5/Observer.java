package edu.postech.csed332.homework5;

import edu.postech.csed332.homework5.events.Event;

/**
 * An observer that can observe subjects.
 */
interface Observer {
    /**
     * This method is called whenever the observed object is changed.
     *
     * @param caller the subject
     * @param arg    an argument passed to caller
     */
    void update(Subject caller, Event arg);
}
