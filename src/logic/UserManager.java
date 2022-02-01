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
import javax.ws.rs.core.GenericType;
import restful.UserRestFul;
import view.PsychologistProfileController;

/**
 *
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

    @Override
    public <T> T resetPasswordByEmail(Class<T> responseType, String email) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User find(String id) throws BusinessLogicException {
        User user = null;
        try {
            user = userRestFul.find(new GenericType<User>() {
            }, id);
        } catch (ClientErrorException ex) {
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
