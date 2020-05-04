package com.ivan.tfm.MainAppBackEnd.utils;

import java.io.UnsupportedEncodingException;

public class Converter {

	public static byte[] asciiToByte32(String ascii){
		byte[] bytearray = new byte[32];
		for (int i = 0; i < ascii.length(); i++) {
			bytearray[i] = (byte) Character.codePointAt(ascii, i);
		}
		return bytearray;
	};

	public static byte[] asciiToByte(String ascii){
		byte[] bytearray = new byte[ascii.length()];
		for (int i = 0; i < ascii.length(); i++) {
			bytearray[i] = (byte) Character.codePointAt(ascii, i);
		}
		return bytearray;
	};

	public static String byteToAscii(byte[] bytes) {
		String res="";
		try {
			res = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return res.trim();
	}
}
