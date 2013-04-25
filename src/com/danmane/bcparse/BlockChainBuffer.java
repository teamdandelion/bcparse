package com.danmane.bcparse;

import java.io.File;

// This class presents an interface to the entire block chain, with uniform addressing
// Basically, its purpose is to abstract away the fact that the blocks are saved in multiple data files, and pretend that they are all in one huge file

public class BlockChainBuffer {
	long[] startingMemAddrs; // the starting memory address for each blk
	long maxAddr;
	int numBlks; // the number of blks
	static String blkDirectory = "/Users/danmane/Library/Application Support/Bitcoin/blocks/";
	
	public BlockChainBuffer(){
		// Find the files, and file lengths, to come up with addressing scheme
		// Make an array of mmaped files? Or maybe cache them as needed? Not sure
		calculateNumBlks();
		calculateStartingMemAddrs(numBlks);
	}
	
	public AddrPair translateAddress(long rawAddr){
		int blkNum, localAddr;
		
		if (rawAddr < 0 || rawAddr > maxAddr){
			throw new IndexOutOfBoundsException();
		}
		
		blkNum = 0;
		while (startingMemAddrs[blkNum + 1] < rawAddr){
			blkNum++;
		}
		
		localAddr = (int) (rawAddr - startingMemAddrs[blkNum]);
		return new AddrPair(blkNum, localAddr);
		
	}
	
	private void calculateNumBlks(){
		numBlks = 0;
		File nextFile;
		// This code would misbehave if there were only 1 blk
		do {
			numBlks++;
			nextFile = getBlk(numBlks);
		} while (nextFile.exists());
	}
	
	private void calculateStartingMemAddrs(int numBlks){
		long runningTotal = 0;
		
		startingMemAddrs = new long[numBlks+1];
		for (int i=0; i<numBlks; i++){
			startingMemAddrs[i] = runningTotal;
			runningTotal += getBlk(i).length();
		}
		startingMemAddrs[numBlks] = runningTotal;
	}
	
	public int getNumBlks(){
		return numBlks;
	}
	
	private File getBlk(int i){
		String fileName;
		fileName = "blk" + String.format("%05d", i) + ".dat";
		return new File(blkDirectory, fileName);
	}
	
	public class AddrPair {
		int bNum;
		int bAddr;
		
		AddrPair(int bNum, int bAddr){
			this.bNum  = bNum;
			this.bAddr = bAddr;
		}
		
		int getbNum(){
			return bNum;
		}
		
		int getbAddr(){
			return bAddr;
		}
	}
	

}
