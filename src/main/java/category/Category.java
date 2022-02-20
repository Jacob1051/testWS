/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package category;

/**
 *
 * @author pc
 */
import connexion.*;
import java.sql.*;
import java.util.*;
import java.util.Vector;

public class Category {
     int id;
     String nom;
     String image;

    public Category(){}

    public Category(int idCat, String name, String img){
        setId(idCat);
        setNom(name);
        setImage(img);
    }

    public int getId()
    {
        return this.id;
    }
    public void setId(int nouveau)
    {
       this.id = nouveau;
    }

    public String getNom()
    {
        return this.nom;
    }
    public void setNom(String nouveau)
    {
       this.nom = nouveau;
    }

    public String getImage()
    {
        return this.image;
    }
    public void setImage(String nouveau)
    {
       this.image = nouveau;
    }

    public Category[] findAll()
    {
        Category[] retour=new Category[0];
        Connexion con=new Connexion();
        Statement stm=null;
        ResultSet rs=null;

        Vector v = new Vector();

        try{
            stm=con.getCon().createStatement();
            rs=stm.executeQuery("SELECT * FROM Category");
            while(rs.next())
            {
                v.add(new Category(rs.getInt(1),rs.getString(2), rs.getString(3)));
            }

            Object[] array = v.toArray();
            retour = Arrays.copyOf(array, array.length, Category[].class);

            con.getCon().close();stm.close();rs.close();

        }catch(Exception ex){
           ex.printStackTrace();    
        }     
        return retour;
    }
    
}
