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
    public void edit(Object appointment, String id) throws ClientErrorException {
        appointmentRestful.edit(appointment, id);
    }

    @Override
    public Appointment find(String id) throws ClientErrorException {
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
    public Set<Appointment> findAppointmentsOfPsychologist(String psychologistId) throws ClientErrorException {
        Set<Appointment> appointments = appointmentRestful.findAppointmentsOfPsychologist(new GenericType<Set<Appointment>>(){}, psychologistId);
        return appointments;
    }

    @Override
    public Set<Appointment> findAppointmentsOfClient(String clientId) throws ClientErrorException {
        Set<Appointment> appointments = appointmentRestful.findAppointmentsOfClient(new GenericType<Set<Appointment>>(){}, clientId);
        return appointments;
    }
    
    @Override
    public Set<Appointment> findAppointmentsOfClientByPsychologist(String psychologistId, String clientId) throws ClientErrorException {
        Set<Appointment> appointments = appointmentRestful.findAppointmentsOfClientByPsychologist(new GenericType<Set<Appointment>>(){}, psychologistId, clientId);
        return appointments;
    }

    @Override
    public Set<Appointment> findAll() throws ClientErrorException { 
        Set<Appointment> appointments = appointmentRestful.findAll(new GenericType<Set<Appointment>>(){});
        return appointments;
    }
    
    @Override
    public Set<Appointment> findAppointmentsByDate(String date) throws ClientErrorException {
        Set<Appointment> appointments = appointmentRestful.findAppointmentsByDate(new GenericType<Set<Appointment>>(){}, date);
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

    @Override
    public void removeAppointment(String psychologistId, String clientId) throws ClientErrorException {
        appointmentRestful.removeAppointment(psychologistId, clientId);
    }

    

    

    
}
