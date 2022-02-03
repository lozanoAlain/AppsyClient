/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.naming.OperationNotSupportedException;

/**
 *  This factory returns an instance of the Psychologist interface and we use it to separate layers.
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
