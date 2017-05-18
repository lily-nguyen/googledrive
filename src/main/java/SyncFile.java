package main.java;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.http.FileContent;

import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class SyncFile {
	
    private static final String APPLICATION_NAME = "Drive API Java Quickstart";

    /** Directory to store user s for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }	
	
	private static Drive drive = null;
	
	
	public SyncFile () {
		if (drive == null) {
			try {
				initialDriveService();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}	

	
    private static void initialDriveService() throws IOException {
        Credential authorization = Authorization.authorize();
        
        //TODO: handle credentail creating error
        
        drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, authorization)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    
    private File getMetadata (String name, String description, String mimeType, String parentId) {
    	
    	File metadata = new com.google.api.services.drive.model.File();

	    metadata.setDescription(description);
	    metadata.setMimeType(mimeType);
	    metadata.setName(name);
   	
	    //TODO: upload to folder:  https://developers.google.com/drive/v3/web/folder
	    
    	return metadata;
    }
    
    
    private FileContent getContent (String filePath, String mimeType) {

	    java.io.File fileContent = new java.io.File(filePath);

	    return new FileContent(mimeType, fileContent);
    }
    
    
    public String uploadFile(String name, String description, String parentId, String mimeType, String filePath) {

    	// File's metadata.
	    File metadataFile = getMetadata(name, description, mimeType, parentId);

	    // File's content.
	    FileContent contentFile = getContent(filePath, mimeType);
	    
	    try {
	      File file = drive.files().create(metadataFile, contentFile).execute();
	      
	      return file.getId();
	      
	    } catch (IOException e) {
	      System.out.println("An error occurred: " + e);
	      return null;
	    }
    }
	
}
