package com.server.signalisation.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseStream {
	private String path = Paths.get("json").toAbsolutePath().toString();
	private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/stockagerojo.appspot.com/o/%s?alt=media";

	private String uploadFile(File file, String fileName) throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
      		  .setCredentials(GoogleCredentials.fromStream(new FileInputStream(path+"/firebase.json")))
      		  .build();
		FirebaseApp.initializeApp(options);
		
		System.out.println(options.toString());
		
        BlobId blobId = BlobId.of("stockagerojo.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        
        System.out.println(options.getStorageBucket());
        
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(path+"/firebase.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(
        		blobInfo
        		, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, fileName);
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    public Object upload(MultipartFile multipartFile) {

        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name. 

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            return "Successfully Uploaded ! " + TEMP_URL;                     // Your customized response
        } catch (Exception e) {
            e.printStackTrace();
            return "500 "+e+" Unsuccessfully Uploaded!";
        }

    }
    
    public Object download(String fileName) throws IOException {
        String destFileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));     // to set random strinh for destination file name
        String destFilePath = "D:\\Google\\" + destFileName;                                    // to set destination file path
        
        ////////////////////////////////   Download  ////////////////////////////////////////////////////////////////////////
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(path+"/firebase.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of("stockagerojo.appspot.com", fileName));
        blob.downloadTo(Paths.get(destFilePath));
        return "Successfully Downloaded!";
    }
}
