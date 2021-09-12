/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import domen.KlasaTabeleKlijenta;
import domen.KlasaTabeleServer;
import domen.Korisnik;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistance.DbBroker;
import transfer.KlijentRequest;
import transfer.Opcode.ErrorCodes;
import transfer.ServerResponse;

public class UsluziKlijenta extends Thread{
    private Socket soket;
    private Korisnik klijent;
    private DbBroker db;
    
    UsluziKlijenta(Socket soket, DbBroker db) {
        this.soket=soket;
        this.db=db;
    }
    @Override
    public void run() {
        while(true){
            try {
                KlijentRequest request=primiZahtev();
                ServerResponse response=odgovoriNaZahtev(request);
                posaljiOdgovor(response);
            } catch (IOException ex) {
                try {
                    soket.close();
                } catch (IOException ex1) {
                    Logger.getLogger(UsluziKlijenta.class.getName()).log(Level.SEVERE, null, ex1);
                }
                return;
            }
        }
    }

    private KlijentRequest primiZahtev() throws IOException {
        KlijentRequest request=null;
        try{
            ObjectInputStream input=new ObjectInputStream(soket.getInputStream());
            request=(KlijentRequest) input.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsluziKlijenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request;
    }

    private void posaljiOdgovor(ServerResponse response) throws IOException {
        if(response==null)
            throw new IOException();
        ObjectOutputStream output=new ObjectOutputStream(soket.getOutputStream());
        output.writeObject(response);
    }

    private ServerResponse odgovoriNaZahtev(KlijentRequest request){
        ServerResponse response=new ServerResponse(null);
        db.otvoriKonekciju();
        try{
            switch(request.getOperacija()){
                case LOGIN: 
                    int id=db.vratiKorisnika((Korisnik)request.getData());
                    if(id==-1)
                        response.setError(ErrorCodes.LOGIN_ERROR);
                    else
                        response.setData(id);
                    break;
                case POPUNI_TABELU:
                    response.setData(db.popuniTabeluKlijent());
                    break;
                case SACUVAJ:
                    db.sacuvaj((ArrayList<KlasaTabeleKlijenta>) request.getData());
                    db.commit();
                    break;
                case SACUVAJ_IZMENJENU_LISTU:
                    db.sacuvajIzmene((ArrayList<KlasaTabeleServer>) request.getData());
                    break;
                case OBRISI:
                    db.obrisi((KlasaTabeleKlijenta) request.getData());
                    break;
            }
            db.commit();
        }
        catch(SQLException e){
            response.setError(ErrorCodes.SERVER_ERROR);
        }
        db.zatvoriKonekciju();
        return response;
    }
    
}