package region;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import connexion.Connexion;
import utilisateur.Token;

public class TokenChefRegion {
	int id;
	int idChefRegion;
    String valeur;
    Date validite;
   
   public TokenChefRegion(){}
   public TokenChefRegion(String val) {
   	setValeur(val);
   }
   public TokenChefRegion(int idChefRegion, String val, Date valid)
   {
	   setIdChefRegion(idChefRegion);
	   setValeur(val);
	   setValidite(valid);
   }
   
   public TokenChefRegion checkLogin(String id, String mdp) {
	   
	   TokenChefRegion t = null;
	   ChefRegion c = new ChefRegion().check(id, mdp);
	   if(c.getId()!=0) {
		   t= createToken(Integer.parseInt(id));
	   }
	   return t;
   }
   
   private static String encryptPassword(String input) throws Exception {

       MessageDigest crypt = MessageDigest.getInstance("SHA-1");
       crypt.reset();
       crypt.update(input.getBytes("UTF-8"));

       String sha = new BigInteger(1, crypt.digest()).toString(16);
       String temp = "";
       int remain = 40 - sha.length();
       if(remain!=0) {
    	   temp += "0";
       } 
       return temp+sha;
   }
   
   public TokenChefRegion createToken(int id)
   {
   	this.idChefRegion = id;
   	
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
           System.out.println("INSERT INTO TokenChefRegion VALUES(null, '"+id+"', sha1('"+dateString+"'), '"+validiteString+"')");
           stm.executeUpdate("INSERT INTO TokenChefRegion VALUES(null, '"+id+"', sha1('"+dateString+"'), '"+validiteString+"')");
           stm.close();con.getCon().close();
       }
       catch(Exception e){
           e.printStackTrace();
       }
       return this;
   }
   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdChefRegion() {
		return idChefRegion;
	}
	public void setIdChefRegion(int idChefRegion) {
		this.idChefRegion = idChefRegion;
	}
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	public Date getValidite() {
		return validite;
	}
	public void setValidite(Date validite) {
		this.validite = validite;
	}
}
