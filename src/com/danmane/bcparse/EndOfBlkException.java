package com.danmane.bcparse;

public class EndOfBlkException extends Exception {
	public EndOfBlkException(String message){
		super(message);
	}
	
	public EndOfBlkException(){
		super();
	}
}
