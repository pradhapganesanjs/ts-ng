package com.pg.springb.front.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.zip.ZipException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.misc.CharacterDecoder;
import sun.misc.CharacterEncoder;


public class FileZipper
{
	 
	 private static final String EXTENSION = "zip";
	   

    public FileZipper()
    {
        
    }
    
    public static void pack(String secretZipKey, String folderPath, String fileName)
    {
        
    	String fullFileName = folderPath+ fileName;
        try {

        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        zipParameters.setPassword(secretZipKey);
        
        String baseFileName = FilenameUtils.getBaseName(fullFileName);
        String destinationZipFilePath = baseFileName + "." + EXTENSION;
        destinationZipFilePath =  folderPath+ destinationZipFilePath;
        
        ZipFile zipFile = new ZipFile(destinationZipFilePath);
        zipFile.addFile(new File(fullFileName), zipParameters);

    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();

    }
    }

    private byte[] zipFileProtected(String key, String folderPath, String fileName) throws IOException {

        
        File inputFile = new File(fileName);
            Path path = Paths.get(fileName);
            byte[] fileBytes =  Files.readAllBytes(path);

        
        ByteArrayInputStream inputByteStream = null;
        ByteArrayOutputStream outputByteStream = null;
        net.lingala.zip4j.io.ZipOutputStream outputZipStream = null;

        try {
            //write the zip bytes to a byte array
            outputByteStream = new ByteArrayOutputStream();
            outputZipStream = new net.lingala.zip4j.io.ZipOutputStream(outputByteStream);

            //input byte stream to read the input bytes
            inputByteStream = new ByteArrayInputStream(fileBytes);

            //init the zip parameters
            ZipParameters zipParams = new ZipParameters();
            zipParams.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            zipParams.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipParams.setEncryptFiles(true);
            zipParams.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            zipParams.setPassword(key);
            zipParams.setSourceExternalStream(true);
            zipParams.setFileNameInZip(fileName);
            
            //create zip entry
            outputZipStream.putNextEntry(new File(fileName), zipParams);
            IOUtils.copy(inputByteStream, outputZipStream);
            outputZipStream.closeEntry();

            //finish up
            outputZipStream.finish();

            IOUtils.closeQuietly(inputByteStream);
            IOUtils.closeQuietly(outputByteStream);
            IOUtils.closeQuietly(outputZipStream);

            return outputByteStream.toByteArray();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputByteStream);
            IOUtils.closeQuietly(outputByteStream);
            IOUtils.closeQuietly(outputZipStream);
        }
        return null;
    }  
   
}

