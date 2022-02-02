/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;


import entities.User;
import exceptions.BusinessLogicException;
import exceptions.PasswordDontMatch;
import exceptions.UserAlreadyExistException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.GenericType;


/**
 *
 * @author Alain Lozano
 */
public interface UserInterface {

    public String countREST() throws BusinessLogicException;
    public void edit(Object requestEntity, String id) throws BusinessLogicException;
    public <T> T resetPasswordByEmail(Class<T> responseType, String email) throws BusinessLogicException;
    public User find(String id) throws BusinessLogicException;
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException;
    public void create(User user) throws BusinessLogicException;
    public User findUserByLoginAndPassword(String login, String password) throws BusinessLogicException, NotAuthorizedException;
    public <T> T findAll(Class<T> responseType) throws BusinessLogicException;
    public void remove(String id) throws BusinessLogicException;
    public void findUserByLogin(String login) throws UserAlreadyExistException,BusinessLogicException;
    public void changePasswordByLogin(String login, String password) throws BusinessLogicException;
    
    
}
