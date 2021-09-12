/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domen.KlasaTabeleServer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

public class ModelGlavneForme extends AbstractTableModel{
    private ArrayList<KlasaTabeleServer> lista;
    private ArrayList<KlasaTabeleServer> celaLista;
    private String filterCombo;
    private String filterText;
    
    public ModelGlavneForme(ArrayList<KlasaTabeleServer> lista){
        this.lista=lista;
        celaLista=(ArrayList<KlasaTabeleServer>) lista.clone();
        filterCombo="Sve";
        filterText="Sve";
    }

    public ArrayList<KlasaTabeleServer> getLista() {
        return lista;
    }

    public void setLista(ArrayList<KlasaTabeleServer> lista) {
        this.celaLista = lista;
        if(filterCombo.equals("Sve") && filterText.equals("Sve"))
            fireTableDataChanged();
        else if(!filterCombo.equals("Sve"))
            updateCombo(filterCombo);
        else
            updateText(filterText);
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
        KlasaTabeleServer current=lista.get(rowIndex);
        Object res=null;
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
        switch(columnIndex){
            case 0: res="foo"; 
                break;
            case 1: res="bar"; 
                break;
            case 2: res=sdf.format(new Date()); 
                break;
        }
        return res;
    }
    @Override
    public String getColumnName(int column) {
        String res="";
        switch(column){
            case 0: res="a"; 
                break;
            case 1: res="b";
                break;
            case 2: res="c";
                break;
        }
        return res;
    }
    public synchronized void updateText(String text){
        lista=celaLista;
        filterText=text;
        filterCombo="Sve";
        fireTableDataChanged();
    }
    public synchronized void updateCombo(String combo) {
        lista=celaLista;
//        if(!combo.equals("Sve")){
//            ArrayList<KlasaTabeleKlijenta> temp=new ArrayList<>();
//            for(KlasaTabeleKlijenta k:lista)
//                if(k.getSmer().equals(combo))
//                    temp.add(k);
//            lista=temp;
//        }
        filterCombo=combo;
        filterText="Sve";
        fireTableDataChanged();
    }
    
}
