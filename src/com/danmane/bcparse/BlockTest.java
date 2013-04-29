package com.danmane.bcparse;

import static org.junit.Assert.*;
import org.apache.commons.codec.binary.Hex;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BlockTest {
	BlockChainBuffer bcb;
	
	@Before
	public void setUp() throws Exception {
	bcb = new BlockChainBuffer("/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/test_blks/");
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNextAddr() {
		try{
			Block b0 = new Block(0, bcb);
			assertEquals(293, b0.getNextBlockAddr());
			
			Block b1 = new Block(293, bcb);
		} catch (EndOfBlkException e) {
			fail("This is unexpected");
		}
	}
	
	@Test
	public void testBlockHash(){
		try{
			Block b0 = new Block(0, bcb);
			Block b1 = new Block(293, bcb);
			byte[] b0hash, b1prev;
			b0hash = b0.getHash();
			b1prev = b1.getPrevHash();
			//System.out.println("b0Hash: " + Hex.encodeHexString(b0hash));
			//System.out.println("b1Prev: " + Hex.encodeHexString(b1prev));
			assertArrayEquals(b1prev, b0hash);
		} catch (EndOfBlkException e){
			fail("Shouldn't have happened");
		}
	}

}
