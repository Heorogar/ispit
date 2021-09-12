/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;

public class KlasaTabeleKlijenta implements Serializable{
    private int id;

    public KlasaTabeleKlijenta() {
        id=-2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
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
        final KlasaTabeleKlijenta other = (KlasaTabeleKlijenta) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
