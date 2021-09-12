/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domen.KlasaTabeleKlijenta;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ModelGlavneForme extends AbstractTableModel{
    private ArrayList<KlasaTabeleKlijenta> lista;
    private ArrayList<KlasaTabeleKlijenta> listaIzmena;

    public ArrayList<KlasaTabeleKlijenta> getListaIzmena() {
        return listaIzmena;
    }

    public ArrayList<KlasaTabeleKlijenta> getLista() {
        return lista;
    }
    
    public void setLista(ArrayList<KlasaTabeleKlijenta> lista) {
        this.lista = lista;
    }

    public void dodaj(KlasaTabeleKlijenta objekat){
        lista.add(objekat);
        listaIzmena.add(objekat);
        fireTableDataChanged();
    }
    public void obrisi(int red){
        KlasaTabeleKlijenta k=lista.get(red);
        for(int i=0;i<listaIzmena.size();i++)
            if(listaIzmena.get(i).equals(k)){
                listaIzmena.remove(i);
                break;
            }
        lista.remove(red);
        fireTableDataChanged();
    }
    public ModelGlavneForme(ArrayList<KlasaTabeleKlijenta> domen) {
        this.lista = domen;
    }
    
    @Override
    public int getRowCount() {
        return lista.size();
    }
    @Override
    public int getColumnCount() {
        return 4;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        KlasaTabeleKlijenta current=lista.get(rowIndex);
        Object res=null;
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
        switch(columnIndex){
            case 0: res="foo"; 
                break;
            case 1: res="bar"; 
                break;
            case 2: res=sdf.format(new Date()); 
                break;
            case 3: res="mar"; 
                break;
        }
        return res;
    }
    @Override
    public String getColumnName(int column) {
        String res="";
        switch(column){
            case 0: res="Pozicija"; 
                break;
            case 1: res="Senioritet";
                break;
            case 2: res="Datum isteka";
                break;
            case 3: res="Firma";
                break;
        }
        return res;
    }
    @Override
    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        KlasaTabeleKlijenta current=lista.get(rowIndex);
        for(int i=0;i<listaIzmena.size();i++)
            if(listaIzmena.get(i).equals(current))
                listaIzmena.set(i, current);
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
        switch(columnIndex){
            case 0: ; // current.setNesto(o)
                break;
            case 1: ;
                break;
//            case 2: try {current.setDatumIsteka(sdf.parse((String) o));
//            } catch (ParseException ex) {
//                JOptionPane.showMessageDialog(null, "Datum mora biti u formatu dd.mm.yyyy");
//            }
//                break;
        }
        
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
