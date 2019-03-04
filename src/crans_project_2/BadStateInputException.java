/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crans_project_2;

/**
 *
 * @author nicka_000
 */
public class BadStateInputException extends Exception {

    /**
     * Creates a new instance of <code>EmptyInputException</code> without detail
     * message.
     */
    public BadStateInputException() {
    }

    /**
     * Constructs an instance of <code>EmptyInputException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BadStateInputException(String msg) {
        super(msg);
    }
}
