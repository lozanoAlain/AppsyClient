/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.HashSet;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for the psychologist, it extends from the entity User which gives the
 * attributes: idUser, password, privilege, status, fullName, email and login.
 * This entity has the fields: specialization and office
 *
 * @author Ilia Consuegra
 */
@XmlRootElement(name="psychologist")
public class Psychologist extends User implements Serializable {

    //@GeneratedValue(strategy = GenerationType.AUTO)
    private static final long serialVersionUID = 1L;
    private String specialization;
    /**
     * The attribute for the office of the psychologist
     */

    private String office;
    private HashSet<Resource> resources;
    private HashSet<Appointment> appointments;

    /**
     * @return the specialization
     */
    public String getSpecialization() {
        return specialization;
    }

    /**
     * @param specialization the specialization to HashSet
     */
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * @return the office
     */
    public String getOffice() {
        return office;
    }

    /**
     * @param office the office to HashSet
     */
    public void setOffice(String office) {
        this.office = office;
    }

    /**
     * @return the resources
     */
        @XmlTransient
    public HashSet<Resource> getResources() {
        return resources;
    }

    /**
     * @param resources the resources to HashSet
     */
    public void setResources(HashSet<Resource> resources) {
        this.resources = resources;
    }

    /**
     * @return the appointments
     */
    public HashSet<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * @param appointments the appointments to HashSet
     */
    public void setAppointments(HashSet<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return getFullName();
    }
    
}
