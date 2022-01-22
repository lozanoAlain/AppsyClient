/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Appointment;
import entities.Psychologist;
import java.util.Set;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.AppointmentRestful;

/**
 *
 * @author HP
 */
public class AppointmentManager implements AppointmentInterface{
    AppointmentRestful appointmentRestful = new AppointmentRestful();

    @Override
    public String countREST() throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        appointmentRestful.edit(requestEntity, id);
    }

    @Override
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object requestEntity) throws ClientErrorException {
        appointmentRestful.create(requestEntity);
    }

    @Override
    public Set<Appointment> findAppointmentsByPsychologist(String psychologistId) throws ClientErrorException {
        Set<Appointment> appointments = appointmentRestful.findAppointmentsByPsychologist(new GenericType<Set<Appointment>>(){}, psychologistId);
        return appointments;
    }

    @Override
    public <T> T findAppointmentsByClient(Class<T> responseType, String clientId) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<Appointment> findAll() throws ClientErrorException { 
        Set<Appointment> appointments = appointmentRestful.findAll(new GenericType<Set<Appointment>>(){});
        return appointments;
    }

    @Override
    public void remove(String id) throws ClientErrorException {
        appointmentRestful.remove(id);
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
