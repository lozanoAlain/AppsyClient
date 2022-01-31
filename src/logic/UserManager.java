/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Client;
import entities.Psychologist;
import entities.User;
import exceptions.BusinessLogicException;
import exceptions.PasswordDontMatch;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.GenericType;
import restful.UserRestFul;

/**
 *
 * @author Usuario
 */
public class UserManager implements UserInterface{
    UserRestFul userRestFul = new UserRestFul();

    @Override
    public String countREST() throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T resetPasswordByEmail(Class<T> responseType, String email) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User find(String id) throws ClientErrorException {
        User user=userRestFul.find(new GenericType<User>(){}, id);
        return user;
    }

    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(User user) throws BusinessLogicException {
        userRestFul.create(user);
    }

    @Override
    public User findUserByLoginAndPassword(String login, String password) throws ClientErrorException, NotAuthorizedException {
        User useraux=userRestFul.findUserByLoginAndPassword(new GenericType<User>(){}, login, password);
        return useraux;
    }

    @Override
    public <T> T findAll(Class<T> responseType) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(String id) throws ClientErrorException {
        userRestFul.remove(id);
    }

    @Override
    public User findUserByLogin(String login) throws ClientErrorException {
        User user = userRestFul.findUserByLogin(new GenericType<User>(){}, login);
        return user;
    }
    @Override
    public void changePasswordByLogin(String login, String password) throws ClientErrorException{
        userRestFul.changePasswordByLogin(new GenericType<Client>(){}, login, password);
    }
    
}