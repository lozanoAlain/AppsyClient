/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import logic.UserInterface;

/**
 * Jersey REST client generated for REST resource:UserFacadeREST
 * [entities.user]<br>
 * USAGE:
 * <pre>
        UserRestFul client = new UserRestFul();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author Alain Lozano
 */
public class UserRestFul{

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("resources.RestFulConfigFile").getString("URI");


    public UserRestFul() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.user");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }
    /**
     * This method edits the user by the id
     * @param requestEntity
     * @param id
     * @throws ClientErrorException 
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
    /**
     * This method reset the password of a user by the email
     * @param <T>
     * @param responseType
     * @param email
     * @return
     * @throws ClientErrorException 
     */
    public <T> T resetPasswordByEmail(GenericType<T> responseType, String email) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("resetPassword/{0}", new Object[]{email}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * This method finds a user by the id
     * @param <T>
     * @param responseType
     * @param id
     * @return
     * @throws ClientErrorException 
     */
    public <T> T find(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * This method creates a user
     * @param requestEntity
     * @throws ClientErrorException 
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
    /**
     * This method finds the user by the login and password
     * @param <T>
     * @param responseType
     * @param login
     * @param password
     * @return
     * @throws ClientErrorException 
     */
    public <T> T findUserByLoginAndPassword(GenericType<T> responseType, String login, String password) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("login/{0}/{1}", new Object[]{login, password}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }


    public <T> T findAll(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * This method removes a user by the id
     * @param id
     * @throws ClientErrorException 
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }
    /**
     * This method finds the user by the login
     * @param <T>
     * @param responseType
     * @param login
     * @return
     * @throws ClientErrorException 
     */
    public <T> T findUserByLogin(GenericType<T> responseType, String login) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("login/{0}", new Object[]{login}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * This method changes the password of a client
     * @param <T>
     * @param responseType
     * @param login
     * @param password
     * @return
     * @throws ClientErrorException 
     */
    public <T> T changePasswordByLogin(GenericType<T> responseType, String login, String password) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("changePassword/{0}/{1}", new Object[]{login, password}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void close() {
        client.close();
    }

    
    
}
