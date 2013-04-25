package com.danmane.bcparse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BTCByteBuffer {
	// A wrapper for a MappedByteBuffer with specialized functions for 
	// parsing the bitcoin block chain, e.g. getVarInt()
	// Implements relevant functions from the MappedByteBuffer (e.g. getInt)
	// Constructor makes a new read-only MappedBytebuffer from the file name
	MappedByteBuffer contents;
	long fileSize;
	public static final int magicNum = 0xd9b4bef9;
	private BigInteger bigNum = new BigInteger("18446744073709551615");
	
	private static BigInteger makeBigNum(){
		BigInteger big = BigInteger.ONE;
		big = big.or(big.shiftLeft(32));
		big = big.or(big.shiftLeft(16));
		big = big.or(big.shiftLeft(8));
		big = big.or(big.shiftLeft(4));
		big = big.or(big.shiftLeft(2));
		big = big.or(big.shiftLeft(1));
		return big;
	}
	
	public BTCByteBuffer(String fileName){
		RandomAccessFile memMapFile;

		try {
			memMapFile = new RandomAccessFile(fileName, "r");
			fileSize = memMapFile.length();
			contents = memMapFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
			contents.order(ByteOrder.LITTLE_ENDIAN);
			memMapFile.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public BigInteger getVarInt(){
		byte b1 = get();

		int i1 = b1 & 0xFF;
		byte[] dest;
		switch (i1){
		case 0xFD:
			dest = new byte[2];
			get(dest);
			break;
		case 0xFE:
			dest = new byte[4];
			get(dest);
			break;
		case 0xFF:
			dest = new byte[8];
			get(dest);
			break;
		default:
			dest = new byte[1];
			dest[0] = b1;
			break;
		}
		return new BigInteger(1, dest);
	}

	public BigInteger getVarInt(int index){
		position(index);
		return getVarInt();
	}
	
	
	public BigInteger goBig(short i){
		System.out.println("short i: " + i);
		BigInteger b =  new BigInteger(String.valueOf(i));
		System.out.println("big b(i): " + b);
		BigInteger big = new BigInteger("18446744073709551615");
		System.out.println("big num big: " + big);
		BigInteger c = b.and(big);
		System.out.println("bi.and(big) = " + c);
		return c;
	}
	public BigInteger goBig(int i){
		BigInteger b =  new BigInteger(String.valueOf(i));
		BigInteger c = b.and(bigNum);
		return c;
	}
	public BigInteger goBig(long i){
		return new BigInteger(String.valueOf(i)).and(bigNum);
	}
	
//	public BigInteger get32Byte() {
//		
//	}
//	 
	
	public byte get(){
		return contents.get();
	}
	
	public byte get(int index){
		return contents.get(index);
	}
	
	public void get(byte[] dest){
		contents.get(dest);
	}
	
	public int getInt(){
		return contents.getInt();
	}
	
	public int getInt(int index){
		return contents.getInt(index);
	}
	
	public short getShort(){
		return contents.getShort();
	}
	
	public short getShort(int index){
		return contents.getShort(index);
	}
	
	public long getLong(){
		return contents.getLong();
	}
	
	public int capacity(){
		return contents.capacity();
	}
	
	public int position(){
		return contents.position();
	}
	
	public void position(int newPosition){
		contents.position(newPosition);
	}
	
	
}