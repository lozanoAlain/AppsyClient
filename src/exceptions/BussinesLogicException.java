/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Usuario
 */
public class BussinesLogicException extends Exception {

    /**
     * Creates a new instance of <code>BussinesLogicException</code> without
     * detail message.
     */
    public BussinesLogicException() {
    }

    /**
     * Constructs an instance of <code>BussinesLogicException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BussinesLogicException(String msg) {
        super(msg);
    }
}
