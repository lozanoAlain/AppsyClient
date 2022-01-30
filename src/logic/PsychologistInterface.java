/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Psychologist;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author Alain Lozano
 */
public interface PsychologistInterface {
    public String countREST() throws ClientErrorException;
    public void editPsychologist(Object requestEntity, String id) throws ClientErrorException;
    public Psychologist findPsychologist(String id) throws ClientErrorException;
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;
    public void createPsychologist(Object requestEntity) throws ClientErrorException;
    public Set<Psychologist> findAllPsychologist() throws Exception;
    public void removePsychologist(String id) throws ClientErrorException;
    public Psychologist findPsychologistByFullName(String fullName) throws ClientErrorException;
}
