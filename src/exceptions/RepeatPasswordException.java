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
public class RepeatPasswordException extends Exception {
    
    /**
     * Constructs an instance of <code>RepeatPasswordException</code> with the
     * specified detail message.
     *
     */
    public RepeatPasswordException() {
        super("The passwords don´t match.");
    }
}