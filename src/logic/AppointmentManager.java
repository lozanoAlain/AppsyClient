/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Appointment;
import exceptions.BusinessLogicException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.AppointmentRestful;

/**
 * This class implements the methods that communicate with the server for the transport of the appointment entity.
 * @author Ilia Consuegra
 */
public class AppointmentManager implements AppointmentInterface {

    private static final Logger LOGGER = Logger.getLogger(AppointmentManager.class.getName());
    AppointmentRestful appointmentRestful = new AppointmentRestful();
    
/**
 * 
 * @return String
 * @throws BusinessLogicException 
 */
    @Override
    public String countREST() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is used to edit an appointment
     * @param appointment
     * @throws BusinessLogicException 
     */
    @Override
    public void edit(Appointment appointment) throws BusinessLogicException {
        try {
            //Call to the edit method of the appointmentRestful
            appointmentRestful.edit(appointment, String.valueOf(appointment.getAppointmentId()));
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception editing the appointment, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error editing the appointment:\n" + ex.getMessage());
        }

    }

    /**
     * This method is used to find an appointment by the id
     * @param id The id of the appointment
     * @return Appointment
     * @throws BusinessLogicException 
     */
    @Override
    public Appointment find(String id) throws BusinessLogicException {
        Appointment appointment = null;
        //Call to the find method of the appointmentRestful. It returns an appointment
        appointment = appointmentRestful.find(new GenericType<Appointment>() {
        }, id);
        return appointment;
    }

    /**
     * 
     * @param <T>
     * @param responseType
     * @param from
     * @param to
     * @return
     * @throws BusinessLogicException 
     */
    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is used to create an appointment sending an object of Appointment
     * @param requestEntity The appointment object
     * @throws BusinessLogicException 
     */
    @Override
    public void create(Appointment requestEntity) throws BusinessLogicException {
        try {
            //Call to the create method of the appointmentRestful
            appointmentRestful.create(requestEntity);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception creating the appointment, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating the appointment:\n" + ex.getMessage());
        }

    }

     /**
     * This method is used to find an appointment of a psychologist
     * @param psychologistId  The id of the psychologist
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    @Override
    public Set<Appointment> findAppointmentsOfPsychologist(String psychologistId) throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
            //Call to the findAppointmentsOfPsychologist method of the appointmentRestful. It returns a list of appointments
            appointments = appointmentRestful.findAppointmentsOfPsychologist(new GenericType<Set<Appointment>>() {
            }, psychologistId);

        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception finding the appointments of the psychologist, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the appointments of the psychologist:\n" + ex.getMessage());
        }
        return appointments;
    }

    /**
     * This method is used to find an appointment of the client
     * @param clientId The id of the client
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    @Override
    public Set<Appointment> findAppointmentsOfClient(String clientId) throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
            //Call to the findAppointmentsOfClient method of the appointmentRestful. It returns a list of appointments            
            appointments = appointmentRestful.findAppointmentsOfClient(new GenericType<Set<Appointment>>() {
            }, clientId);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception finding the appointments of the client, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the appointments of the client:\n" + ex.getMessage());
        }
        return appointments;
    }

    /**
     * This method is used to find an appointment of a client and of a psychologist
     * @param psychologistId The id of the psychologist
     * @param clientId The id of the client
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    @Override
    public Set<Appointment> findAppointmentsOfClientByPsychologist(String psychologistId, String clientId) throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
            //Call to the findAppointmentsOfClientByPsychologist method of the appointmentRestful. It returns a list of appointments
            appointments = appointmentRestful.findAppointmentsOfClientByPsychologist(new GenericType<Set<Appointment>>() {
            }, psychologistId, clientId);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception finding the appointments of the client searching by a psychologist's name, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the appointments of the client searching by a psychologist's name:\n" + ex.getMessage());
        }

        return appointments;
    }

    /**
     * This method is used to find all the appointment of the database 
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    @Override
    public Set<Appointment> findAll() throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
            //Call to the findAll method of the appointmentRestful. It returns a list of appointments
            appointments = appointmentRestful.findAll(new GenericType<Set<Appointment>>() {
            });
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception finding all the appointments, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all the appointments:\n" + ex.getMessage());
        }

        return appointments;
    }

    /**
     * This method is used to find an appointment by a date
     * @param date The date selected
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    @Override
    public Set<Appointment> findAppointmentsByDate(String date) throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
            //Call to the findAppointmentsByDate method of the appointmentRestful. It returns a list of appointments
            appointments = appointmentRestful.findAppointmentsByDate(new GenericType<Set<Appointment>>() {
            }, date);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception finding the appointments of the client by date, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding the appointments of the client by date:\n" + ex.getMessage());
        }
        return appointments;
    }

    /**
     * This method is used to delete an appointment of the database
     * @param id The id of ther appointment
     * @throws BusinessLogicException 
     */
    @Override
    public void remove(String id) throws BusinessLogicException {
        try {
            //Call to the remove method of the appointmentRestfull
            appointmentRestful.remove(id);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception deleting the appointment selected by id, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error deleting the appointment selected by id:\n" + ex.getMessage());
        }

    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is used to delete an appointment by the id of the psychologist and the id of the client
     * @param psychologistId
     * @param clientId
     * @throws BusinessLogicException 
     */
    @Override
    public void removeAppointment(String psychologistId, String clientId) throws BusinessLogicException {
        try {
            //Call to the removeAppointment method of the appointmentRestfull
            appointmentRestful.removeAppointment(psychologistId, clientId);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception deleting the appointment selected by psychologist and client, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error deleting the appointment selected by psychologist and client:\n" + ex.getMessage());
        }
        appointmentRestful.removeAppointment(psychologistId, clientId);
    }


}