package com.monica.garcia.ips.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.primitives.Longs;

public class Util {
	
	/**
	 * Tipo de archivo.
	 */
	private static String EXTENSION = "csv";
	
	private static final String PATTERN_IP = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"; 
	
	/**
	 * Verificar si el archivo tiene la extensión correcta.
	 * @param file
	 * @return
	 */
	public static boolean isCSVFile(MultipartFile file) {
		String extension = file.getOriginalFilename().split("\\.")[1];
		if(!extension.equals(EXTENSION)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validar si es un double.
	 * @param string
	 * @return
	 */
	public static boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validar si es un long.
	 * @param string
	 * @return
	 */
	public static boolean isLong(String string) {
		try {
			Longs.tryParse(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * Validar si es una ip válida.
	 * @param ip
	 * @return
	 */
	public static boolean validateIp(final String ip) {
		Pattern pattern = Pattern.compile(PATTERN_IP);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	/**
	 * Cálculo de una ip a decimal.
	 * @param ip
	 * @return
	 */
	public static long getDecimalIp(String ip) {
		
		long  result = 0L;
		List<String> listSegments = Arrays.asList(ip.split("\\."));
		
		int power = 3;
		for(String i : listSegments) {
			int data = Integer.parseInt(i);
	        result += data * Math.pow(256, power);
			power--;
		}
		
		return result;
	}

}
