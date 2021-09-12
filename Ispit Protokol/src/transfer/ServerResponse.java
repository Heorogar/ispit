/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

import java.io.Serializable;
import java.util.Objects;
import transfer.Opcode.ErrorCodes;

public class ServerResponse implements Serializable{
    private Object data;
    private ErrorCodes error;

    public ServerResponse(Object data, ErrorCodes error) {
        this.data = data;
        this.error = error;
    }
    public ServerResponse(Object data) {
        this.data = data;
        this.error=ErrorCodes.NO_ERROR;
    }
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ErrorCodes getError() {
        return error;
    }

    public void setError(ErrorCodes error) {
        this.error = error;
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
        final ServerResponse other = (ServerResponse) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

}
