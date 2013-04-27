package com.danmane.bcparse;

import static org.junit.Assert.*;

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
		Block b0 = new Block(0, 0, bcb);
		assertEquals(293, b0.getNextBlockAddr());
		Block b1 = new Block(293, 1, bcb);
	}

}
