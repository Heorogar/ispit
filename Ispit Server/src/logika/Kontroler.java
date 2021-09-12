/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import forme.GlavnaForma;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modeli.ModelGlavneForme;
import niti.OsveziTabelu;
import niti.PrihvatiKonekcije;
import persistance.DbBroker;

public class Kontroler {
    private static Kontroler instanca;
    private PrihvatiKonekcije server;
    private GlavnaForma gf;
    private DbBroker db;
    private ModelGlavneForme modelGf;
    private String driver;
    private String url;
    private String user;
    private String pass;
    
    public ModelGlavneForme getModelGf() {
        return modelGf;
    }
    
    
    private Kontroler(){
        driver="com.mysql.jdbc.Driver";
        url="jdbc:mysql://localhost:3306/seminarski";
        user="root";
        pass="";
        db=new DbBroker(driver,url,user,pass);
        db.otvoriKonekciju();
        try {
            modelGf=new ModelGlavneForme(db.popuniTabelu());
        } catch (SQLException ex) {
            db.zatvoriKonekciju();
            System.exit(1);
        }
        db.zatvoriKonekciju();
        gf=new GlavnaForma(modelGf);
        gf.setVisible(true);
        prihvatiKonekcije();
    }
    public static Kontroler getInstance(){
        if(instanca==null)
            instanca=new Kontroler();
        return instanca;
    }
    private void refresh(){
        new OsveziTabelu(new DbBroker(driver, url, user, pass)).start();
    }
    private void prihvatiKonekcije(){
        new PrihvatiKonekcije(9400, driver, url, user, pass).start();
    }
    public static void main(String[] args){
//        PropertyReader pr=new PropertyReader(PropertyKonstante.PROPERTY_FILE_PATH);
//        pr.vratiProperty(PropertyKonstante.USER), pr.vratiProperty(PropertyKonstante.PASS));
        Kontroler.getInstance().refresh();
    }
}
