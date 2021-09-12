/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import domen.KlasaTabeleKlijenta;
import domen.KlasaTabeleServer;
import domen.Korisnik;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.TransferServerResponseException;

    //kada cuvas korisnikove izmene cuvaj ih sve, ako ne postoje brisi iz baze

public class DbBroker {
    private Connection conn;
    private String url;
    private String user;
    private String pass;
    private String driver;
//    private PropertyReader pr;
    
    public DbBroker(String driver,String url, String user, String pass) {
//        pr=new PropertyReader(PropertyKonstante.PROPERTY_FILE_PATH);
        this.driver=driver;
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public void otvoriKonekciju(){
        try {
//            Class.forName(pr.vratiProperty(PropertyKonstante.DRIVER));
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn=DriverManager.getConnection(url,user,pass);
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void zatvoriKonekciju(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void commit(){
        try {
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void rollback(){
        try {
            conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<KlasaTabeleKlijenta> popuniTabeluKlijent() throws SQLException{
        String upit="SELECT * FROM PredajePredmet";
        ArrayList<KlasaTabeleKlijenta> res=new ArrayList<>();
        try (PreparedStatement ps=conn.prepareStatement(upit)){
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    Nastavnik n=vratiNastavnika(rs.getInt("NastavnikID"));
                    res.add(new KlasaTabeleKlijenta(n, null, 0, rs.getString("StudijskiProgram"), null, new java.util.Date()));
                }
            }
        }
        return res;
    }
    public ArrayList<KlasaTabeleServer> popuniTabelu() throws SQLException{
        String upit="SELECT * FROM PredajePredmet";
        ArrayList<KlasaTabeleServer> res=new ArrayList<>();
        try (PreparedStatement ps=conn.prepareStatement(upit)){
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    Nastavnik n=vratiNastavnika(rs.getInt("NastavnikID"));
                    res.add(new KlasaTabeleServer(n, null, 0, rs.getString("StudijskiProgram"), null, new java.util.Date()));
                }
            }
        }
        return res;
    }
    public int vratiKorisnika(Korisnik korisnik) throws SQLException{
        String upit="SELECT * FROM Korisnik WHERE KorisnickoIme=?";
        try(PreparedStatement ps=conn.prepareStatement(upit)){
            ps.setString(1, korisnik.getUsername());
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next() && rs.getString("Lozinka").equals(korisnik.getPassword()))
                    return rs.getInt("KorisnikID");
            }
        }
        return -1;
    }
    public void sacuvaj(ArrayList<KlasaTabeleKlijenta> lista) throws SQLException{
        for(KlasaTabeleKlijenta k: lista){
            String upit="INSERT INTO PredajePredmet VALUES (?,?,?,?,?,?)";
            try(PreparedStatement ps=conn.prepareStatement(upit)){
                ps.setInt(1, k.getN().getId());
                ps.setInt(2, k.getP().getId());
                ps.setInt(3, k.getSemestar());
                ps.setString(4, k.getSmer());
                ps.setDate(5, new Date(k.getDatum().getTime()));
                ps.setInt(6, k.getK().getId());
                ps.executeUpdate();
            }
        }
    }
    public void sacuvajIzmene(ArrayList<KlasaTabeleServer> lista) throws SQLException{
//        for()
//        String upit="UPDATE oglasi SET Pozicija=?,Senioritet=?,Datum_isteka=?,Firma=? WHERE PK=?";
//        try(PreparedStatement ps=conn.prepareStatement(upit)){
//            ps.setString(1, oglas.getPozicija());
//            ps.setString(2, s);
//            ps.setDate(3, new Date(oglas.getDatumIsteka().getTime()));
//            ps.setInt(4, oglas.getFirma().getId());
//            ps.setInt(5, oglas.getId());
//            ps.executeUpdate();
//        }
//    }
    }

    public void obrisi(KlasaTabeleKlijenta k) throws SQLException {
        String upit="DELETE FROM tabela WHERE ID=?";
        try(PreparedStatement ps=conn.prepareStatement(upit)){
            ps.setInt(k.getId());
            ps.executeUpdate();
        }
    }
    
}

