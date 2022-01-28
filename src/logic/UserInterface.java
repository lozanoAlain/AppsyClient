/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.ws.rs.ClientErrorException;

/**
 *
 * @author Usuario
 */
public interface UserInterface {
    public String countREST() throws ClientErrorException;
    public void edit(Object requestEntity, String id) throws ClientErrorException;
    public <T> T resetPasswordByEmail(Class<T> responseType, String email) throws ClientErrorException;
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException;
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;
    public void create(Object requestEntity) throws ClientErrorException;
    public <T> T findUserByLoginAndPassword(Class<T> responseType, String login, String password) throws ClientErrorException;
    public <T> T changePasswordByLogin(Class<T> responseType, String login, String password) throws ClientErrorException;
    public <T> T findAll(Class<T> responseType) throws ClientErrorException;
    public void remove(String id) throws ClientErrorException;
    public <T> T findUserByLogin(Class<T> responseType, String login) throws ClientErrorException;
    
    
}
