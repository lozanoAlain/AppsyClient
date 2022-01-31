/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class is for the User entity is has this attributes: id, password,
 * enumPrivilege, enumStatus, fullName, email and login.
 *
 * @author Alain Lozano Isasi
 */
@XmlRootElement
public class User implements Serializable {

    private static long serialVersionUID = 1L;
    private Integer id;
    private String password;
    private EnumPrivilege enumPrivilege;
    private EnumStatus enumStatus;
    private String fullName;
    private String email;
    private String login;
    private HashSet<LastSignIn> lastSignIns;
   

   @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        /* if (getClass() != obj.getClass()) {
        return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.password, other.password)) {
        return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
        return false;
        }
        if (!Objects.equals(this.email, other.email)) {
        return false;
        }
        if (!Objects.equals(this.login, other.login)) {
        return false;
        }
        if (!Objects.equals(this.id, other.id)) {
        return false;
        }
        if (this.getEnumPrivilege() != other.getEnumPrivilege()) {
        return false;
        }
        if (this.getEnumStatus() != other.getEnumStatus()) {
        return false;
        }
        if (!Objects.equals(this.lastSignIns, other.lastSignIns)) {
        return false;
        }*/
        return true;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "User{" + "id=" + getId() + ", password=" + getPassword() + ", enumPrivilege=" + getEnumPrivilege() + ", enumStatus=" + getEnumStatus() + ", fullName=" + getFullName() + ", email=" + getEmail() + ", login=" + getLogin() + '}';
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
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the enumPrivilege
     */
    public EnumPrivilege getEnumPrivilege() {
        return enumPrivilege;
    }

    /**
     * @param enumPrivilege the enumPrivilege to set
     */
    public void setEnumPrivilege(EnumPrivilege enumPrivilege) {
        this.enumPrivilege = enumPrivilege;
    }

    /**
     * @return the enumStatus
     */
    public EnumStatus getEnumStatus() {
        return enumStatus;
    }

    /**
     * @param enumStatus the enumStatus to set
     */
    public void setEnumStatus(EnumStatus enumStatus) {
        this.enumStatus = enumStatus;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the lastSignIns
     */
    public HashSet<LastSignIn> getLastSignIns() {
        return lastSignIns;
    }

    /**
     * @param lastSignIns the lastSignIns to set
     */
    public void setLastSignIns(HashSet<LastSignIn> lastSignIns) {
        this.lastSignIns = lastSignIns;
    }

}