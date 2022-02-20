/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package signalement;

/**
 *
 * @author pc
 */

import connexion.*;
import java.sql.*;
import java.util.*;
import java.util.Vector;
import java.util.Date;

public class Signalement {
    int id;
    int idUtilisateur;
    int idCategory;
    String description;
    Date dateSignalement;
    float latitude;
    float longitude;
    
    public Signalement() {
    }

    public Signalement(int id, int idUtilisateur, String description, Date dateSignalement, float latitude, float longitude) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.description = description;
        this.dateSignalement = dateSignalement;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Signalement(int id, int idUtilisateur, int idCategory, String description, Date dateSignalement, float latitude, float longitude) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.idCategory = idCategory;
        this.description = description;
        this.dateSignalement = dateSignalement;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateSignalement() {
        return dateSignalement;
    }

    public void setDateSignalement(Date dateSignalement) {
        this.dateSignalement = dateSignalement;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void insertSignalement(int idUser, int idCat, String desc, float lat, float lng)
    {
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;

        System.out.println("GG Be");
        System.out.println(idUser);
        System.out.println(desc);

        try{
        	if(idUser!=0) {
	            stm=con.getCon().createStatement();
	            System.out.println("INSERT INTO Signalement VALUES(NULL, '"+Integer.toString(idUser)+"', '"+Integer.toString(idCat)+"', '"+desc+"', NOW(), '"+Float.toString(lat)+"', '"+Float.toString(lng)+"')");
	            stm.executeUpdate("INSERT INTO Signalement VALUES(NULL, '"+Integer.toString(idUser)+"', '"+Integer.toString(idCat)+"', '"+desc+"', NOW(), '"+Float.toString(lat)+"', '"+Float.toString(lng)+"')");
	            con.getCon().close();stm.close();
        	}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void insert(int idUser, int idCat, String desc, float lat, float lng, Date d)
    {
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;

        try{
        	if(this.idUtilisateur!=0) {
	            stm=con.getCon().createStatement();
	            System.out.println("INSERT INTO Signalement VALUES(NULL, '"+Integer.toString(idUser)+"', '"+Integer.toString(idCat)+"', '"+desc+"', '"+(d.getYear()+1900)+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"', '"+Float.toString(lat)+"', '"+Float.toString(lng)+"')");
	            stm.executeUpdate("INSERT INTO Signalement VALUES(NULL, '"+Integer.toString(idUser)+"', '"+Integer.toString(idCat)+"', '"+desc+"', '"+(d.getYear()+1900)+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"', '"+Float.toString(lat)+"', '"+Float.toString(lng)+"')");
	            con.getCon().close();stm.close();
        	}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void find() throws Exception {
    	Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;

        try{
            stm=con.getCon().createStatement();
            System.out.println("SELECT * FROM Signalement WHERE idUtilisateur = '"+this.idUtilisateur+"' and idCategory="+this.idCategory+" and dateSignalement = '"+(this.dateSignalement.getYear()+1900)+"-"+(this.dateSignalement.getMonth()+1)+"-"+this.dateSignalement.getDate()+" "+this.dateSignalement.getHours()+":"+this.dateSignalement.getMinutes()+":"+this.dateSignalement.getSeconds()+"'");
            rs=stm.executeQuery("SELECT * FROM Signalement WHERE idUtilisateur = '"+this.idUtilisateur+"' and idCategory="+this.idCategory+" and dateSignalement = '"+(this.dateSignalement.getYear()+1900)+"-"+(this.dateSignalement.getMonth()+1)+"-"+this.dateSignalement.getDate()+" "+this.dateSignalement.getHours()+":"+this.dateSignalement.getMinutes()+":"+this.dateSignalement.getSeconds()+"'");
            while(rs.next())
            {
            	this.setId(rs.getInt(1));
            }
            con.getCon().close();stm.close();;

        }catch(Exception ex){
           ex.printStackTrace();    
        }
    }
    
    public Signalement[] getHistoryOfReport(String id)
    {
        Signalement[] retour=new Signalement[0];
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;

        Vector v = new Vector();

        try{
            stm=con.getCon().createStatement();
            rs=stm.executeQuery("SELECT * FROM Signalement WHERE idUtilisateur = '"+id+"'");
            while(rs.next())
            {
                String[] espace =  rs.getString(5).split(" ");
                String[] date = espace[0].split("-");

                Date temp = new Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])+1);	

                v.add(new Signalement(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getString(4), temp, Float.parseFloat(rs.getString(6)), Float.parseFloat(rs.getString(7))));
            }

            Object[] array = v.toArray();
            retour = Arrays.copyOf(array, array.length, Signalement[].class);

            con.getCon().close();stm.close();rs.close();

        }catch(Exception ex){
           ex.printStackTrace();    
        }     
        return retour;
    }
    public Signalement[] findAll()
    {
        Signalement[] retour=new Signalement[0];
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;

        Vector v = new Vector();

        try{
            stm=con.getCon().createStatement();
            rs=stm.executeQuery("SELECT * FROM Signalement");
            while(rs.next())
            {
                String[] espace =  rs.getString(5).split(" ");
                String[] date = espace[0].split("-");

                Date temp = new Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])+1);	

                v.add(new Signalement(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getString(4), temp, Float.parseFloat(rs.getString(6)), Float.parseFloat(rs.getString(7))));
            }

            Object[] array = v.toArray();
            retour = Arrays.copyOf(array, array.length, Signalement[].class);

            con.getCon().close();stm.close();rs.close();

        }catch(Exception ex){
           ex.printStackTrace();    
        }     
        return retour;
    }
    public Signalement[] findSignalementByRegion(String idRegion)
    {
         Signalement[] retour=new Signalement[0];
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;

        Vector v = new Vector();

        try{
            stm=con.getCon().createStatement();
            rs=stm.executeQuery("SELECT s.* FROM Signalement s, Affectation a WHERE a.idSignalement = s.id and a.idRegion = '"+idRegion+"'");
            while(rs.next())
            {
                String[] espace =  rs.getString(5).split(" ");
                String[] date = espace[0].split("-");

                Date temp = new Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])+1);	

                v.add(new Signalement(rs.getInt(1),rs.getInt(2), rs.getInt(3), rs.getString(4), temp, Float.parseFloat(rs.getString(6)), Float.parseFloat(rs.getString(7))));
            }

            Object[] array = v.toArray();
            retour = Arrays.copyOf(array, array.length, Signalement[].class);

            con.getCon().close();stm.close();rs.close();

        }catch(Exception ex){
           ex.printStackTrace();    
        }     
        return retour;
    }
}
