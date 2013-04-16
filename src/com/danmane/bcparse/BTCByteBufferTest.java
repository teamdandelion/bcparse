package com.danmane.bcparse;

import static org.junit.Assert.*;

import java.math.BigInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.source.tree.AssertTree;

public class BTCByteBufferTest {
	private BTCByteBuffer b;
	private BigInteger e0_1, e1_1, e2_1, e3_2, e6_4, e11_4, e16_4, e21_8, e30_8;
	
	@Before
	public void setUp() throws Exception {
		// eX_Y where X is byte address and Y is size (in bytes) of the int
		b = new BTCByteBuffer("/Users/danmane/Dropbox/Code/Eclipse/bcparse/bin/com/danmane/bcparse/resources/b.hex");
		e0_1 = BigInteger.ZERO;
		e1_1 = BigInteger.valueOf(16);
		e2_1 = BigInteger.valueOf(252);
		e3_2 = BigInteger.valueOf(64250);
		e6_4 = BigInteger.valueOf(268501503);
		e11_4 = BigInteger.ZERO;
		e16_4 = new BigInteger("4294967295");
		e21_8 = new BigInteger("1157442765409226768");
		e30_8 = new BigInteger("18446744073709551615");
	}

	@After
	public void tearDown() throws Exception {
		b = null;
	}

	@Test
	public void testGetVarInt1(){
		assertEquals(b.getVarInt(0), e0_1);
		assertEquals(b.getVarInt(1), e1_1);
		assertEquals(b.getVarInt(2), e2_1);
	}
	
	@Test
	public void testGetVarInt2(){
		assertEquals(b.getVarInt(3), e3_2);
	}
	
	@Test
	public void testGetVarInt4(){
		assertEquals(b.getVarInt(11), e11_4);
		assertEquals(b.getVarInt(16), e16_4);
	}

	@Test
	public void testGetVarInt8(){
		assertEquals(b.getVarInt(21), e21_8);
		assertEquals(b.getVarInt(30), e30_8);
	}
	
	@Test
	public void testGetVarInt() {
		assertEquals(b.getVarInt(), e0_1);
		assertEquals(b.getVarInt(), e1_1);
		assertEquals(b.getVarInt(), e2_1);
		assertEquals(b.getVarInt(), e3_2);
		assertEquals(b.getVarInt(), e6_4);
		assertEquals(b.getVarInt(), e11_4);
		assertEquals(b.getVarInt(), e16_4);
		assertEquals(b.getVarInt(), e21_8);
		assertEquals(b.getVarInt(), e30_8);
	}

	private void assertEquals(BigInteger expected, BigInteger actual) {
		assertTrue("expected: " + expected + " got: " + actual, expected.equals(actual));
	}

}
