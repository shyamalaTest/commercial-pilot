package com.commercialpilot.s3.controller;


import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.commercialpilot.s3.service.StorageService;
import com.commercialpilot.s3.util.EncryptDecryptUtil;

@Controller
/* @RequestMapping("/file") */
public class StorageController {

    @Autowired
    private StorageService service;
    
    @GetMapping("")
	public String viewHomePage() {
		return "upload";
	}

    @PostMapping("/upload")
    public String uploadFile(Model model, String description,@RequestParam(value = "file") MultipartFile file) {
    	
    	String message = "";
    	String fileName = file.getOriginalFilename();
		String secretKey = "commercial-Pilot";
    	
    	System.out.println("Description: " + description);
		System.out.println("file Obj: " + file);
		
		if (file.isEmpty()) {
            message = "Please select a file to upload";
        }		
		try {
			EncryptDecryptUtil.encryptedFile(secretKey, "D:\\2022\\"+fileName, "D:\\2022\\myFile.enc");
			//StorageService.encryptedFile(secretKey, "D:\\2022\\myText.txt", "D:\\2022\\myText.enc");
			//String uploadedEncryptedFile = service.uploadFile(file);
			//System.out.println("uploadedEncryptedFile .."+uploadedEncryptedFile);
			message = "Your file has been uploaded successfully!";
		} catch (Exception ex) {
			message = "Error uploading file: " + ex.getMessage();
		}		
		model.addAttribute("message", message);		
		return "message";	
      //  return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);

    }

    @PostMapping("/download") 
    public ResponseEntity<ByteArrayResource> downloadFile(Model model, String fileName ) {

    	String secretKey = "commercial-Pilot";
    	String fileNameAndPath = null;
    	System.out.println("fileName from model..."+fileName);
    		//from S3 download
    		//byte[] data =  service.downloadFile(fileName); 
    	fileNameAndPath = "D:\\2022\\"+fileName.concat(".enc");
        File file = new File(fileNameAndPath);
    	System.out.println("fileNameAndPath.."+fileNameAndPath);
        byte[] data;

		try {			
				//data = Files.readAllBytes(file.toPath());
				data = EncryptDecryptUtil.decryptedFile(secretKey, fileNameAndPath);
				System.out.println("data..."+data);
			  	ByteArrayResource resource = new ByteArrayResource(data); 
			  	return ResponseEntity .ok().contentLength(data.length) .header("Content-type",
		    			"application/octet-stream") .header("Content-disposition",
		    					"attachment; filename=\"" + fileName + "\"") .body(resource);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | IOException e) {
				e.printStackTrace();
				return null;
			}
    }

	/*
	 * @DeleteMapping("/delete") public ResponseEntity<String>
	 * deleteFile(String fileName) { return new
	 * ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK); }
	 */
}	 
    
  
