/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Matteo Fernández
 */
public interface ResourceInterface {

    public <T> T getAllResourcesByPsychologist(Class<T> responseType, String id) throws ClientErrorException;

    public void edit(Object requestEntity, String id) throws ClientErrorException;

    public <T> T find(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;

    public void create(Object requestEntity) throws ClientErrorException;

    public <T> T getAllResourcesByTittle(Class<T> responseType, String tittle) throws ClientErrorException;

    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public void close();
}
