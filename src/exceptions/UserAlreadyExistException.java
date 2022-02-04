/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception when the user already exist in the database
 * @author Usuario
 */
public class UserAlreadyExistException extends Exception {

    /**
     * Creates a new instance of <code>UserAlreadyExistException</code> without
     * detail message.
     */
    public UserAlreadyExistException() {
    }

    /**
     * Constructs an instance of <code>UserAlreadyExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}
