package com.danmane.bcparse;

import java.math.BigInteger;

public class Block {
	long memAddr, byteSize;
	int blockNum, numTransactions, blockFormatVersion;
	long totalOut, totalFees, blockTimestamp, blockLength;
	byte[] blockHash, merkleRoot;
	BlockChainBuffer theBuffer;
	Transaction[] transactions;
	static BigInteger magicNum = new BigInteger("4190024921");
	
	public Block(long memAddr, int blockNum, BlockChainBuffer theBuffer){
		this.memAddr   = memAddr;
		this.blockNum  = blockNum;
		this.theBuffer = theBuffer;
		
		BigInteger shouldBeMagic = theBuffer.get4Byte(memAddr);
		if (!shouldBeMagic.equals(magicNum)){
			System.out.println("Block " + blockNum + " missing magic at addr " + memAddr);
			throw new RuntimeException();
			// All blocks are preceded by the 4 byte magic number
		}
		blockLength = theBuffer.get4Byte().longValue();
		blockFormatVersion = theBuffer.get4Byte().intValue();
		// Makes assumptions about length and format value that don't match spec,
		// but seem reasonable
		blockHash = theBuffer.get32Byte();
		merkleRoot = theBuffer.get32Byte();
		blockTimestamp = theBuffer.get4Byte().longValue();
//		target = theBuffer.get4Byte();
//		nonce = theBuffer.get4Byte();
		numTransactions = theBuffer.getVarInt().intValue();
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
		return blockHash;
	}
	
	
}
