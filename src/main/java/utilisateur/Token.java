/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilisateur;

import java.util.Date;
import java.util.Calendar;
import connexion.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;

/**
 *
 * @author pc
 */
public class Token {
     int idUser;
     String valeur;
     Date validite;
    
    public Token(){}
    public Token(String val) {
    	setValeur(val);
    }
    public Token(int iduser, String val, Date valid)
    {
       setIdUser(iduser);
       setValeur(val);
       setValidite(valid);
    }
    
    public void setIdUser(int id)
    {
       this.idUser = id;
    }
    public int getIdUser()
    {
       return this.idUser;
    }
    public void setValeur(String id)
    {
       this.valeur = id;
    }
    public String getValeur()
    {
       return this.valeur;
    }
    public void setValidite(Date id)
    {
       this.validite = id;
    }
    public Date getValidite()
    {
       return this.validite;
    }

    public void deleteToken(int token)
    {
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            System.out.println("DELETE FROM Token WHERE idUser="+token);
            stm.executeUpdate("DELETE FROM Token WHERE idUser="+token);
            con.getCon().close();stm.close();rs.close();
        }
        catch(Exception e){
             e.printStackTrace();
        }
    }

    public void deleteToken(String token)
    {
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            System.out.println("DELETE FROM Token WHERE valeur="+token);
            stm.executeUpdate("DELETE FROM Token WHERE valeur="+token);
            con.getCon().close();stm.close();rs.close();
        }
        catch(Exception e){
             e.printStackTrace();
        }
    }
    
    private static String encryptPassword(String input) throws Exception {

        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(input.getBytes("UTF-8"));

        return new BigInteger(1, crypt.digest()).toString(16);
    }
    
    public void createToken(int id)
    {
    	this.idUser = id;
    	
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        
        Calendar cal = Calendar.getInstance();
        String dateString = id+""+Integer.toString(cal.get(cal.YEAR))+"-"+Integer.toString(cal.get(cal.MONTH)+1)+"-"+Integer.toString(cal.get(cal.DATE))+Integer.toString(cal.get(cal.HOUR))+"-"+Integer.toString(cal.get(cal.MINUTE))+"-"+Integer.toString(cal.get(cal.SECOND))+"-"+Integer.toString(cal.get(cal.MILLISECOND));
        System.out.println(dateString);

        Date validite = new Date(cal.get(cal.YEAR)-1900, cal.get(cal.MONTH), cal.get(cal.DATE));
        cal.setTime(validite);
        cal.add(Calendar.DATE, 1);
        this.validite = cal.getTime();
        
        String validiteString = Integer.toString(cal.get(cal.YEAR))+"-"+Integer.toString(cal.get(cal.MONTH)+1)+"-"+Integer.toString(cal.get(cal.DATE));
        System.out.println(validiteString);

        try{
        	this.valeur = encryptPassword(dateString);
            stm=con.getCon().createStatement();
            System.out.println("INSERT INTO Token VALUES('"+id+"', '"+this.valeur+"', '"+validiteString+"')");
            stm.executeUpdate("INSERT INTO Token VALUES('"+id+"', '"+this.valeur+"', '"+validiteString+"')");
            stm.close();con.getCon().close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void getUser() {
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            rs=stm.executeQuery("SELECT * FROM Token WHERE valeur='"+this.valeur+"'");
            while(rs.next()){

                String[] espace =  rs.getString(3).split(" ");
                String[] date = espace[0].split("-");

                Date temp = new Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])+1);	
                
                this.setIdUser(rs.getInt(1));
                this.setValidite(temp);
            }
            stm.close();con.getCon().close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Token find(int id)
    {
        Token retour = new Token();
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            System.out.println("SELECT * FROM Token WHERE idUser="+id+" ORDER BY validite DESC");
            rs=stm.executeQuery("SELECT * FROM Token WHERE idUser="+id+" ORDER BY validite DESC");
            while(rs.next()){

                String[] espace =  rs.getString(3).split(" ");
                String[] date = espace[0].split("-");

                Date temp = new Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])+1);	
                
                retour = new Token(rs.getInt(1),rs.getString(2), temp);
             
                break;
            }
            con.getCon().close();stm.close();rs.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return retour;
    }
    public Token findFromValue(String token)
    {
        Token retour = new Token();
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            System.out.println("SELECT * FROM Token WHERE valeur='"+token+"'");
            rs=stm.executeQuery("SELECT * FROM Token WHERE valeur='"+token+"'");
            while(rs.next()){

                String[] espace =  rs.getString(3).split(" ");
                String[] date = espace[0].split("-");

                Date temp = new Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])+1);	
                
                retour = new Token(rs.getInt(1), rs.getString(2), temp);
             
                break;
            }
            con.getCon().close();stm.close();rs.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return retour;
    }

}
