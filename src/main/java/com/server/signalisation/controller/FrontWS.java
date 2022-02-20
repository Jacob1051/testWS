package com.server.signalisation.controller;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import region.*;
import utilisateur.*;

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
public class FrontWS {
/*-------------------Region----------------------------------------------*/    
    //********************************login*******************************//
    @RequestMapping(value = "/front/login", method = RequestMethod.POST)
    public TokenChefRegion loginFront(@RequestBody ChefRegion user) throws Exception
    {
    	TokenChefRegion t = new TokenChefRegion().checkLogin(""+user.getIdRegion(), user.getMdp());
        if(t==null) {
        	throw new Exception("Utilisateur non reconnue");
        }
    	return t;
    }
  //********************************login*******************************//
    @GetMapping("/front/signalement/{idRegion}")
    public Signalement[] getListeSignalementByRegion(@PathVariable String idRegion){
        Signalement signalement = new Signalement();
        Signalement[] retour = signalement.findSignalementByRegion(idRegion);
        return retour;
    }
  
}


