package com.monica.garcia.ips.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.monica.garcia.ips.exception.ClientErrorException;
import com.monica.garcia.ips.models.Ip;
import com.monica.garcia.ips.util.Util;

@Service
public class CSVLoadHelper {
	
	final static Logger LOGGER = LogManager.getLogger(CSVLoadHelper.class);
	
	/**
	 * procesar el archivo csv.
	 * @param input
	 * @return
	 */
	public List<Ip> csvToIps(MultipartFile file) {
		LOGGER.info("::: CSVLoadHelper - csvToIps ::: INIT UPLOAD :::");
		int row = 0;
		try {
			
			if(file == null) {
				throw new ClientErrorException("el archivo contiene información incorrecta", HttpStatus.BAD_REQUEST);
			}
			
			InputStream input = file.getInputStream();
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withTrim()); 
			List<Ip> ips = new ArrayList<Ip>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			
			for (CSVRecord csvRecord : csvRecords) {
				row++;
				
				if(csvRecord == null) {
					throw new ClientErrorException("La fila "+ row +" contiene información incorrecta", HttpStatus.BAD_REQUEST);
				}
				
				if(csvRecord.size() < 9 || csvRecord.size() > 9) {
					throw new ClientErrorException("La fila "+ row +" tiene la cantidad de columnas incorrecta.", HttpStatus.BAD_REQUEST);
				}
				
				if(csvRecord.get(0) == null  || (csvRecord.get(0) != null && !Util.isLong(csvRecord.get(0)))) {
					throw new ClientErrorException("El valor del rango inicial de ips de la fila "+ row +" es incorrecto.", HttpStatus.BAD_REQUEST);
				}
				
				if(csvRecord.get(1) == null || (csvRecord.get(1) != null && !Util.isLong(csvRecord.get(1)))) {
					throw new ClientErrorException("El valor del rango final de ips de la fila "+ row +" es incorrecto.", HttpStatus.BAD_REQUEST);
				}
				
				if(csvRecord.get(6) == null || (csvRecord.get(6) != null && !Util.isDouble(csvRecord.get(6)))) {
					throw new ClientErrorException("El valor de la latitud de la fila "+ row +" es incorrecto.", HttpStatus.BAD_REQUEST);
				}
				
				if(csvRecord.get(7) == null || (csvRecord.get(7) != null && !Util.isDouble(csvRecord.get(7)))) {
					throw new ClientErrorException("El valor de la longitud de la fila "+ row +" es incorrecto.", HttpStatus.BAD_REQUEST);
				}
				
				Ip ip = new Ip();
				ip.setIpFrom(new BigInteger(csvRecord.get(0)).longValue());
				ip.setIpTo(new BigInteger(csvRecord.get(1)).longValue());
				ip.setCountryCode(csvRecord.get(2));
				ip.setCountry(csvRecord.get(3));
				ip.setRegion(csvRecord.get(4));
				ip.setCity(csvRecord.get(5));
				ip.setLatitude(Double.parseDouble(csvRecord.get(6)));
				ip.setLongitude(Double.parseDouble(csvRecord.get(7)));
				ip.setTimezone(csvRecord.get(8));
				ips.add(ip);
			}
			
			
			if(ips == null || ips.isEmpty()) {
				throw new ClientErrorException("Ocurrió un error, el archivo se encuentra vacío.", HttpStatus.BAD_REQUEST);
			}
			
			LOGGER.info("::: CSVLoadHelper - csvToIps ::: FINISH UPLOAD :::");
			return ips;
			
		} catch (IOException e) {
			LOGGER.error("ERROR CSVLoadHelper - csvToIps::: ROW ::: "+ row +"TRAZA" + e.getMessage());
			throw new ClientErrorException("Ocurrió un error al cargar el archivo.", HttpStatus.BAD_REQUEST);
		} 
	}

}
