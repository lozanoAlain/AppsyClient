/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Alain Lozano,Ilia Consuegra
 */
public class UserNotExistException extends Exception {
   
    /**
     * Constructs an instance of <code>UserNotExistException</code> with the
     * specified detail message.
     *
     */
    public UserNotExistException() {
        super("The user does not exist.");
    }
}