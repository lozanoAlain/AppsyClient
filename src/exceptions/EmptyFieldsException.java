/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception in case the TextField is empty
 * @author  Alain Lozano,Ilia Consuegra
 */
public class EmptyFieldsException extends Exception {

    /**
     * Constructs an instance of <code>EmptyFieldsException</code> with the
     * specified detail message.
     *
     */
    public EmptyFieldsException() {
        super("The field cannot be empty.");
    }
}