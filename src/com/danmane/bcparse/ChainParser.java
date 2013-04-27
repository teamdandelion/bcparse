package com.danmane.bcparse;

public class ChainParser {
	BlockChainBuffer bcb;
	int numBlocks;
	
	public ChainParser(String blkDirectory){
		bcb = new BlockChainBuffer(blkDirectory);
		
	}
	
	public int getNumBlocks(){
		long maxAddr = bcb.getMaxAddr();
		long blockAddr = 0;
		Block nextBlock;
		numBlocks = 0;
		while (blockAddr < maxAddr){
			nextBlock = new Block(blockAddr, numBlocks, bcb);
			numBlocks++;
			blockAddr = nextBlock.getNextBlockAddr();
		}
		return numBlocks;
	}
	
	public static void main(String[] args){
		ChainParser cp = new ChainParser("/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/test_blks/");
		// "/Users/danmane/Library/Application Support/Bitcoin/blocks/"
		System.out.println(cp.getNumBlocks());
	}
}
