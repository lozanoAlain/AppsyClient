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
 *  This class is the interface of the users, here are all the methods that are implemented by the Manager.
 * @author Alain Lozano
 */
public interface UserInterface {
    /**
     * 
     * @return
     * @throws BusinessLogicException 
     */
    public String countREST() throws BusinessLogicException;
    /**
     * This method is used to edit the user
     * @param requestEntity the user
     * @param id the id os the user to edit 
     * @throws BusinessLogicException in case the server throws and error
     */
    public void edit(Object requestEntity, String id) throws BusinessLogicException;
    /**
     * This method is to reset the password by the email
     * @param email the email of the user
     * @throws BusinessLogicException in case the server throws and error
     */
    public void resetPasswordByEmail(String email) throws BusinessLogicException;
    /**
     * This method is to find the user
     * @param id the id of the user to find
     * @return the user founded
     * @throws BusinessLogicException in case the server throws and error
     */
    public User find(String id) throws BusinessLogicException;
    /**
     * 
     * @param <T>
     * @param responseType
     * @param from
     * @param to
     * @return
     * @throws BusinessLogicException 
     */
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException;
    /**
     * This method is to create a user
     * @param user the user to create
     * @throws BusinessLogicException in case the server throws and error
     */
    public void create(User user) throws BusinessLogicException;
    /**
     * This method is used to login by the user login and passoword
     * @param login the user login
     * @param password the user password
     * @return the user founded
     * @throws BusinessLogicException in case the server throws and error
     * @throws NotAuthorizedException in case the user password is incorrect
     */
    public User findUserByLoginAndPassword(String login, String password) throws BusinessLogicException, NotAuthorizedException;
    /**
     * This method is to find all the users
     * @param <T>
     * @param responseType
     * @return all the users
     * @throws BusinessLogicException in case the server throws and error
     */
    public <T> T findAll(Class<T> responseType) throws BusinessLogicException;
    /**
     * This method is to remove a user
     * @param id the id of the user to remove
     * @throws BusinessLogicException in case the server throws and error
     */
    public void remove(String id) throws BusinessLogicException;
    /**
     * This method is to find a user by the login
     * @param login the login of the user
     * @throws UserAlreadyExistException in case the user already exist
     * @throws BusinessLogicException in case the server throws and error
     */
    public void findUserByLogin(String login) throws UserAlreadyExistException,BusinessLogicException;
    /**
     * This method is to change the password of the user by the login
     * @param login the login of the user
     * @param password the new password 
     * @throws BusinessLogicException in case the server throws and error
     */
    public void changePasswordByLogin(String login, String password) throws BusinessLogicException;
    
    
}
