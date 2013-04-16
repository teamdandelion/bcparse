package com.danmane.bcparse;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BLKParserTest {
	BLKParser blktest;
	public static final int magic = 0xd9b4bef9;

	@Before
	public void setUp() throws Exception {
		blktest = new BLKParser(-1);
	}

	@After
	public void tearDown() throws Exception {
		blktest = null;
	}

	@Test
	public void testGetInt32(){
		int expected = magic;
		int actual = blktest.getContents().getInt(0);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testNextBlockAddr() {
		// should return the starting address of the next block (i.e. location of magic #)
		// in test case should be 4 + 4 + 285 = 293
		int expected = 293;
		int actual = blktest.nextBlockAddr(0);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetBlockAddrs() {
		// makes sure that every block address points to a magic number
		// DOESNT confirm that we are not missing any blocks
		Integer[] addrs = blktest.getBlockAddrs();
		for (Integer i : addrs){
			assertEquals(blktest.getIntAtAddr(i), magic);
		}
		assertEquals(45, blktest.getNumBlocks());
	}


}
