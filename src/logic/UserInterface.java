/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.User;
import exceptions.BussinesLogicException;
import exceptions.PasswordDontMatch;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Usuario
 */
public interface UserInterface {
    public String countREST() throws ClientErrorException;
    public void edit(Object requestEntity, String id) throws ClientErrorException;
    public <T> T resetPasswordByEmail(Class<T> responseType, String email) throws ClientErrorException;
    public User find(String id) throws ClientErrorException;
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;
    public void create(User user) throws BussinesLogicException;
    public User findUserByLoginAndPassword(String login, String password) throws ClientErrorException, NotAuthorizedException;
    public <T> T findAll(Class<T> responseType) throws ClientErrorException;
    public void remove(String id) throws ClientErrorException;
    public User findUserByLogin(String login) throws ClientErrorException;
    public void changePasswordByLogin(String login, String password) throws ClientErrorException,PasswordDontMatch;
    
    
}
