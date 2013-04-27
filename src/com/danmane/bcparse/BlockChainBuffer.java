package com.danmane.bcparse;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

// This class presents an interface to the entire block chain, with uniform addressing
// Basically, its purpose is to abstract away the fact that the blocks are saved in multiple data files, and pretend that they are all in one huge file

public class BlockChainBuffer {
	long[] startingMemAddrs; // the starting memory address for each blk
	long maxAddr;
	int numBlks; // the number of blks
	String blkDirectory;
	BTCByteBuffer openBuffer;
	int openBufferNum = -1;
	int openBufferCapacity;
	
	// Constructors

	public BlockChainBuffer(String blkDirectory){
		this.blkDirectory = blkDirectory;
		calculateNumBlks();
		calculateStartingMemAddrs(numBlks);
		getBuffer(0);
	}
	
	public BlockChainBuffer(){
		this("/Users/danmane/Library/Application Support/Bitcoin/blocks/");
	}

	// Buffer Getters
	
	public BigInteger getVarInt(){
		BigInteger rval = openBuffer.getVarInt();
		checkBufferBounds();
		return rval;
	}

	public BigInteger getVarInt(long index){
		position(index);
		return getVarInt();
	}
	
	public byte[] get32Byte() {
		byte[] rval = openBuffer.get32Byte();
		checkBufferBounds();
		return rval;
	}
	 
	public byte[] get32Byte(long index){
		position(index);
		return get32Byte();
	}
	
	public BigInteger get4Byte(){
		BigInteger rval = openBuffer.get4Byte();
		checkBufferBounds();
		return rval;
	}
	
	public BigInteger get4Byte(long index){
		position(index);
		return get4Byte();
	}
	
	public byte get(){
		byte rval;
		rval = openBuffer.get();
		checkBufferBounds();
		return rval;
	}
	
	public byte get(long index){
		position(index);
		return get();
	}
	
	public void get(byte[] dest){
		openBuffer.get(dest);
		checkBufferBounds();
	}
	
	public int getInt(){
		int rval = openBuffer.getInt();
		checkBufferBounds();
		return rval;
	}
	
	public int getInt(long index){
		position(index);
		return getInt();
	}
	
	public short getShort(){
		short rval = openBuffer.getShort();
		checkBufferBounds();
		return rval;
	}
	
	public short getShort(long index){
		position(index);
		return getShort();
	}
	
	public long getLong(){
		long rval = openBuffer.getLong();
		checkBufferBounds();
		return rval;
	}
	
	public long getLong(long index){
		position(index);
		return getLong();
	}
	
	public void seek(long offset){
		// Move the position forward by offset
		long currentPosition = position();
		position(currentPosition + offset);
	}


	// Mechanics
	
	public long position(){
		long localAddr = (long) openBuffer.position();
		return localAddr + startingMemAddrs[openBufferNum];
	}
	
	public void position(long newPosition){
		AddrPair newloc = translateAddr(newPosition);
		int bnum = newloc.getbNum();
		int localAddr = newloc.getmemAddr();
		getBuffer(bnum);
		openBuffer.position(localAddr);
	}
	
	void getBuffer(int bufferNum){
		if (openBufferNum == bufferNum){
			return; // nothing needs be done
		} else {
			openBufferNum = bufferNum;
			String fileName = "blk" + String.format("%05d", bufferNum) + ".dat";
			try {
				openBuffer = new BTCByteBuffer(blkDirectory + fileName);
				openBufferCapacity = openBuffer.capacity();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
	
	public AddrPair translateAddr(long rawAddr){
		int blkNum, localAddr;
		
		if (rawAddr < 0 || rawAddr > maxAddr){
			throw new IndexOutOfBoundsException();
		}
		
		if (startingMemAddrs[openBufferNum] < rawAddr && startingMemAddrs[openBufferNum+1] > rawAddr){
			blkNum = openBufferNum;
			// in most cases we are staying in the current buffer
		} else {
			blkNum = 0;
			while (startingMemAddrs[blkNum + 1] <= rawAddr){
				blkNum++;
			}
			// this algorithm could be made more efficient using a binary search pattern
		}
		
		localAddr = (int) (rawAddr - startingMemAddrs[blkNum]);
		return new AddrPair(blkNum, localAddr);
		
	}
	
	private void calculateNumBlks(){
		numBlks = 0;
		File nextFile = getBlk(0);
		while (nextFile.exists()){
			numBlks++;
			nextFile = getBlk(numBlks);
		}
	}
	
	void checkBufferBounds(){
		if (openBuffer.position() == openBufferCapacity && openBufferNum < (numBlks-1)){
			getBuffer(openBufferNum + 1);
		}

	}

	private void calculateStartingMemAddrs(int numBlks){
		long runningTotal = 0;
		
		startingMemAddrs = new long[numBlks+1];
		for (int i=0; i<numBlks; i++){
			startingMemAddrs[i] = runningTotal;
			runningTotal += getBlk(i).length();
		}
		startingMemAddrs[numBlks] = runningTotal;
		maxAddr = runningTotal;
	}
	
	public int getNumBlks(){
		return numBlks;
	}
	
	public long getMaxAddr(){
		return maxAddr;
	}
	
	private File getBlk(int i){
		String fileName;
		fileName = "blk" + String.format("%05d", i) + ".dat";
		return new File(blkDirectory, fileName);
	}
	
	public class AddrPair {
		int bNum;
		int memAddr;
		
		AddrPair(int bNum, int memAddr){
			this.bNum  = bNum;
			this.memAddr = memAddr;
		}
		
		int getbNum(){
			return bNum;
		}
		
		int getmemAddr(){
			return memAddr;
		}
	}
}