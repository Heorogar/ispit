/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistance.DbBroker;

public class PrihvatiKonekcije extends Thread{
    private int port;
    private String url;
    private String user;
    private String pass;
    private String driver;
    
    public PrihvatiKonekcije(int port, String driver, String url, String user,String pass) {
        this.port=port;
        this.driver=driver;
        this.user=user;
        this.pass=pass;
        this.url=url;
    }
    @Override
    public void run() {
        try(ServerSocket serverSoket=new ServerSocket(port)){
            while(true)
                new UsluziKlijenta(serverSoket.accept(),new DbBroker(driver, url, user, pass)).start();
        } catch (IOException ex) {
            Logger.getLogger(PrihvatiKonekcije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}