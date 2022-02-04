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
import exceptions.UserAlreadyExistException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import restful.UserRestFul;
import view.PsychologistProfileController;

/**
 *  This class implements the methods that communicate with the server for the transport of the user entity.
 * @author Alain Lozano
 */
public class UserManager implements UserInterface {

    UserRestFul userRestFul = new UserRestFul();
    private final static Logger LOGGER = Logger.getLogger(UserManager.class.getName());

    @Override
    public String countREST() throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Object requestEntity, String id) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * This method is to reset the password of a user by their email
     * @param email the email of the user to reset the password
     * @throws BusinessLogicException in case the server throws an error
     */
    @Override
    public void resetPasswordByEmail( String email) throws BusinessLogicException {
        userRestFul.resetPasswordByEmail(new GenericType<Client>() {
            }, email);
    }

    /**
     * This method is to find a user by the id that we recived from the windows
     * @param id the id of the user to find
     * @return the user found
     * @throws BusinessLogicException in case the Server throws an Exception
     */
    @Override
    public User find(String id) throws BusinessLogicException {
        User user = null;
        try {
            user = userRestFul.find(new GenericType<User>() {
            }, id);
        } catch (NotFoundException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception finding the user , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the user :\n" + ex.getMessage());
        }
        return user;
    }

    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * This method is used to create a user reciving the data from the windows
     * @param user the user to create
     * @throws BusinessLogicException in case the Server throws an exception
     */
    @Override
    public void create(User user) throws BusinessLogicException {
        try {
            userRestFul.create(user);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception creating the user , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating the user :\n" + ex.getMessage());
        }
    }
    /**
     * This method is use to log in a user by their login and password
     * @param login the login of the user
     * @param password the password of the user
     * @return the user found by the login and the email
     * @throws BusinessLogicException in case the server thorws an exception 
     */
    @Override
    public User findUserByLoginAndPassword(String login, String password) throws BusinessLogicException {
        User user = null;
        try {
            user = userRestFul.findUserByLoginAndPassword(new GenericType<User>() {
            }, login, password);
        } catch (NotAuthorizedException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception login the user , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error login the user :\n" + ex.getMessage());
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception finding the user , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating the user :\n" + ex.getMessage());
        }
        return user;
    }

    @Override
    public <T> T findAll(Class< T> responseType) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * This method is used to delete a user by the id that we recived from the windows
     * @param id the id of the user to delete
     * @throws BusinessLogicException in case the server throws an error
     */

    @Override
    public void remove(String id) throws BusinessLogicException {
        try {
            userRestFul.remove(id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception removing the user , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error removing the user :\n" + ex.getMessage());
        }
    }
    /**
     * This method is used to find a user by their login recived from the windows
     * @param login the loging of the user to find
     * @throws UserAlreadyExistException the user already exist in the database
     * @throws BusinessLogicException  in case the server throws an error
     */
    @Override
    public void findUserByLogin(String login) throws UserAlreadyExistException,BusinessLogicException {
        User user = null;
        try {
            user = userRestFul.findUserByLogin(new GenericType<User>() {
            }, login);
            if(user!=null)
                throw new UserAlreadyExistException("The user already exist in the database");
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception finding the user by login , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the psychologist :\n" + ex.getMessage());
        }
    }
    /**
     * This method is to change the passord of the user by the login that we recived from the windows
     * @param login the login of the user
     * @param password the new password of the user
     * @throws BusinessLogicException in case the server throws an error
     */
    @Override
    public void changePasswordByLogin(String login, String password) throws BusinessLogicException {
        try {
            userRestFul.changePasswordByLogin(new GenericType<Client>() {
            }, login, password);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "PsychologistManager: Exception changing the user password , {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Exception changing the user password :\n" + ex.getMessage());
        }

    }

}
