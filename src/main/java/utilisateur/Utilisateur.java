/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilisateur;

/**
 *
 * @author pc
 */
import connexion.*;
import java.sql.*;
import java.util.Date;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilisateur {
    int id;
    String email;
    String mdp;
    Date naissance;

    public Utilisateur()
    {}
    public Utilisateur(int ID, String EMAIL, String MDP, Date NAISSANCE)
    {
        setId(ID);
        setEmail(EMAIL);
        setMdp(MDP);
        setNaissance(NAISSANCE);
    }
    public int getId()
    {
      return this.id;
    }
    public void setId(int ID)
    {
        this.id=ID;
    }
    public String getEmail()
    {
      return this.email;
    }
    public void setEmail(String ID)
    {
        this.email=ID;
    }
    public String getMdp()
    {
      return this.mdp;
    }
    public void setMdp(String ID)
    {
        this.mdp=ID;
    }
    public Date getNaissance()
    {
      return this.naissance;
    }
    public void setNaissance(Date ID)
    {
        this.naissance=ID;
    }
    public Utilisateur check(String user, String password)
    {
        Utilisateur retour = new Utilisateur();
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            System.out.println("SELECT * FROM Utilisateur WHERE email='"+user+"' AND mdp=sha1('"+password+"')");
            rs=stm.executeQuery("SELECT * FROM Utilisateur WHERE email='"+user+"' AND mdp=sha1('"+password+"')");
            while(rs.next()){

            String[] espace =  rs.getString(4).split(" ");
            String[] date = espace[0].split("-");

                Date temp = new Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])+1);	
                
                retour = new Utilisateur(rs.getInt(1),rs.getString(2), rs.getString(3), temp);

                con.getCon().close();stm.close();rs.close();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return retour;
    }
    public Utilisateur find(int id)
    {
        Utilisateur retour = new Utilisateur();
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            System.out.println("SELECT * FROM Utilisateur WHERE id="+id);
            rs=stm.executeQuery("SELECT * FROM Utilisateur WHERE id="+id);
            while(rs.next()){

                String[] espace =  rs.getString(4).split(" ");
                String[] date = espace[0].split("-");

                Date temp = new Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])+1);	
                
                retour = new Utilisateur(rs.getInt(1),rs.getString(2), rs.getString(3), temp);
            }
            con.getCon().close();stm.close();rs.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return retour;
    }
    public void insert(String mail, String pass, Date dateofbirth)
    {
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        String dateString = Integer.toString(dateofbirth.getYear()+1900)+"-"+Integer.toString(dateofbirth.getMonth()+1)+"-"+Integer.toString(dateofbirth.getDate());
        try{
            stm=con.getCon().createStatement();
            System.out.println("INSERT INTO Utilisateur VALUES(NULL, '"+mail+"', sha1('"+pass+"'), '"+dateString+"')");
            stm.executeUpdate("INSERT INTO Utilisateur VALUES(NULL, '"+mail+"', sha1('"+pass+"'), '"+dateString+"')");
            con.getCon().close();stm.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public int emailCheck(String email) throws Exception
    {
    	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
                  
    	Pattern pattern = Pattern.compile(emailRegex);
    	Matcher mat = pattern.matcher(email);
        int retour = 0;

        if(mat.matches()){
            retour = 1;
            //System.out.println("Valid email address");
        }else{
            retour = 0;
            //System.out.println("Not a valid email address");
            throw(new Exception("E-Mail Invalide(exemple : moi@email.com)"));
        }
        return retour;
    }
    public int passwordCheck(String password) throws Exception
    {
        int retour = 0;
        Pattern pattern = Pattern.compile(".*[çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ].*");
        Matcher mat = pattern.matcher(password);

        if(mat.matches()==false && password.length()>=8){
            retour = 1;
            //System.out.println("Valid password");
        }else{
            retour = 0;
            //System.out.println("Not a valid password");
            throw(new Exception("password must contains 8 characters minimum and no accents"));
        }
        return retour;
    }
    public Utilisateur findUserFromToken(String token)
    {
        Utilisateur retour = new Utilisateur();
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;
        try{
            stm=con.getCon().createStatement();
            System.out.println("SELECT * FROM Utilisateur u JOIN Token t ON t.idUser=u.id WHERE t.valeur='"+token+"'");
            rs=stm.executeQuery("SELECT * FROM Utilisateur u JOIN Token t ON t.idUser=u.id WHERE t.valeur='"+token+"'");
            while(rs.next()){

                String[] espace =  rs.getString(4).split(" ");
                String[] date = espace[0].split("-");

                Date temp = new Date(Integer.parseInt(date[0])-1900, Integer.parseInt(date[1])-1, Integer.parseInt(date[2])+1);	
                
                retour = new Utilisateur(rs.getInt(1),rs.getString(2), rs.getString(3), temp);
            }
            con.getCon().close();stm.close();rs.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return retour;
    }
}
