/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Psychologist;
import java.util.Set;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.PsychologistRestFul;

/**
 *
 * @author Usuario
 */
public class PsychologistManager implements PsychologistInterface {
    private PsychologistRestFul psychologistRestFul;

    public PsychologistManager() {
        this.psychologistRestFul = new PsychologistRestFul();
    }

    @Override
    public String countREST() throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editPsychologist(Object requestEntity, String id) throws ClientErrorException {
        psychologistRestFul.editPsychologist(requestEntity, id);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createPsychologist(Object requestEntity) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removePsychologist(String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Psychologist findPsychologist(String id) throws ClientErrorException {
        Psychologist psychologist = null;
        psychologist=psychologistRestFul.find(new GenericType<Psychologist>(){}, id);
        return psychologist;
    }

    @Override
    public Set<Psychologist> findAllPsychologist() throws Exception {
        Set <Psychologist> psychologists;       
        psychologists = psychologistRestFul.findAll(new GenericType<Set<Psychologist>>(){});       
        return psychologists;
    }
    
    @Override
    public Psychologist findPsychologistByFullName(String fullName) throws ClientErrorException {
        Psychologist psychologist = null;
        psychologist = psychologistRestFul.findPsychologistByFullName(new GenericType<Psychologist>(){}, fullName);
        return psychologist;
    }
    
   
    
}
