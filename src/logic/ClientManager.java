/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Client;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.ClientRestful;

/**
 *
 * @author 2dam
 */
public class ClientManager implements ClientInterface{

    ClientRestful clientRestful = new ClientRestful();
    
    @Override
    public String countREST() throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Object client, String id) throws ClientErrorException {
        clientRestful.edit(client, id);
    }

    @Override
    public Client find(String id) throws ClientErrorException {
        Client client = clientRestful.find(new GenericType<Client>(){}, id);
        return client;
    }

    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object client) throws ClientErrorException {
        clientRestful.create(client);
    }

    @Override
    public <T> T findAll(Class<T> responseType) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
