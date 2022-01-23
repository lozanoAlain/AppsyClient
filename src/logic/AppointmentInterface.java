package logic;


import entities.Appointment;
import java.util.Set;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HP
 */
public interface AppointmentInterface {
    public String countREST() throws ClientErrorException;
    public void edit(Object requestEntity, String id) throws ClientErrorException;
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException;
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException;
    public void create(Object requestEntity) throws ClientErrorException;
    public Set<Appointment> findAppointmentsByPsychologist(String psychologistId) throws ClientErrorException;
    public Set<Appointment> findAppointmentsByClient(String clientId) throws ClientErrorException;
    public Set<Appointment> findAll() throws ClientErrorException;
    public void remove(String id) throws ClientErrorException;
    public void close();
}
