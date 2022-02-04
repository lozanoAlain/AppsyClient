/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exception that recibes the messages 
 * @author Alain Lozano
 */
public class BusinessLogicException extends Exception {

  
    /**
     * Constructs an instance of <code>EmptyFieldsException</code> with the
     * specified detail message.
     *
     */
    public BusinessLogicException(String message){
        super(message);
    }
}
