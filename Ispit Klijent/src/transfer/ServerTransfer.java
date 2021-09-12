/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.Opcode.ErrorCodes;

public class ServerTransfer {
    private Socket soket;
    
    public ServerTransfer(String server, int port) throws TransferServerResponseException {
        try {
            soket=new Socket(server, port);
        } catch (IOException ex) {
            throw new TransferServerResponseException(ErrorCodes.SERVER_ERROR);
        }
    }
    public ServerResponse transfer(KlijentRequest request) throws TransferServerResponseException {
        try {
            sendRequest(request);
        } catch (IOException ex) {
            throw new TransferServerResponseException(ErrorCodes.SERVER_ERROR);
        }
        try {    
            ServerResponse response=receiveResponse();
            if(!response.getError().equals(ErrorCodes.NO_ERROR))
                throw new TransferServerResponseException(response.getError());
            return response;
        } catch (IOException | ClassNotFoundException ex) {
            throw new TransferServerResponseException(ErrorCodes.SERVER_ERROR);
        }
    }
    private ServerResponse receiveResponse() throws IOException, ClassNotFoundException {
        ObjectInputStream input=new ObjectInputStream(soket.getInputStream());
        ServerResponse response=(ServerResponse) input.readObject();
        return response;
    }
    private void sendRequest(KlijentRequest request) throws IOException {
        ObjectOutputStream output=new ObjectOutputStream(soket.getOutputStream());
        output.writeObject(request);
    }
}
