package logic;


import entities.Appointment;
import exceptions.BusinessLogicException;
import java.util.Date;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is the interface of the appointment, here are all the methods that are implemented by the Manager.
 * @author Ilia Consuegra
 */
public interface AppointmentInterface {
    /**
     * 
     * @return String
     * @throws BusinessLogicException 
     */
    public String countREST() throws BusinessLogicException;
    
    /**
     * This method is used to edit an appointment
     * @param appointment
     * @throws BusinessLogicException 
     */
    public void edit(Appointment appointment) throws BusinessLogicException;
    
    /**
     * This method is used to find an appointment by the id
     * @param id The id of the appointment
     * @return Appointment
     * @throws BusinessLogicException 
     */
    public Appointment find(String id) throws BusinessLogicException;
    
    /**
     * 
     * @param <T>
     * @param responseType
     * @param from
     * @param to
     * @return
     * @throws BusinessLogicException 
     */
    public <T> T findRange(Class<T> responseType, String from, String to) throws BusinessLogicException;
    
    /**
     * This method is used to create an appointment sending an object of Appointment
     * @param requestEntity The appointment object
     * @throws BusinessLogicException 
     */
    public void create(Appointment requestEntity) throws BusinessLogicException;
    
    /**
     * This method is used to find an appointment of a psychologist
     * @param psychologistId  The id of the psychologist
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    public Set<Appointment> findAppointmentsOfPsychologist(String psychologistId) throws BusinessLogicException;
    
    /**
     * This method is used to find an appointment of the client
     * @param clientId The id of the client
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    public Set<Appointment> findAppointmentsOfClient(String clientId) throws BusinessLogicException;
    
    /**
     * This method is used to find an appointment of a client and of a psychologist
     * @param psychologistId The id of the psychologist
     * @param clientId The id of the client
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    public Set<Appointment> findAppointmentsOfClientByPsychologist(String psychologistId, String clientId) throws BusinessLogicException;
    
    /**
     * This method is used to find an appointment by a date
     * @param date The date selected
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    public Set<Appointment> findAppointmentsByDate(String date) throws BusinessLogicException;
    
    /**
     * This method is used to find all the appointment of the database 
     * @return A list of appointments
     * @throws BusinessLogicException 
     */
    public Set<Appointment> findAll() throws BusinessLogicException;
    
    /**
     * This method is used to delete an appointment of the database
     * @param id The id of ther appointment
     * @throws BusinessLogicException 
     */
    public void remove(String id) throws BusinessLogicException;
    
    /**
     * This method is used to delete an appointment by the id of the psychologist and the id of the client
     * @param psychologistId
     * @param clientId
     * @throws BusinessLogicException 
     */
    public void removeAppointment(String psychologistId, String clientId) throws BusinessLogicException;
    
    public void close();
}