/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * This class is for the Client entity, it has the attributes: dateStart and the
 * User attributes: idUser, password, enumPrivilege, enumStatus, fullName, email
 * and login.
 *
 * @author Ilia Consuegra
 */
public class Client extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date dateStart;
    

    private Set<Appointment> appointments;
    
    private Set<ClientResource> clientResources;


    /**
     * @return the dateStart
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * @param dateStart the dateStart to set
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * @return the appointments
     */

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * @param appointments the appointments to set
     */
    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * @return the clientResources
     */

    public Set<ClientResource> getClientResources() {
        return clientResources;
    }

    /**
     * @param clientResources the clientResources to set
     */
    public void setClientResources(Set<ClientResource> clientResources) {
        this.clientResources = clientResources;
    }

}
