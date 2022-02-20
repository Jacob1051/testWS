/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author orion
 */
public class Connexion {
    Connection con;
    
    public  Connexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Signalisation","root","root");
            //con=DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11460930","sql11460930","eBxZMJjkAs");
            //con=DriverManager.getConnection("jdbc:mysql://db4free.net:3306/signalisation","zeus_db4free","mdpprom13");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Connection getCon() {
        return con;
    }
    void setCon(Connection con) {
        this.con = con;
    }
}
