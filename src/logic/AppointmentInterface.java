package logic;


import entities.Appointment;
import exceptions.BusinessLogicException;
import java.util.Set;

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
    public String countREST() throws BusinessLogicException;
    public void edit(Object requestEntity, String id) throws BusinessLogicException;
    public Appointment find(String id) throws BusinessLogicException;
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException;
    public void create(Object requestEntity) throws BusinessLogicException;
    public Set<Appointment> findAppointmentsOfPsychologist(String psychologistId) throws BusinessLogicException;
    public Set<Appointment> findAppointmentsOfClient(String clientId) throws BusinessLogicException;
    public Set<Appointment> findAppointmentsOfClientByPsychologist(String psychologistId, String clientId) throws BusinessLogicException;
    public Set<Appointment> findAppointmentsByDate(String date) throws BusinessLogicException;
    public Set<Appointment> findAll() throws BusinessLogicException;
    public void remove(String id) throws BusinessLogicException;
    public void removeAppointment(String psychologistId, String clientId) throws BusinessLogicException;
    public void close();
}
