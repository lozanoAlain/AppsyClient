/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Psychologist;
import exceptions.BusinessLogicException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Alain Lozano
 */
public interface PsychologistInterface {
    public String countREST() throws ClientErrorException;
	public void editPsychologist(Psychologist psychologist) throws BusinessLogicException, NotFoundException ;
    public Psychologist findPsychologist(String id) throws BusinessLogicException;
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException;
    public void createPsychologist(Psychologist psychologist) throws BusinessLogicException;
    public Set<Psychologist> findAllPsychologist() throws BusinessLogicException;
    public void removePsychologist(String id) throws BusinessLogicException;
    public Psychologist findPsychologistByFullName(String fullName) throws BusinessLogicException;
    public Psychologist findPsychologistByMail(String email) throws BusinessLogicException;

}
