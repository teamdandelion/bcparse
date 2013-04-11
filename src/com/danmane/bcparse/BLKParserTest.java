package com.danmane.bcparse;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BLKParserTest {
	BLKParser b0;

	@Before
	public void setUp() throws Exception {
		b0 = new BLKParser(1);
	}

	@After
	public void tearDown() throws Exception {
		b0 = null;
	}

	@Test
	public void testGetInt32(){
		byte[] contents = b0.getContents();
		
		assertEquals(0xd9b4bef9, b0.getInt32(0, contents));
	}
	
	@Test
	public void testNextBlockAddr() {
		fail("Not yet implemented");
	}
	


}
