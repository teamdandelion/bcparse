package com.danmane.bcparse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.danmane.bcparse.BlockChainBuffer.AddrPair;

public class BlockChainBufferTest {
	BlockChainBuffer bcb;
	BigInteger magicNum;
	@Before
	public void setUp() throws Exception {
		bcb = new BlockChainBuffer("/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/test_blks/");
		magicNum = new BigInteger("3652501241");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNumBlks() {
		assertEquals(4, bcb.getNumBlks());
	}
	
	@Test
	public void testTranslateAddress(){
		assertAddrEquals(0,  0, bcb.translateAddr(0));
		assertAddrEquals(0, 30, bcb.translateAddr(30));
		assertAddrEquals(1,  0, bcb.translateAddr(791755617));
		assertAddrEquals(1,  1, bcb.translateAddr(791755618));
		assertAddrEquals(3,  134217727, bcb.translateAddr(1194408800));
		try{
			bcb.translateAddr(1194408801);
			fail("This address was out of bounds");
		} catch (IndexOutOfBoundsException expectedException){
		}	
	}
	
	@Test
	public void testGet4Byte(){
		assertBIEquals(magicNum, bcb.get4Byte());
		assertBIEquals(magicNum, bcb.get4Byte(0));
		assertBIEquals(magicNum, bcb.get4Byte(293));
	}
	
	private void assertAddrEquals(int bNumExpected, int addrExpected, AddrPair actual){
		assertEquals(bNumExpected, actual.getbNum());
		assertEquals(addrExpected, actual.getmemAddr());
	}
	
	private void assertBIEquals(BigInteger expected, BigInteger actual) {
		assertTrue("expected: " + expected + " got: " + actual, expected.equals(actual));
	}

}
