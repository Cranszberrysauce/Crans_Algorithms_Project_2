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
public class EmptyRulesException extends Exception {

    /**
     * Creates a new instance of <code>EmptyRulesException</code> without detail
     * message.
     */
    public EmptyRulesException() {
    }

    /**
     * Constructs an instance of <code>EmptyRulesException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EmptyRulesException(String msg) {
        super(msg);
    }
}
