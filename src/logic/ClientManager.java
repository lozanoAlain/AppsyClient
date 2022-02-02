/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Client;
import exceptions.BusinessLogicException;
import exceptions.PasswordDontMatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.GenericType;
import restful.ClientRestful;

/**
 *
 * @author Alain Lozano
 */
public class ClientManager implements ClientInterface {

    ClientRestful clienRestFul = new ClientRestful();
    private static final Logger LOGGER = Logger.getLogger(PsychologistManager.class.getName());

    @Override
    public void edit(Client client) throws BusinessLogicException {
        try {
            clienRestFul.edit(client, String.valueOf(client.getId()));
        } catch (ServerErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "ClientManager: Exception editing the client, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error editing the client:\n");
        }
    }

    @Override
    public Client find(String id) throws BusinessLogicException {
        Client client = null;
        try {
            client = clienRestFul.find(new GenericType<Client>() {
            }, id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "ClientManager: Exception finding the client, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the client:\n" );
        }
        return client;
    }

    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T findAll(Class<T> responseType) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(String id) throws BusinessLogicException {
        try {
            clienRestFul.remove(id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "ClientManager: Exception removing the client, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error removing the client:\n" );
        }
    }

    @Override
    public void create(Client client) throws BusinessLogicException {
        try {
            clienRestFul.create(client);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "ClientManager: Exception creating the client, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating the client:\n" );
        }

    }

    @Override
    public Client findClientByFullName(String fullName) throws BusinessLogicException {
        Client client = null;
        try {
            client = clienRestFul.findClientByFullName(new GenericType<Client>() {
            }, fullName);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "ClientManager: Exception finding the client by the Full Name, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the client by the Full Name:\n" );
        }
        return client;
    }

}
