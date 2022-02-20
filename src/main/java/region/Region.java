/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package region;
import connexion.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author orion
 */
public class Region{
    int id;
    String nom;
    
    public Region(){}
    public Region(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int idRegion) {
        this.id = idRegion;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public ArrayList<Region> findAll(){
        ArrayList<Region> retour=new ArrayList<>();
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            rs=stm.executeQuery("SELECT * FROM Region");
            while(rs.next()){
                retour.add(new Region(rs.getInt(1),rs.getString(2)));
            }
        }catch(Exception ex){}
        try{
            con.getCon().close();stm.close();rs.close();
        }catch(Exception ex){}
        
        return retour;
    }

    public Region find(int id)
    {
        Region retour=new Region();
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            rs=stm.executeQuery("SELECT * FROM Region WHERE id='"+Integer.toString(id)+"'");
            while(rs.next()){
                retour = new Region(rs.getInt(1),rs.getString(2));
            }
        }catch(Exception ex){}
        try{
            con.getCon().close();stm.close();rs.close();
        }catch(Exception ex){}
        
        return retour;
    }
}
