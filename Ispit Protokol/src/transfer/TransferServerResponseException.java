/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

import transfer.Opcode.ErrorCodes;

public class TransferServerResponseException extends Exception{
    private ErrorCodes error;
    public TransferServerResponseException(ErrorCodes error) {
//        super(errors.get(error));
        this.error=error;
    }

    public ErrorCodes getError() {
        return error;
    }    
}
