/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Client;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author Usuario
 */
public interface ClientInterface {
    
    public void edit(Object requestEntity, String id) throws ClientErrorException;
    public Client find(String id) throws ClientErrorException;
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;
    public <T> T findAll(Class<T> responseType) throws ClientErrorException;
    public void remove(String id) throws ClientErrorException;
    public void changePasswordByLogin(String login, String password) throws ClientErrorException;
}
