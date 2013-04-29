package com.danmane.bcparse;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ChainParser {
	BlockChainBuffer bcb;
	int numBlocks;
	HashMap<String, Long>   hash2AddrMap;
	HashMap<String, String> hash2PrevMap;
	
	public ChainParser(String blkDirectory){
		bcb = new BlockChainBuffer(blkDirectory);
		
	}
	
	public void buildHashMaps(){
		hash2AddrMap = new HashMap<String, Long>();
		hash2PrevMap = new HashMap<String, String>();
		
		String blockHash, prevHash;
		long maxAddr = bcb.getMaxAddr();
		long blockAddr = 0;
		Block nextBlock;
		while (blockAddr < maxAddr){
			try {
				nextBlock = new Block(blockAddr, bcb);
				blockHash = nextBlock.getHashString();
				prevHash  = nextBlock.getPrevHashString();
				hash2AddrMap.put(blockHash, blockAddr);
				hash2PrevMap.put(blockHash, block)
				blockAddr = nextBlock.getNextBlockAddr();
			} catch (EndOfBlkException e) {
				blockAddr = bcb.getNextBlkAddr();
			}

		}
		return hm;
	}
	
	public HashMap<Integer, Long> getMainChain(HashMap<String, Long> blockAddrs){
		// Here's how this algorithm works:
		// It makes a set of block hashes called VirginBlocks. This contains the hash of every block which we have not found somewhere on a chain
		// While this set is nonempty, it finds the virgin block vblock with the highest address within the block chain
		// It then searches back through the block chain finding all the previous blocks that this virgin block points to. Every block it reaches is
		// removed from the virgin blocks set. Once it reaches the genesis block, the height of the chain ending with vblock is recorded
		// Then after it has tried every virgin block it is guaranteed to have found the block with the longest chain
		
		
		HashMap<Integer, Long> mainChain = new HashMap<Integer, Long>();
		HashSet<String> virginBlocks = new HashSet<String>(blockAddrs.keySet());
		int highestBlockHeight = 0;
		String highestBlockHash = null;
		
		while (!virginBlocks.isEmpty()){
			long   vBlockAddr = 0;
			String vBlockHash = null;
			
			// Get the virgin block with the latest address
			for (String h : virginBlocks){
				long thisAddr = blockAddrs.get(h);
				if (thisAddr > vBlockAddr){
					vBlockAddr = thisAddr;
					vBlockHash = h;
				}
			}
			
			// Get the height of the chain ending with this virgin block
			int blockHeight = getBlockHeight(vBlockHash, virginBlocks, blockAddrs);
			// getBlockHeight takes every block it visits out of the virginBlocks map, since it 
			if (blockHeight > highestBlockHeight){
				highestBlockHeight = blockHeight;
				highestBlockHash = vBlockHash;
			}
		}
		// We now have the hash and height of the block terminating the longest block chain
		
		
		return mainChain;
	}
	
	private Map.Entry<String, Long>
	
	public int getNumBlocks(){
		long maxAddr = bcb.getMaxAddr();
		long blockAddr = 0;
		Block nextBlock;
		numBlocks = 0;
		while (blockAddr < maxAddr){
			try {
				nextBlock = new Block(blockAddr, bcb);
				numBlocks++;
				blockAddr = nextBlock.getNextBlockAddr();
			} catch (EndOfBlkException e) {
				blockAddr = bcb.getNextBlkAddr();
			}

		}
		return numBlocks;
	}
	
	public static void main(String[] args){
		ChainParser cp = new ChainParser("/Users/danmane/Library/Application Support/Bitcoin/blocks/");
		// "/Users/danmane/Library/Application Support/Bitcoin/blocks/"
		// "/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/test_blks/"
		System.out.println(cp.getNumBlocks());
	}
}
