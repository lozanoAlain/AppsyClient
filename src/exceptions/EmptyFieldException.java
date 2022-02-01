/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import javafx.scene.control.Label;

/**
 *
 * @author  Alain Lozano,Ilia Consuegra
 */
public class EmptyFieldException extends Exception {

    /**
     * Constructs an instance of <code>EmptyFieldsException</code> with the
     * specified detail message.
     *
     */
    public EmptyFieldException() {
        super("The field cannot be empty.");
    }

    public EmptyFieldException(Label lblRepeatPasswordError) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}