package com.danmane.bcparse;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

}
