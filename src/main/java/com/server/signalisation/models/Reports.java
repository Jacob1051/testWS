package com.server.signalisation.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;

import connexion.Connexion;
import signalement.Signalement;

public class Reports {
	int id;
	int idUtilisateur;
	int idCategory;
	String desc;
	java.sql.Date date;
	float latitude;
	float longitude;
	String category;
	String photo;
	String statut;
	int idRegion;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public java.sql.Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}
	public Reports(int id, int idUtilisateur, int idCategory, String desc, Date date, float latitude, float longitude,
			String category, String photo, String statut, int idRegion) {
		super();
		this.id = id;
		this.idUtilisateur = idUtilisateur;
		this.idCategory = idCategory;
		this.desc = desc;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
		this.category = category;
		this.photo = photo;
		this.statut = statut;
		this.idRegion = idRegion;
	}
	
	public Reports[] findByRegion(String idRegion)
    {
		Reports[] retour=new Reports[0];
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;

        Vector v = new Vector();

        try{
            stm=con.getCon().createStatement();
            rs=stm.executeQuery("SELECT * FROM v_Reports");
            while(rs.next())
            {
                //v.add(new Reports());
            }

            Object[] array = v.toArray();
            retour = Arrays.copyOf(array, array.length, Reports[].class);

            con.getCon().close();stm.close();rs.close();

        }catch(Exception ex){
           ex.printStackTrace();    
        }     
        return retour;
    }
}
