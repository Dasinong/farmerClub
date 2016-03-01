package com.dasinong.farmerClub.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;

public class QRGenUtil {
	
	public static void gen(String myCodeText, String root, String name){
		QRCode q = new QRCode();
	    
	    // change path as per your laptop/desktop location
	   // String filePath = Env.getEnv().QRCodeDir + "/" + name +".png";
		String filePath = root + "/" + name +".png";
	    int size = 300;
	    String fileType = "png";
	    File myFile = new File(filePath);
	    try {
	        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
	        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText,BarcodeFormat.QR_CODE, size, size, hintMap);
	        int CrunchifyWidth = byteMatrix.getWidth();
	        BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
	                BufferedImage.TYPE_INT_RGB);
	        image.createGraphics();
	
	        Graphics2D graphics = (Graphics2D) image.getGraphics();
	        graphics.setColor(Color.WHITE);
	        graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
	        graphics.setColor(Color.BLACK);
	
	        for (int i = 0; i < CrunchifyWidth; i++) {
	            for (int j = 0; j < CrunchifyWidth; j++) {
	                if (byteMatrix.get(i, j)) {
	                    graphics.fillRect(i, j, 1, 1);
	                }
	            }
	        }
	        ImageIO.write(image, fileType, myFile);
	    } catch (WriterException e1) {
	        e1.printStackTrace();
	    } catch (IOException e2) {
	        e2.printStackTrace();
	    }
	    //System.out.println("\n\nYou have successfully created QR Code.");
	}
	public static void main(String[] args){
		QRGenUtil.gen("http://www.baidu.com", Env.getEnv().CouponQRDir,"QRtest");
	}       

}
