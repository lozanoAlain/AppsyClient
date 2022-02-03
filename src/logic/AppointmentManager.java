/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Appointment;
import exceptions.BusinessLogicException;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.AppointmentRestful;

/**
 *
 * @author Ilia Consuegra
 */
public class AppointmentManager implements AppointmentInterface {

    private static final Logger LOGGER = Logger.getLogger(AppointmentManager.class.getName());
    AppointmentRestful appointmentRestful = new AppointmentRestful();

    @Override
    public String countREST() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Appointment appointment) throws BusinessLogicException {
        try {
            appointmentRestful.edit(appointment, String.valueOf(appointment.getAppointmentId()));
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception editing the appointment, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error editing the appointment:\n" + ex.getMessage());
        }

    }

    @Override
    public Appointment find(String id) throws BusinessLogicException {
        Appointment appointment = null;
        appointment = appointmentRestful.find(new GenericType<Appointment>() {
        }, id);
        return appointment;
    }

    @Override
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Appointment requestEntity) throws BusinessLogicException {
        try {
            appointmentRestful.create(requestEntity);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "AppointmentManager: Exception creating the appointment, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating the appointment:\n" + ex.getMessage());
        }

    }

    @Override
    public Set<Appointment> findAppointmentsOfPsychologist(String psychologistId) throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
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

    @Override
    public Set<Appointment> findAppointmentsOfClient(String clientId) throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
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

    @Override
    public Set<Appointment> findAppointmentsOfClientByPsychologist(String psychologistId, String clientId) throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
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

    @Override
    public Set<Appointment> findAll() throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
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

    @Override
    public Set<Appointment> findAppointmentsByDate(String date) throws BusinessLogicException {
        Set<Appointment> appointments;
        try {
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

    @Override
    public void remove(String id) throws BusinessLogicException {
        try {
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

    @Override
    public void removeAppointment(String psychologistId, String clientId) throws BusinessLogicException {
        try {
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