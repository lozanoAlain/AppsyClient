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

    public void edit(Client client) throws BusinessLogicException;

    public Client find(String id) throws BusinessLogicException;

    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException;

    public <T> T findAll(Class<T> responseType) throws BusinessLogicException;

    public void remove(String id) throws BusinessLogicException;

    public void create(Client client) throws BusinessLogicException;

    public Client findClientByFullName(String fullName) throws BusinessLogicException;
}
