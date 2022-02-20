/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package region;
import connexion.*;
import java.sql.*;
import java.util.*;
import utilisateur.*;
/**
 *
 * @author orion
 */
public class ChefRegion {
    int id;
    int idRegion;
    String nom;
    String mdp;

    public ChefRegion(){}
    public ChefRegion(int id, int idRegion, String nom, String mdp) {
        this.id = id;
        this.idRegion = idRegion;
        this.nom = nom;
        this.mdp = mdp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
    public ChefRegion check(String idRegion, String mdp)
    {
        ChefRegion retour = new ChefRegion();
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            System.out.println("SELECT * FROM ChefRegion WHERE idRegion='"+idRegion+"' AND mdp=sha1('"+mdp+"')");
            rs=stm.executeQuery("SELECT * FROM ChefRegion WHERE idRegion='"+idRegion+"' AND mdp=sha1('"+mdp+"')");
            while(rs.next()){
                retour = new ChefRegion(rs.getInt(1),rs.getInt(2), rs.getString(3), rs.getString(4));
            }
            con.getCon().close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return retour;
    }
    
    
    
}
