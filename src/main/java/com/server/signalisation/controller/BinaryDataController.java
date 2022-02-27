package com.server.signalisation.controller;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import com.server.signalisation.models.Photo;

@RestController
public class BinaryDataController {
	
	/*@Autowired
	private GridFsOperations gridFsOperations;
	
	String fileId="";
	
	@PostMapping("/saves")
	public String save(@RequestParam("files") MultipartFile file, @RequestParam("id")String idSignalement) throws Exception {
		DBObject metaData = new BasicDBObject();
		metaData.put("fileSize", file.getSize());
		
		InputStream inputStream = file.getInputStream();
		metaData.put("idSignalement", idSignalement);
		//metaData.put("type", "image");
		
		//gridFsOperations.store(inputStream, metaData);
		//fileId = gridFsOperations.store(inputStream, "logo.png","image/png", metaData).get().toString();
		//System.out.println("File Stored: "+fileId);
		System.out.println(this.gridFsOperations+" gridFsOperation");
		fileId = gridFsOperations.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData).get().toString();
		return "File Stored Successfully "+fileId;
	}
	
	@GetMapping("/lists/{signal}")
	public Photo retrieveImage(@PathVariable String signal) throws Exception {
		GridFSFile gridFSFile = gridFsOperations.findOne( new Query(Criteria.where("filename").is(signal)));
        Photo loadFile = new Photo();
        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename( gridFSFile.getFilename() );
            loadFile.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );
            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );
            loadFile.setFile( IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream()));
        }
        return loadFile;
	}*/

}
