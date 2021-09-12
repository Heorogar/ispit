/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logika.Kontroler;
import persistance.DbBroker;

public class OsveziTabelu extends Thread{
    private DbBroker db;
    public OsveziTabelu(DbBroker db){
        this.db=db;
    }
    @Override
    public void run(){
        while(true){
            db.otvoriKonekciju();
            try {
                Kontroler.getInstance().getModelGf().setLista(db.popuniTabelu());
            } catch (SQLException ex) {

            }
            db.zatvoriKonekciju();
            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OsveziTabelu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
