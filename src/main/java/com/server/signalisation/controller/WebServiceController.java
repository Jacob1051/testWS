/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.server.signalisation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import region.*;
import utilisateur.*;

import org.apache.commons.io.IOUtils;

import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.server.signalisation.models.Photo;
import com.server.signalisation.models.StatutRep;
import com.server.signalisation.services.FileStorageImpl;
import com.server.signalisation.services.FileStorageInterface;
import com.server.signalisation.services.FirebaseStream;

import category.*;
import connexion.Connexion;
import data.DetailSignalement;

import signalement.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author orion
 */
@RestController
@CrossOrigin(origins = "*")
public class WebServiceController {
	
	@Autowired
	private GridFsOperations gridFsOperations;
	private FirebaseStream fbs = new FirebaseStream();
	
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
    		
    		new StatutRep().insert(s.getId(), 1);
    		
    		DetailSignalement ds = null;
    		Connection c = new Connexion().getCon();
    		BinaryDataController bdc = new BinaryDataController();
    		for (int i = 0; i < files.length; i++) {
				ds = new DetailSignalement(0, s.getId(), files[i].getOriginalFilename());
				//FileStorageImpl fsi = new FileStorageImpl();
				//fsi.save(files[i]);
				ds.insert(c);
			}
    		c.close();
    		return s;
    	}
    }
    @GetMapping("/getStatutSignalement")
    public String[] getStatut(@RequestParam String id){
        Signalement signalement = new Signalement();
        String[] retour = signalement.getStatutSignalement(id);
        return retour;
    }
    @RequestMapping(value = "/sendNotification", method = RequestMethod.POST)
    public void sendNotification(@RequestBody String message)
    {
       try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "BASIC M2Y5OTIwN2UtYjhmOS00ZDA2LTk4M2ItNWQ3MGIxZWFiNTVk");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                               +   "\"app_id\": \"c293c03d-6325-4e47-b63e-383887c54741\","
                               +   "\"included_segments\": [\"Subscribed Users\"],"
                               +   "\"data\": {\"foo\": \"bar\"},"
                               +   "\"contents\": {\"en\": \""+message+"\"}"
                               + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
               && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
               Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
               jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
               scanner.close();
            }
            else {
               Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
               jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
               scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

         } catch(Throwable t) {
            t.printStackTrace();
         }
    }
    
    @GetMapping("/getSignalementByRegionWithPhoto/{idRegion}")
    public Signalement[] getListeSignalementByRegionWithPhoto(@PathVariable String idRegion){
        Signalement signalement = new Signalement();
        Signalement[] retour = signalement.findSignalementByRegion(idRegion);
        final String uri = "https://ws-photo.herokuapp.com/images/1.png";

        RestTemplate restTemplate = new RestTemplate();
        File result = restTemplate.getForObject(uri, File.class);

        System.out.println(result);
        return retour;
    }
    
    
	public String save(@RequestParam("files") MultipartFile file, String idSignalement) throws Exception {
		DBObject metaData = new BasicDBObject();
		String fileId =  "";
		metaData.put("fileSize", file.getSize());
		
		//metaData.put("images", new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		InputStream inputStream = file.getInputStream();
		metaData.put("idSignalement", idSignalement);
		//metaData.put("type", "image");
		
		fileId = gridFsOperations.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData).get().toString();
		return "File Stored Successfully "+fileId;
	}
	
	@GetMapping("/lists/{signal}")
	public Photo retrieveImage(@PathVariable String signal) throws Exception {
		GridFSFile gridFSFile = gridFsOperations.findOne( new Query(Criteria.where("filename").is(signal)));
        GridFSFindIterable gridIter = gridFsOperations.find( new Query(Criteria.where("filename").is(signal)));
        
		Photo loadFile = new Photo();
        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename( gridFSFile.getFilename() );
            loadFile.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );
            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );
            loadFile.setFile( IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream()));
        }
        return loadFile;
	}
	
	//---------------------------------file---------------------------------------//
	@PostMapping("/profile/pic")
    public Object upload(@RequestParam("file") MultipartFile multipartFile) {
		//return new Object();
        return fbs.upload(multipartFile);
    }

    @PostMapping("/profile/pic/{fileName}")
    public Object download(@PathVariable String fileName) throws IOException {
    	//return new Object();
        return fbs.download(fileName);
    }
  //------------------------------------file------------------------------------//
}


