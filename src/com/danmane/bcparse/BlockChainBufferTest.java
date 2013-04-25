package com.danmane.bcparse;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.danmane.bcparse.BlockChainBuffer.AddrPair;

public class BlockChainBufferTest {
	BlockChainBuffer bcb;
	@Before
	public void setUp() throws Exception {
		bcb = new BlockChainBuffer();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNumBlks() {
		assertEquals(51, bcb.getNumBlks());
		// Test passed on 4/25 
	}
	
	@Test
	public void testTranslateAddress(){
		AddrPair a0;
		int bNum, offset;
		a0 = bcb.translateAddress(0);
		bNum = a0.getbNum();
		offset = a0.getbAddr();
		assertEquals(0, bNum);
		assertEquals(0, offset);
	}

}
