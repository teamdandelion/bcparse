package com.danmane.bcparse;

import static org.junit.Assert.*;

import java.math.BigInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.source.tree.AssertTree;

public class BTCByteBufferTest {
	BTCByteBuffer b, b0;
	BigInteger e0_1, e1_1, e2_1, e3_2, e6_4, e11_4, e16_4, e21_8, e30_8;
	BigInteger magicNum;
	
	@Before
	public void setUp() throws Exception {
		// eX_Y where X is byte address and Y is size (in bytes) of the int
		b = new BTCByteBuffer("/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/b.hex");
		b0 = new BTCByteBuffer("/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/test_blks/blk00000.dat");
		e0_1 = BigInteger.ZERO;
		e1_1 = BigInteger.valueOf(16);
		e2_1 = BigInteger.valueOf(252);
		e3_2 = BigInteger.valueOf(64250);
		e6_4 = BigInteger.valueOf(268501503);
		e11_4 = BigInteger.ZERO;
		e16_4 = new BigInteger("4294967295");
		e21_8 = new BigInteger("1157442765409226768");
		e30_8 = new BigInteger("18446744073709551615");
		magicNum = new BigInteger("3652501241");
	}

	@After
	public void tearDown() throws Exception {
		b = null;
	}

	@Test
	public void testGetVarInt1(){
		assertBIEquals(b.getVarInt(0), e0_1);
		assertBIEquals(b.getVarInt(1), e1_1);
		assertBIEquals(b.getVarInt(2), e2_1);
	}
	
	@Test
	public void testGetVarInt2(){
		assertBIEquals(b.getVarInt(3), e3_2);
	}
	
	@Test
	public void testGetVarInt4(){
		assertBIEquals(b.getVarInt(11), e11_4);
		assertBIEquals(b.getVarInt(16), e16_4);
	}

	@Test
	public void testGetVarInt8(){
		assertBIEquals(b.getVarInt(21), e21_8);
		assertBIEquals(b.getVarInt(30), e30_8);
	}
	
	@Test
	public void testGetVarInt() {
		assertBIEquals(b.getVarInt(), e0_1);
		assertBIEquals(b.getVarInt(), e1_1);
		assertBIEquals(b.getVarInt(), e2_1);
		assertBIEquals(b.getVarInt(), e3_2);
		assertBIEquals(b.getVarInt(), e6_4);
		assertBIEquals(b.getVarInt(), e11_4);
		assertBIEquals(b.getVarInt(), e16_4);
		assertBIEquals(b.getVarInt(), e21_8);
		assertBIEquals(b.getVarInt(), e30_8);
	}
	
	@Test
	public void testGet4Byte(){
		assertBIEquals(magicNum, b0.get4Byte());
	}

	private void assertBIEquals(BigInteger expected, BigInteger actual) {
		assertTrue("expected: " + expected + " got: " + actual, expected.equals(actual));
	}

}
