/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Client;
import entities.Psychologist;
import java.util.Set;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.ClientRestFul;

/**
 *
 * @author Usuario
 */
public class ClientManager implements ClientInterface{
    ClientRestFul clienRestFul = new ClientRestFul();

    @Override
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        clienRestFul.edit(requestEntity, id);
    }

    @Override
    public Client find(String id) throws ClientErrorException {
        Client client=clienRestFul.find(new GenericType<Client>(){}, id);
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
    public void remove(String id) throws ClientErrorException {
        clienRestFul.remove(id);
    }

    @Override
    public void changePasswordByLogin(String login, String password) throws ClientErrorException {
        Client client=clienRestFul.changePasswordByLogin(new GenericType<Client>(){}, login, password);
    }
    
}
