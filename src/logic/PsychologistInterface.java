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
 *  This class is the interface of the psychologist, here are all the methods that are implemented by the Manager.
 * @author Alain Lozano
 */
public interface PsychologistInterface {
    /**
     * 
     * @return
     * @throws ClientErrorException in case the server throws and error
     */
    public String countREST() throws ClientErrorException;
    /**
     * This method is to edit a psychologist
     * @param psychologist the psychologist to edit
     * @throws BusinessLogicException in case the server throws and error
     * @throws NotFoundException in case the server doesnt find a user
     */
    public void editPsychologist(Psychologist psychologist) throws BusinessLogicException, NotFoundException ;
    /**
     * This method to find a psychologist
     * @param id the id of the psychologist to find
     * @return the psychologist found
     * @throws BusinessLogicException in case the server throws and error
     */
    public Psychologist findPsychologist(String id) throws BusinessLogicException;
    /**
     * 
     * @param <T>
     * @param responseType
     * @param from
     * @param to
     * @return
     * @throws BusinessLogicException 
     */
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException;
    /**
     * This method is to create a psychologist
     * @param psychologist the psychologist to create
     * @throws BusinessLogicException in case the server throws and error
     */
    public void createPsychologist(Psychologist psychologist) throws BusinessLogicException;
    /**
     * This method is used to find all psychologist
     * @return all psychologist
     * @throws BusinessLogicException in case the server throws and error
     */
    public Set<Psychologist> findAllPsychologist() throws BusinessLogicException;
    /**
     * This method is used to remove the psychologist
     * @param id the id of the psychologist
     * @throws BusinessLogicException in case the server throws and error
     */
    public void removePsychologist(String id) throws BusinessLogicException;
    /**
     * This method is to find psychologist by the full name
     * @param fullName the full name to search the psychologist
     * @return the founded psychologist
     * @throws BusinessLogicException in case the server throws and error
     */
    public Psychologist findPsychologistByFullName(String fullName) throws BusinessLogicException;
    /**
     * This method is to find a psychologist by mail
     * @param email the email to find the psychologist
     * @return the founded psychologist
     * @throws BusinessLogicException in case the server throws and error 
     */
    public Psychologist findPsychologistByMail(String email) throws BusinessLogicException;
}
