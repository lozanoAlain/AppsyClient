/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entities.Psychologist;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:PsychologistFacadeREST
 * [entities.psychologist]<br>
 * USAGE:
 * <pre>
 *        PsychologistRestFul client = new PsychologistRestFul();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * 
 * </pre>
 *
 * @author Alain Lozano
 */
public class PsychologistRestful {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("resources.RestFulConfigFile").getString("URI");

    public PsychologistRestful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.psychologist");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }
    /**
     * This method edit the psychologist
     * @param requestEntity
     * @param id
     * @throws ClientErrorException 
     */
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML),new GenericType<Psychologist>(){});
    }
    /**
     * This method finds a client by the id
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
     * This method find the psychologist by the mail
     * @param <T>
     * @param responseType
     * @param email
     * @return
     * @throws ClientErrorException 
     */
    public <T> T findPsychologistByMail(GenericType<T> responseType, String email) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("email/{0}", new Object[]{email}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * This method creates a psychologist
     * @param requestEntity
     * @throws ClientErrorException 
     */
    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML),new GenericType<Psychologist>(){});
    }

    /**
     * This method finds all psychologist
     * @param <T>
     * @param responseType
     * @return
     * @throws ClientErrorException 
     */
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * This method finds the psychologist by the full name
     * @param <T>
     * @param responseType
     * @param fullName
     * @return
     * @throws ClientErrorException 
     */
    public <T> T findPsychologistByFullName(GenericType<T> responseType, String fullName) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("fullName/{0}", new Object[]{fullName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    /**
     * This method removes a psychologist by the id
     * @param id
     * @throws ClientErrorException 
     */
    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public void close() {
        client.close();
    }

}
