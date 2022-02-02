/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import entities.Psychologist;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.OperationNotSupportedException;
import logic.PsychologistFactory;
import logic.PsychologistInterface;

/**
 *
 * @author HP
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            PsychologistInterface psychologistInterface = PsychologistFactory.createPsychologistRestful();
            Psychologist psychologist = psychologistInterface.findPsychologistByFullName("Sigmund Freud");
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
