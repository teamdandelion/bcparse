package com.danmane.bcparse;

import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BitTools {
	public static MappedByteBuffer openByteBuffer(String fileName) throws Exception{
		RandomAccessFile memMapFile;
		MappedByteBuffer contents;
		memMapFile = new RandomAccessFile(fileName, "r");
//		log("got memMapFile");
		long fileSize = memMapFile.length();
//		log("fileSize is " + fileSize);
		contents = memMapFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
		contents.order(ByteOrder.LITTLE_ENDIAN);
		memMapFile.close();
		return contents;
	}
	
}
