package com.danmane.bcparse;

import java.nio.MappedByteBuffer;
import java.util.LinkedList;

public class BLKParser {
	private int blockNum;
	private String fileName;
	private String blockDirectory = "/Users/danmane/Library/Application Support/Bitcoin/blocks";
	private MappedByteBuffer contents;
	private long fileSize;
	private LinkedList<Integer> blockAddresses;
	
	public BLKParser(int blockNum_) throws Exception{
		log("initializing parser");
		blockNum = blockNum_;
		
		if (blockNum == -1){
			fileName = blockDirectory + "/testblk.dat";
		} else {
			String blockName = String.format("%05d", blockNum);
			fileName = blockDirectory + "/" + blockName + ".dat";
		}

		contents = BitTools.openByteBuffer(fileName);
		fileSize = contents.capacity();
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
