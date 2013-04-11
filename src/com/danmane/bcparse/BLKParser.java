package com.danmane.bcparse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;

public class BLKParser {
	private int blockNum;
	private String blockName, fileName;
	private String blockDirectory = "/Users/danmane/Library/Application Support/Bitcoin/blocks";
	private MappedByteBuffer contents;
	private RandomAccessFile memMapFile;
	private long fileSize;
	private LinkedList<Integer> blockAddresses;
	
	public BLKParser(int blockNum_) throws Exception{
		log("initializing parser");
		blockNum = blockNum_;
		blockName = String.format("%05d", blockNum);
		fileName = blockDirectory + "/testblk.dat";
		log("opening " + fileName);
		memMapFile = new RandomAccessFile(fileName, "r");
		log("got memMapFile");
		fileSize = memMapFile.length();
		log("fileSize is " + fileSize);
		contents = memMapFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
		contents.order(ByteOrder.LITTLE_ENDIAN);
		computeBlockAddresses();
	}
	
	public int nextBlockAddr(int lastAddr){
		assert(contents.getInt(lastAddr) == 0xd9b4bef9);
		int length = contents.getInt(lastAddr + 4);
		return lastAddr + 8 + length;
	}
	
	public int getIntAtAddr(int addr){
		return contents.getInt(addr);
	}
	
	private void computeBlockAddresses(){
		blockAddresses = new LinkedList<Integer>();
		int nextAddr = 0;
		blockAddresses.add(0);
		while (nextAddr < fileSize){
			blockAddresses.add( (Integer) nextAddr);
			nextAddr = nextBlockAddr(nextAddr);
		}
	}
	
	public MappedByteBuffer getContents(){
		return contents;
	}
	
	public int getNumBlocks(){
		return blockAddresses.size();
	}
	
	public Integer[] getBlockAddrs(){
		return blockAddresses.toArray(new Integer[0]);
	}
	
	private static void log(Object aThing){
	    System.out.println(String.valueOf(aThing));
	  }
}
