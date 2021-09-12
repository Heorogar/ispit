/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

import java.io.Serializable;
import java.util.Objects;
import transfer.Opcode.Operacija;

public class KlijentRequest implements Serializable{
    private Operacija operacija;
    private Object data;

    public KlijentRequest(Operacija operacija, Object data) {
        this.operacija = operacija;
        this.data = data;
    }
    public Operacija getOperacija() {
        return operacija;
    }
    public void setOperacija(Operacija operacija) {
        this.operacija = operacija;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
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
        final KlijentRequest other = (KlijentRequest) obj;
        if (this.operacija != other.operacija) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }    
}
