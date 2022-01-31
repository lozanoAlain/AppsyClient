/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author  Alain Lozano,Ilia Consuegra
 */
public class IncorrectPasswordException extends Exception {

    /**
     * Constructs an instance of <code>IncorrectPasswordException</code> with
     * the specified detail message.
     *
     */
    public IncorrectPasswordException() {
        super("The password is incorrect.");
    }
}