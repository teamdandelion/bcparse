package com.danmane.bcparse;

import static org.junit.Assert.*;

import java.math.BigInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
	
public class BTCByteBufferTest {
	BTCByteBuffer b, b0, bend;
	BigInteger e0_1, e1_1, e2_1, e3_2, e6_4, e11_4, e16_4, e21_8, e30_8;
	BigInteger magicNum;
	
	@Before
	public void setUp() throws Exception {
		// eX_Y where X is byte address and Y is size (in bytes) of the int
		b = new BTCByteBuffer("/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/b.hex");
		b0 = new BTCByteBuffer("/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/test_blks/blk00000.dat");
		bend = new BTCByteBuffer("/Users/danmane/Dropbox/Code/Eclipse/bcparse/test/endian.hex");

		// e0_1 = BigInteger.ZERO;
		// e1_1 = BigInteger.valueOf(16);
		// e2_1 = BigInteger.valueOf(252);
		// e3_2 = BigInteger.valueOf(64250);
		// e6_4 = BigInteger.valueOf(268501503);
		// e11_4 = BigInteger.ZERO;
		// e16_4 = new BigInteger("4294967295");
		// e21_8 = new BigInteger("1157442765409226768");
		// e30_8 = new BigInteger("18446744073709551615");
		magicNum = new BigInteger("3652501241");
	}

	@After
	public void tearDown() throws Exception {
		b = null;
	}

	@Test
	public void testEndianness(){
		assertBIEquals(BigInteger.ONE, bend.get4Byte());
	}
	
	@Test
	public void testGetVarInt1(){
		assertBIEquals(BigInteger.ZERO,         b.getVarInt(0));
		assertBIEquals(BigInteger.valueOf(16),  b.getVarInt(1));
		assertBIEquals(BigInteger.valueOf(252), b.getVarInt(2));
	}

	@Test
	public void testGetVarInt2(){
		assertBIEquals(BigInteger.valueOf(64250), b.getVarInt(3));
	}

	@Test
	public void testGetVarInt4(){
		assertBIEquals(new BigInteger("4278255888"), b.getVarInt(6));
		assertBIEquals(BigInteger.ZERO, b.getVarInt(11));
		assertBIEquals(new BigInteger("4294967295"), b.getVarInt(16));
	}

	@Test
	public void testGetVarInt8(){
		assertBIEquals(new BigInteger("1157442765409226768") , b.getVarInt(21));
		assertBIEquals(new BigInteger("18446744073709551615"), b.getVarInt(30));
	}

	@Test
	public void testGetVarInt() {
		assertBIEquals(BigInteger.ZERO, b.getVarInt());
		assertEquals(1, b.position());
		assertBIEquals(BigInteger.valueOf(16), b.getVarInt());
		assertEquals(2, b.position());
		assertBIEquals(BigInteger.valueOf(252), b.getVarInt());
		assertEquals(3, b.position());
		assertBIEquals(BigInteger.valueOf(64250), b.getVarInt());
		assertEquals(6, b.position());
		assertBIEquals(new BigInteger("4278255888"), b.getVarInt());
		assertEquals(11, b.position());
		assertBIEquals(BigInteger.ZERO, b.getVarInt());
		assertEquals(16, b.position());
		assertBIEquals(new BigInteger("4294967295")          , b.getVarInt());
		assertEquals(21, b.position());
		assertBIEquals(new BigInteger("1157442765409226768") , b.getVarInt());
		assertEquals(30, b.position());
		assertBIEquals(new BigInteger("18446744073709551615"), b.getVarInt());
	}

	@Test
	public void testGet4Byte(){
		assertBIEquals(magicNum, b0.get4Byte());
	}

	private void assertBIEquals(BigInteger expected, BigInteger actual) {
		assertTrue("expected: " + expected + " got: " + actual, expected.equals(actual));
	}

}
