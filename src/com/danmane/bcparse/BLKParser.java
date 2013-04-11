package com.danmane.bcparse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BLKParser {
	private int blockNum;
	private String blockName, fileName;
	private String blockDirectory = "/Users/danmane/Library/Application Support/Bitcoin/blocks";
	private byte[] fileContents;
	
	public BLKParser(int blockNum_){
		log("initializing parser");
		blockNum = blockNum_;
		blockName = String.format("%05d", blockNum);
		fileName = blockDirectory + "/blk" + blockName + ".dat";
		fileContents = read(fileName);
		
	}
	
	int nextBlockAddr(int lastAddr){
		assert (getInt32(lastAddr, fileContents) == 0xd9b4bef9);
		return 0;
	}
	
	public byte[] getContents(){
		return fileContents;
	}
	
	int getInt32(int location, byte[] bArray){
		int a, b, c, d;
		a = (int) bArray[location]   >> 24;
		log("byte = " + bArray[location]);
		log("a = " + a);
		
		b = (int) bArray[location+1] >> 16;
		log(b);
		c = (int) bArray[location+2] >> 8;
		log(c);
		d = (int) bArray[location+3];
		log(d);
		log(a^b^c^d);
		return a^b^c^d;
	}
	
	private byte[] read(String aInputFileName){
		  log("Reading in binary file named : " + aInputFileName);
		  File file = new File(aInputFileName);
		  log("File size: " + file.length());
		  byte[] result = new byte[(int)file.length()];
		  log("allocated byte[]");
		  try {
		    InputStream input = null;
		    try {
		      int totalBytesRead = 0;
		      input = new BufferedInputStream(new FileInputStream(file));
		      while(totalBytesRead < result.length){
		        int bytesRemaining = result.length - totalBytesRead;
		        //input.read() returns -1, 0, or more :
		        int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
		        if (bytesRead > 0){
		          totalBytesRead = totalBytesRead + bytesRead;
		        }
		      }
		      /*
		       the above style is a bit tricky: it places bytes into the 'result' array; 
		       'result' is an output parameter;
		       the while loop usually has a single iteration only.
		      */
		      log("Num bytes read: " + totalBytesRead);
		    }
		    finally {
		      log("Closing input stream.");
		      input.close();
		    }
		  }
		  catch (FileNotFoundException ex) {
		    log("File not found.");
		  }
		  catch (IOException ex) {
		    log(ex);
		  }
		  return result;
		}
	
	private static void log(Object aThing){
	    System.out.println(String.valueOf(aThing));
	  }
}
/** Read the given binary file, and return its contents as a byte array.*/ 
