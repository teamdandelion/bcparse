package com.danmane.bcparse;

public class Block {
	long memAddr, byteSize;
	int blockNum;
	long totalOut, totalFees;
	int numTransactions; 
	BlockChainBuffer theBuffer;
	Transaction[] transactions;
	// Spec allows for 2^64 transactions but I don't think it likely we'll see that..
	byte[] blockHash;
	
	public Block(long memAddr, int blockNum, BlockChainBuffer theBuffer){
		this.memAddr   = memAddr;
		this.blockNum  = blockNum;
		this.theBuffer = theBuffer;
		
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
