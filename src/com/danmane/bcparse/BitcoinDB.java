package com.danmane.bcparse;

import org.iq80.leveldb.*;
import static org.fusesource.leveldbjni.JniDBFactory.*;
import java.io.*;

public class BitcoinDB {
	DB db;
	public BitcoinDB(String fileName){
		Options options = new Options();
		options.createIfMissing(true);
//		db = factory.open(new File(fileName), options);
	}
}
