package main.java;

import java.io.IOException;
import java.util.List;

public class GoogleDrive {

    public static void main(String[] args) throws IOException {
        
        SyncFile gDrive = new SyncFile();
        
        String fileUploadId = gDrive.uploadFile("bill_12_12_2017.csv", "i hope it works well", null, "application/vnd.google-apps.spreadsheet", "D:/work/bill_12_12_2017.csv");

        System.out.println("Successfulllllllll " + fileUploadId);
    }	
	
}
