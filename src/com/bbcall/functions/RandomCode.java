package com.bbcall.functions;

public class RandomCode {

	private static char[] code = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'0' };
	
	public String getNoncestr(){
		String str = "";
		for(Integer i=0;i<16;i++){
			str += code[(int)(Math.random()*62)];
		}
		return str;
	}
	
	public String getToken(){
		String str = "";
		for(Integer i=0;i<50;i++){
			str += code[(int)(Math.random()*62)];
		}
		return str;
	}
}