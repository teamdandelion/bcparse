package com.danmane.bcparse;

import java.util.HashMap;
import java.util.HashSet;

public class ChainParser {
	BlockChainBuffer bcb;
	int numBlocks;
	HashMap<String, Long>   hash2AddrMap;
	HashMap<String, String> hash2PrevMap;
	int chainHeight;
	String highestBlockHash;
	static String genesisPrevHash = "0000000000000000000000000000000000000000000000000000000000000000";
	
	public ChainParser(String blkDirectory){
		bcb = new BlockChainBuffer(blkDirectory);
		buildHashMaps();
		getHighestBlock();
		
	}
	
	public void buildHashMaps(){
		System.out.println("Building hash maps");
		hash2AddrMap = new HashMap<String, Long>();
		hash2PrevMap = new HashMap<String, String>();
		
		String blockHash, prevHash;
		long maxAddr = bcb.getMaxAddr();
		long blockAddr = 0;
		Block nextBlock;
		while (blockAddr < maxAddr){
			try {
				nextBlock = new Block(blockAddr, bcb);
				// Can throw EndOfBlkException; handled below
				
				blockHash = nextBlock.getHashString();
				prevHash  = nextBlock.getPrevHashString();
				hash2AddrMap.put(blockHash, blockAddr);
				hash2PrevMap.put(blockHash, prevHash);
				numBlocks++;
				blockAddr = nextBlock.getNextBlockAddr();
			} catch (EndOfBlkException e) {
				blockAddr = bcb.getNextBlkAddr();
			}
		}
	}
	
	public void getHighestBlock(){
		// Here's how this algorithm works:
		// It makes a set of block hashes called VirginBlocks. This contains the hash of every block which we have not found somewhere on a chain
		// While this set is nonempty, it finds the virgin block vblock with the highest address within the block chain
		// It then searches back through the block chain finding all the previous blocks that this virgin block points to. Every block it reaches is
		// removed from the virgin blocks set. Once it reaches the genesis block, the height of the chain ending with vblock is recorded
		// Then after it has tried every virgin block it is guaranteed to have found the block with the longest chain
		System.out.println("getting highest block");
		HashSet<String> virginBlocks = new HashSet<String>(hash2AddrMap.keySet());
		
		chainHeight = 0;
		highestBlockHash = null;
		
		while (!virginBlocks.isEmpty()){
			long   vBlockAddr = -1;
			String vBlockHash = null;
			
			// Get the virgin block with the latest address
			for (String h : virginBlocks){
				long thisAddr = hash2AddrMap.get(h);
				if (thisAddr > vBlockAddr){
					vBlockAddr = thisAddr;
					vBlockHash = h;
				}
			}
			
			System.out.println("vBlock: hash " + vBlockHash + " addr: " + vBlockAddr);
			
			// Get the height of the chain ending with this virgin block
			int blockHeight = getBlockHeight(vBlockHash, virginBlocks);
			// getBlockHeight takes every block it visits out of the virginBlocks map, since it 
			if (blockHeight > chainHeight){
				chainHeight = blockHeight;
				highestBlockHash = vBlockHash;
			}
		}
		// We now have the hash and height of the block terminating the longest block chain
	}
	
	public int getBlockHeight(String blockHash, HashSet<String> virginBlocks){
		int height = 0;
		String prevHash = hash2PrevMap.get(blockHash);
		
		while (!prevHash.equals(genesisPrevHash)){
			virginBlocks.remove(blockHash);
			blockHash = prevHash;
			prevHash = hash2PrevMap.get(blockHash);
			height++;

			if (prevHash == (null)){
				throw new RuntimeException("Block " + blockHash + " had no prev hash");
			}
		}
		virginBlocks.remove(blockHash); // gets rid of the genesis block

		System.out.println("Returning height " + height);
		return height;
	}
	
	public void buildMainChain(){
		HashMap<Integer, Long> mainChain = new HashMap<Integer, Long>();

	}

	public static void main(String[] args){
		ChainParser cp = new ChainParser("/Users/danmane/Library/Application Support/Bitcoin/blocks/");
		//ChainParser cp = new ChainParser("/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/test_blks/");
		// "/Users/danmane/Library/Application Support/Bitcoin/blocks/"
		// "/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/test_blks/"
		System.out.println(cp.chainHeight);
	}
}
