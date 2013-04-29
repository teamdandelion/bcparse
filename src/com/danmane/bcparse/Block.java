package com.danmane.bcparse;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;


public class Block {
	long memAddr, byteSize;
	int numTransactions, blockFormatVersion;
	long totalOut, totalFees, blockTimestamp, blockLength;
	byte[] blockHash, prevBlockHash, merkleRoot;
	BlockChainBuffer theBuffer;
	Transaction[] transactions;
	static BigInteger magicNum = new BigInteger("3652501241");
	
	public Block(long memAddr, BlockChainBuffer theBuffer) throws EndOfBlkException{
		this.memAddr   = memAddr;
		this.theBuffer = theBuffer;
		
		BigInteger shouldBeMagic = theBuffer.get4Byte(memAddr);
		if (!shouldBeMagic.equals(magicNum)){
			if (shouldBeMagic.equals(BigInteger.ZERO)){
				// The ends of files are padded with 0s so that the BLKs are all the same length
				// If we throw a EndOfBlkException, then the parser will move to the start of the next file
				throw new EndOfBlkException();
			} else {
				System.out.println("Block missing magic at addr " + memAddr);
				theBuffer.printPosition();
				throw new RuntimeException();				
			}

			// All blocks are preceded by the 4 byte magic number
		}
		blockLength = theBuffer.get4Byte().longValue();
		//System.out.println(blockLength);
		// blockFormatVersion = theBuffer.get4Byte().intValue();
		// Makes assumptions about length and format value that don't match spec,
		// but seem reasonable
		// prevBlockHash = theBuffer.get32Byte();
		// merkleRoot = theBuffer.get32Byte();
		// blockTimestamp = theBuffer.get4Byte().longValue();
//		target = theBuffer.get4Byte();
//		nonce = theBuffer.get4Byte();
		// numTransactions = theBuffer.getVarInt().intValue();
		// Note spec allows for 2^64 transactions
		
		// TODO: Parse transactions
		
	}
	
	public long getNextBlockAddr(){
		return memAddr + 8 + blockLength;
	}
	
	public void writeToDB(BitcoinDB theDB){
		
	}
	
	public Transaction[] getTransactions(){
		return transactions;
	}
	
	public int getNumTransaction(){
		return numTransactions;
	}
	
	public Transaction getTransaction(int position){
		return transactions[position];
	}
	
	public byte[] getHash(){
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] header = new byte[80];
			theBuffer.position(memAddr+8);
			theBuffer.get(header);
			ArrayUtils.reverse(header);
			//System.out.println("header: " + Hex.encodeHexString(header));
			blockHash = digest.digest(header);
			blockHash = digest.digest(blockHash);
			ArrayUtils.reverse(blockHash);
			return blockHash;
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public String getHashString(){
		byte[] hash = getHash();
		return Hex.encodeHexString(hash);
	}
	
	public byte[] getPrevHash(){
		theBuffer.position(memAddr + 12);
		// 4(Magic) + 4(Length) + 4(version) -> 32(PrevHash)
		return theBuffer.get32Byte();
		
	}
	
	public String getPrevHashString(){
		return Hex.encodeHexString(getPrevHash());
	}
	
	
}
