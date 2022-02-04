/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Client;
import exceptions.BusinessLogicException;
import exceptions.PasswordDontMatch;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;

/**
 *  This class is the interface of the client, here are all the methods that are implemented by the Manager.
 * @author Alain Lozano
 */
public interface ClientInterface {
    /**
     * This method is to edit the client
     * @param client the client to edit
     * @throws BusinessLogicException in case the server throws an exception
     */
    public void edit(Client client) throws BusinessLogicException;
    /**
     * This method is to find a client
     * @param id the id of the client to find 
     * @return the founded client
     * @throws BusinessLogicException in case the server throws and error
     */
    public Client find(String id) throws BusinessLogicException;

    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException;
    /**
     * This method is to search all the psychologist
     * @param <T>
     * @param responseType
     * @return
     * @throws BusinessLogicException in case the server throws and error 
     */
    public <T> T findAll(Class<T> responseType) throws BusinessLogicException;
    /**
     * This method is to removed the client
     * @param id the id of the client to removed
     * @throws BusinessLogicException in case the server throws and error
     */
    public void remove(String id) throws BusinessLogicException;
    /**
     * This method is to create a client
     * @param client the client to create
     * @throws BusinessLogicException in case the server throws and error
     */
    public void create(Client client) throws BusinessLogicException;
    /**
     * This method is to find a client by the full name
     * @param fullName the full name of the client to search
     * @return the client found
     * @throws BusinessLogicException in case the server throws and error
     */
    public Client findClientByFullName(String fullName) throws BusinessLogicException;
}
