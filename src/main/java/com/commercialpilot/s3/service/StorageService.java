package com.commercialpilot.s3.service;

/*import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;*/
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

@Service
@Slf4j
public class StorageService {
	/*
	 * @Value("${application.bucket.name}") private static String bucketName;
	 * 
	 * @Autowired private AmazonS3 s3Client;
	 */


    public String uploadFile(MultipartFile file) {

        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
       // s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
       // fileObj.delete();
        return "File uploaded : " + fileName;
    }
    
   

    
    
	/*
	 * public static void encryptedFile(String secretKey, String fileInputPath,
	 * String fileOutPath) throws NoSuchAlgorithmException, NoSuchPaddingException,
	 * InvalidKeyException, IOException, IllegalBlockSizeException,
	 * BadPaddingException { SecretKeySpec key = new
	 * SecretKeySpec(secretKey.getBytes(), "AES"); Cipher cipher =
	 * Cipher.getInstance("AES"); cipher.init(Cipher.ENCRYPT_MODE, key);
	 * 
	 * File fileInput = new File(fileInputPath); FileInputStream inputStream = new
	 * FileInputStream(fileInput); byte[] inputBytes = new byte[(int)
	 * fileInput.length()]; inputStream.read(inputBytes);
	 * 
	 * byte[] outputBytes = cipher.doFinal(inputBytes);
	 * 
	 * File fileEncryptOut = new File(fileOutPath); FileOutputStream outputStream =
	 * new FileOutputStream(fileEncryptOut); outputStream.write(outputBytes);
	 * 
	 * inputStream.close(); outputStream.close();
	 * 
	 * System.out.println("File successfully encrypted!");
	 * System.out.println("New File: " + fileOutPath); }
	 */


	
//	 public byte[] downloadFile(String fileName) {
//		 S3Object s3Object = s3Client.getObject(bucketName, fileName); 
//		 S3ObjectInputStream inputStream =	 s3Object.getObjectContent(); 
//		 try { 
//			 byte[] content =  IOUtils.toByteArray(inputStream);
//		 return content;
//		 } 
//		 catch (IOException e) {
//	 e.printStackTrace(); 
//	 } 
//		 return null;
//		 }
	 


	/*
	 * public String deleteFile(String fileName) { s3Client.deleteObject(bucketName,
	 * fileName); return fileName + " removed ..."; }
	 */


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
        	//logger.error("Error converting multipartFile to file", e);
        	e.printStackTrace();
        }
        return convertedFile;
    }

}
