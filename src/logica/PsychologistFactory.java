/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.naming.OperationNotSupportedException;

/**
 *
 * @author Alain Lozano
 */
public class PsychologistFactory {
    public static PsychologistInterface createPsychologistRestful() 
            throws OperationNotSupportedException{
        
        PsychologistInterface psychologistinterface=null;
        
        psychologistinterface = new PsychologistManager();
        
        return psychologistinterface;
    }
}
