/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Psychologist;
import exceptions.BusinessLogicException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.GenericType;
import restful.PsychologistRestFul;

/**
 *
 * @author Alain Lozano
 */
public class PsychologistManager implements PsychologistInterface {

    private PsychologistRestFul psychologistRestFul;
    private static final Logger LOGGER = Logger.getLogger(PsychologistManager.class.getName());

    public PsychologistManager() {
        this.psychologistRestFul = new PsychologistRestFul();
    }

    @Override
    public String countREST() throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editPsychologist(Psychologist psychologist) throws BusinessLogicException {
        try {
            psychologistRestFul.edit(psychologist, String.valueOf(psychologist.getId()));
        } catch (ServerErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception editing the psychologist , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error editing the psychologist :\n");
        }

    }

    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createPsychologist(Psychologist psychologist) throws BusinessLogicException {
        try {
            psychologistRestFul.create(psychologist);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception creating the psychologist , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating the psychologist :\n");
        }

    }

    @Override
    public void removePsychologist(String id) throws BusinessLogicException {
        try {
            psychologistRestFul.remove(id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception removing the psychologist , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error removing the psychologist :\n");
        }

    }

    @Override
    public Psychologist findPsychologist(String id) throws BusinessLogicException {
        Psychologist psychologist = null;
        try {
            psychologist = psychologistRestFul.find(new GenericType<Psychologist>() {
            }, id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception finding the psychologist , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the psychologist :\n");
        }
        return psychologist;
    }

    @Override
    public Set<Psychologist> findAllPsychologist() throws BusinessLogicException {
        Set<Psychologist> psychologists;
        try {
            psychologists = psychologistRestFul.findAll(new GenericType<Set<Psychologist>>() {
            });
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception finding all psychologist, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all psychologist:\n");
        }
        return psychologists;
    }

    @Override
    public Psychologist findPsychologistByFullName(String fullName) throws BusinessLogicException {
        Psychologist psychologist = null;
        try {
            psychologist = psychologistRestFul.findPsychologistByFullName(new GenericType<Psychologist>() {
            }, fullName);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception finding the psychologist by Full Name, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the psychologist by Full Name:\n");
        }

        return psychologist;
    }

    @Override
    public Psychologist findPsychologistByMail(String email) throws BusinessLogicException {
        Psychologist psychologist = null;
        try {
            psychologist = psychologistRestFul.findPsychologistByMail(new GenericType<Psychologist>() {
            }, email);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception finding the psychologist by the mail, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the psychologist by by the mail:\n");
        }
        return psychologist;
    }

}
 

