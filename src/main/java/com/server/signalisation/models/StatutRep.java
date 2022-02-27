package com.server.signalisation.models;

import java.sql.ResultSet;
import java.sql.Statement;

import connexion.Connexion;

public class StatutRep {
	int id;
	int idSignalement;
	int idStatut;
	java.sql.Date dates;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdSignalement() {
		return idSignalement;
	}
	public void setIdSignalement(int idSignalement) {
		this.idSignalement = idSignalement;
	}
	public int getIdStatut() {
		return idStatut;
	}
	public void setIdStatut(int idStatut) {
		this.idStatut = idStatut;
	}
	public java.sql.Date getDates() {
		return dates;
	}
	public void setDates(java.sql.Date dates) {
		this.dates = dates;
	}
	
	public void insert(int idSignalement, int idStatut) {
		Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;

        try{
	            stm=con.getCon().createStatement();
	            System.out.println("INSERT INTO StatutReport VALUES(NULL, "+idSignalement+", "+idStatut+", NOW())");
	            stm.executeUpdate("INSERT INTO StatutReport VALUES(NULL, "+idSignalement+", "+idStatut+", NOW())");
	            con.getCon().close();stm.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}
}
