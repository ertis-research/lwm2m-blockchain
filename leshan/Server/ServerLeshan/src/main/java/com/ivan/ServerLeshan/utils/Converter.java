package com.ivan.ServerLeshan.utils;

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
	
	public static byte[] hexToByte(String s) {
		int len = s.length();
		byte[] data = new byte[len/2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
}
