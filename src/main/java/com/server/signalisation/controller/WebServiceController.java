/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.server.signalisation.controller;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import region.*;
import utilisateur.*;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.server.signalisation.services.FileStorageImpl;
import com.server.signalisation.services.FileStorageInterface;

import category.*;
import connexion.Connexion;
import data.DetailSignalement;
import signalement.*;

/**
 *
 * @author orion
 */
@RestController
@CrossOrigin(origins = "*")
public class WebServiceController {
/*-------------------Region----------------------------------------------*/    
    @GetMapping("/region")
    public ArrayList<Region> region(){
        ArrayList<Region> retour=new ArrayList<>();
        Region un=new Region();
        retour=un.findAll();
        return retour;
    }
    
    @GetMapping("/trouverRegion")
    public Region findRegion(@RequestParam int id)
    {
       Region retour = new Region();
       return retour.find(id);
    }
    
    @RequestMapping(value = "/region/login", method = RequestMethod.POST)
    public ChefRegion checkLogin(@RequestBody ChefRegion user) throws Exception
    {
       Utilisateur utilisateur = new Utilisateur();
       ChefRegion retour = new ChefRegion();
       retour = retour.check(Integer.toString(user.getIdRegion()), user.getMdp());
       return retour;
    }
    
    /*---------------------- USER -------------------------------------------*/
    //********************************login*******************************//
    @RequestMapping(value = "/utilisateur/login", method = RequestMethod.POST)
    public Token login(@RequestBody Utilisateur user) throws Exception
    {
    	Token t = new Token();
        Utilisateur users = new Utilisateur().check(user.getEmail(), user.getMdp());
        if(users==null) {
        	throw new Exception("Utilisateur non reconnue");
        }else {
            t.deleteToken(users.getId());
            t.createToken(users.getId());
            t = t.find(users.getId());
        }
    	return t;
    }
   //********************************login*******************************//
    @GetMapping("/checkLogin")
    public Utilisateur checkUtilisateur(@RequestParam String user, @RequestParam String pass)
    {
       Utilisateur retour = new Utilisateur();
       return retour.check(user, pass);
    }
    @GetMapping("/findLogin")
    public Utilisateur findUtilisateur(@RequestParam int id)
    {
       Utilisateur retour = new Utilisateur();
       return retour.find(id);
    }
    
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public Utilisateur checkLogin(@RequestBody Utilisateur user) throws Exception
    {
       Utilisateur retour = new Utilisateur();
       try{
           if(retour.emailCheck(user.getEmail())==1 && retour.passwordCheck(user.getMdp())==1)
           {
                Token token = new Token();
                retour = retour.check(user.getEmail(), user.getMdp());

                token.deleteToken(retour.getId());
                token.createToken(retour.getId());
           }
       }
       catch(Exception e)
       {
           e.printStackTrace();
           throw e;
       }

       return retour;
    }
    
    //*********************************inscription********************************//
    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public void register(@RequestBody Utilisateur user) throws Exception
    {
       Utilisateur retour = new Utilisateur();
       try{
           if(retour.emailCheck(user.getEmail())==1 && retour.passwordCheck(user.getMdp())==1)
           {
               retour.insert(user.getEmail(), user.getMdp(), user.getNaissance());
           }
       }
       catch(Exception e)
       {
           e.printStackTrace();
           throw e;
       }
       //return "Inserer";
    }
    //*********************************inscription********************************//
    
    /*---------------------- TOKEN -------------------------------------------*/

    @GetMapping("/findToken")
    public Token findToken(@RequestParam int id)
    {
       Token retour = new Token();
       return retour.find(id);
    }
    @RequestMapping(value = "/users/checkToken", method = RequestMethod.POST)
    public Token checkToken(@RequestBody Token token) 
    {
       Token retour = new Token();
       retour = retour.findFromValue(token.getValeur());
       return retour;
    }
    
    @RequestMapping(value = "/token/getUser", method = RequestMethod.POST)
    public Token getUserFromToken(@RequestParam("token") String token) 
    {
       Token retour = new Token(token);
       retour.getUser();
       return retour;
    }
    
    @RequestMapping(value = "/users/deleteToken", method = RequestMethod.POST)
    public void deleteToken(@RequestBody Token token) 
    {
       Token retour = new Token();
       retour.deleteToken(token.getValeur());
    }
    /*---------------------- SIGNALEMENT -------------------------------------------*/
    @GetMapping("/getCategory")
    public Category[] getCategory(){
        Category category = new Category();
        Category[] retour = category.findAll();
        return retour;
    }

    @RequestMapping(value = "/signalement/report", method = RequestMethod.POST)
    public void createSignalement(@RequestBody Signalement signalement)
    {
       Signalement retour = new Signalement();
       retour.insertSignalement(signalement.getIdUtilisateur(), signalement.getIdCategory(), signalement.getDescription(), signalement.getLatitude(), signalement.getLongitude());
    }

    @GetMapping("/getHistoryOfReport")
    public Signalement[] getHistory(@RequestParam String id){
        Signalement signalement = new Signalement();
        Signalement[] retour = signalement.getHistoryOfReport(id);
        return retour;
    }
    @GetMapping("/getListeSignalement")
    public Signalement[] getListeSignalement(){
        Signalement signalement = new Signalement();
        Signalement[] retour = signalement.findAll();
        return retour;
    }
    @GetMapping("/getListeSignalementByRegion/{idRegion}")
    public Signalement[] getListeSignalementByRegion(@PathVariable String idRegion){
        Signalement signalement = new Signalement();
        Signalement[] retour = signalement.findSignalementByRegion(idRegion);
        return retour;
    }
    
    @RequestMapping(value = "/signalement/insert")
    public Signalement insertSignalement(@RequestParam("token") String token, 
    							  @RequestParam("idCategory") String category,
    							  @RequestParam("description") String description,
    							  @RequestParam("date") String dateSignalement,
    							  @RequestParam("lat") String lat,
    							  @RequestParam("long") String longitude,
    							  @RequestParam("files") MultipartFile[] files) throws Exception {
    	if(token.length()==0) {
    		throw new Exception("token needed");
    	} else {
    		Token t = this.getUserFromToken(token);
    		Signalement s = new Signalement(0, t.getIdUser(), Integer.parseInt(category), description, new java.util.Date(), Float.parseFloat(lat), Float.parseFloat(longitude));
    		s.insertSignalement(t.getIdUser(), Integer.parseInt(category), description, Float.parseFloat(lat), Float.parseFloat(longitude));
    		s.find();
    		
    		DetailSignalement ds = null;
    		Connection c = new Connexion().getCon();
    		for (int i = 0; i < files.length; i++) {
				ds = new DetailSignalement(0, s.getId(), files[i].getOriginalFilename());
				FileStorageImpl fsi = new FileStorageImpl();
				fsi.save(files[i]);
				ds.insert(c);
			}
    		//c.commit();
    		c.close();
    		return s;
    	}
    }
}


