/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Matteo Fernández
 */
public class ClientResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private IdClientResource idClientResource;
    private Resource resource;
    private Client client;
    private String typeDiagnose;

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getTypeDiagnose() {
        return typeDiagnose;
    }

    public void setTypeDiagnose(String typeDiagnose) {
        this.typeDiagnose = typeDiagnose;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.resource);
        hash = 17 * hash + Objects.hashCode(this.idClientResource);
        hash = 17 * hash + Objects.hashCode(this.typeDiagnose);
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
        final ClientResource other = (ClientResource) obj;
        if (!Objects.equals(this.typeDiagnose, other.typeDiagnose)) {
            return false;
        }
        if (!Objects.equals(this.resource, other.resource)) {
            return false;
        }
        if (!Objects.equals(this.idClientResource, other.idClientResource)) {
            return false;
        }
        return true;
    }

}