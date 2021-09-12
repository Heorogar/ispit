/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import domen.KlasaTabeleKlijenta;
import domen.Korisnik;
import forme.GlavnaForma;
import forme.Login;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modeli.ModelGlavneForme;
import transfer.KlijentRequest;
import transfer.Opcode.Operacija;
import transfer.ServerResponse;
import transfer.ServerTransfer;
import transfer.TransferServerResponseException;

public class Kontroler {
    private static Kontroler instance;
    private ModelGlavneForme modelGf;
    private GlavnaForma gf;
    private ServerTransfer komunikacija;
    private Korisnik user;
    
    private Kontroler(){
        try {
            komunikacija=new ServerTransfer("localhost", 9400);
            Login loginf=new Login();
            loginf.setVisible(true);
        } catch (TransferServerResponseException ex) {
            JOptionPane.showConfirmDialog(null,
            "Neuspesno povezivanje sa serverom, molimo Vas proverite Vasu internet konekciju i zauzetost porta ili pokusajte kasnije. Hvala na strpljenju.",
            "Connection failed", JOptionPane.DEFAULT_OPTION);
            System.exit(0);
        }
    }
    public static Kontroler getInstance(){
        if(instance==null)
            instance=new Kontroler();
        return instance;
    }
    public static void main(String[] args){
        Kontroler.getInstance();       
    }
    public boolean login(String username, String password) {
        try {
            Korisnik user=new Korisnik("", "", username, password);
            user.setId((int) komunikacija.transfer(new KlijentRequest(Operacija.LOGIN, user)).getData());
            this.user=user;
        } catch (TransferServerResponseException ex) {
            return false;
        }
        return true;
    }

    public boolean kreirajGlavnuFormu() {
        ServerResponse response;
        try {
            response = komunikacija.transfer(new KlijentRequest(Operacija.POPUNI_TABELU, 0));
        } catch (TransferServerResponseException ex) {
            return false;
        }
        modelGf=new ModelGlavneForme((ArrayList<KlasaTabeleKlijenta>) response.getData());
        gf=new GlavnaForma(modelGf);
        gf.setVisible(true);
        return true;
    }

    public boolean dodajUModel(String combo) {
        if(!combo.equals("")){ //validacija
            modelGf.dodaj(new KlasaTabeleKlijenta());
            return true;
        }
        return false;
    }

    public boolean sacuvaj() {
        try {
            komunikacija.transfer(new KlijentRequest(Operacija.SACUVAJ, modelGf.getListaIzmena()));
        } catch (TransferServerResponseException ex) {
            return false;
        }
//        try {
//            komunikacija.transfer(new KlijentRequest(Operacija.SACUVAJ_IZMENJENU_LISTU, modelGf.getListaIzmena()));
//        } catch (TransferServerResponseException ex) {
//            return false;
//        }
        return true;
    }

    public boolean obrisi(int row) {
        try {
            if(modelGf.getLista().get(row).getId()!=-2)
                komunikacija.transfer(new KlijentRequest(Operacija.OBRISI, modelGf.getLista().get(row)));
            modelGf.obrisi(row);
            return true;
        } catch (TransferServerResponseException ex) {
            return false;
        }
    }

}
