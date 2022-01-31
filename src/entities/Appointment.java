/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity for the appointments, it has the attributes: psychologist, client,
 * date, diagnose, numApppointment and price.
 *
 * @author Ilia Consuegra
 */
@XmlRootElement
public class Appointment implements Serializable {

    private static long serialVersionUID = 1L;

    private SimpleObjectProperty<AppointmentId> appointmentId;
    private SimpleObjectProperty<Psychologist> psychologist;
    private SimpleObjectProperty<Client> client;
    private SimpleObjectProperty<Date> date;
    private SimpleObjectProperty<String> diagnose;
    private SimpleObjectProperty<Integer> numAppointment;
    private SimpleObjectProperty<Float> price;

    public Appointment() {
        this.appointmentId = new SimpleObjectProperty();
        this.psychologist = new SimpleObjectProperty();
        this.client = new SimpleObjectProperty();
        this.date = new SimpleObjectProperty();
        this.diagnose = new SimpleObjectProperty();
        this.numAppointment = new SimpleObjectProperty();
        this.price = new  SimpleObjectProperty();
    }

    public Appointment(AppointmentId appointmentId, Psychologist psychologist, Client client, Date date, String diagnose, Integer numAppointment, Float price) {
        this.psychologist = new SimpleObjectProperty(psychologist);
        this.appointmentId = new SimpleObjectProperty(appointmentId);
        this.client = new SimpleObjectProperty(client);
        this.date = new SimpleObjectProperty(date);
        this.diagnose = new SimpleObjectProperty(diagnose);
        this.numAppointment = new SimpleObjectProperty(numAppointment);
        this.price = new  SimpleObjectProperty(price);

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.appointmentId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Appointment other = (Appointment) obj;
        if (!Objects.equals(this.diagnose, other.diagnose)) {
            return false;
        }
        if (!Objects.equals(this.appointmentId, other.appointmentId)) {
            return false;
        }
        if (!Objects.equals(this.psychologist, other.psychologist)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{" + "appointmentId=" + appointmentId + ", psychologist=" + psychologist + ", client=" + client + ", date=" + date + ", diagnose=" + diagnose + ", numAppointment=" + numAppointment + ", price=" + price + '}';
    }

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the appointmentId
     */
    public AppointmentId getAppointmentId() {
        return this.appointmentId.get();
    }

    /**
     * @param appointmentId the appointmentId to set
     */
    public void setAppointmentId(AppointmentId appointmentId) {
        this.appointmentId.set(appointmentId);
    }

    /**
     * @return the psychologist
     */
    @XmlElement(name = "psychologist")
    public Psychologist getPsychologist() {
        return this.psychologist.get();
    }

    /**
     * @param psychologist the psychologist to set
     */
    public void setPsychologist(Psychologist psychologist) {
        this.psychologist.set(psychologist);
    }

    /**
     * @return the client
     */
    @XmlTransient
    public Client getClient() {
        return this.client.get();
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client.set(client);
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return this.date.get();
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date.set(date);
    }

    /**
     * @return the diagnose
     */
    public String getDiagnose() {
        return this.diagnose.get();
    }

    /**
     * @param diagnose the diagnose to set
     */
    public void setDiagnose(String diagnose) {
        this.diagnose.set(diagnose);
    }

    /**
     * @return the numAppointment
     */
    public Integer getNumAppointment() {
        return this.numAppointment.get();
    }

    /**
     * @param numAppointment the numAppointment to set
     */
    public void setNumAppointment(Integer numAppointment) {
        this.numAppointment.set(numAppointment);
    }

    /**
     * @return the price
     */
    public Float getPrice() {
        return this.price.get();
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Float price) {
        this.price.set(price);
    }

}
