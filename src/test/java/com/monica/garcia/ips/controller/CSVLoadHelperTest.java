package com.monica.garcia.ips.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.monica.garcia.ips.exception.ClientErrorException;
import com.monica.garcia.ips.models.Ip;
import com.monica.garcia.ips.service.CSVLoadHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CSVLoadHelperTest {
	
	private static Long IP_FROM = 16777240L;
	private static Long IP_TO = 16777243L;
	private static String COUNTRY_CODE = "AU";
	private static String COUNTRY = "AUSTRALIA";
	private static String REGION = "SOUTH AUSTRALIA";
	private static String CITY = "PROSPECT";
	private static double LATITUDE = -34.8839D;
	private static double LONGITUDE = 138.598D;
	private static String TIMEZONE = "?";
	
	
	@Autowired
	private CSVLoadHelper csvLoadHelper;
	
	/**
	 * Validar carga IPS.
	 */
	@Test
	public void uploadIps() {
		MockMultipartFile file = new MockMultipartFile("file", "prueba.csv", MediaType.TEXT_PLAIN_VALUE, "\"16777240\",\"16777243\",\"AU\",\"AUSTRALIA\",\"SOUTH AUSTRALIA\",\"PROSPECT\",\"-34.8839\",\"138.598\",\"?\"".getBytes());
		List<Ip> listIps = makeIp();
		assertEquals(listIps.get(0).getIpFrom(), csvLoadHelper.csvToIps(file).get(0).getIpFrom());
		assertEquals(listIps.get(0).getIpTo(), csvLoadHelper.csvToIps(file).get(0).getIpTo());
	}
	
	/**
	 * Validar carga IPS.
	 */
	@Test
	public void clientErrorExceptionLengthRow() {
		HttpStatus statusCode = null;
		try {
			MockMultipartFile file = new MockMultipartFile("file", "prueba.csv", MediaType.TEXT_PLAIN_VALUE, "\"16777240\",\"16777243\",\"AU\",\"AUSTRALIA\",\"SOUTH AUSTRALIA\",\"PROSPECT\",\"-34.8839\",\"138.598\"".getBytes());
			csvLoadHelper.csvToIps(file);
		} catch (ClientErrorException ex) {
			statusCode = ex.getStatusCode();
		}
		assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	
	/**
	 * Validar carga IPS.
	 */
	@Test
	public void clientErrorExceptionIpFromIsNotLong() {
		HttpStatus statusCode = null;
		try {
			MockMultipartFile file = new MockMultipartFile("file", "prueba.csv", MediaType.TEXT_PLAIN_VALUE, "\"PR\",\"16777243\",\"AU\",\"AUSTRALIA\",\"SOUTH AUSTRALIA\",\"PROSPECT\",\"-34.8839\",\"138.598\",\"?\"".getBytes());
			csvLoadHelper.csvToIps(file);
		} catch (ClientErrorException ex) {
			statusCode = ex.getStatusCode();
		}
		assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	
	/**
	 * Validar carga IPS.
	 */
	@Test
	public void clientErrorExceptionIpToIsNotLong() {
		HttpStatus statusCode = null;
		try {
			MockMultipartFile file = new MockMultipartFile("file", "prueba.csv", MediaType.TEXT_PLAIN_VALUE, "\"16777240\",\"PR\",\"AU\",\"AUSTRALIA\",\"SOUTH AUSTRALIA\",\"PROSPECT\",\"-34.8839\",\"138.598\",\"?\"".getBytes());
			csvLoadHelper.csvToIps(file);
		} catch (ClientErrorException ex) {
			statusCode = ex.getStatusCode();
		}
		assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	/**
	 * Validar carga IPS.
	 */
	@Test
	public void clientErrorExceptionLatitudeIsNotDouble() {
		HttpStatus statusCode = null;
		try {
			MockMultipartFile file = new MockMultipartFile("file", "prueba.csv", MediaType.TEXT_PLAIN_VALUE, "\"16777240\",\"16777243\",\"AU\",\"AUSTRALIA\",\"SOUTH AUSTRALIA\",\"PROSPECT\",\"PR\",\"138.598\",\"?\"".getBytes());
			csvLoadHelper.csvToIps(file);
		} catch (ClientErrorException ex) {
			statusCode = ex.getStatusCode();
		}
		assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	
	/**
	 * Validar carga IPS.
	 */
	@Test
	public void clientErrorExceptionLongitudeIsNotDouble() {
		HttpStatus statusCode = null;
		try {
			MockMultipartFile file = new MockMultipartFile("file", "prueba.csv", MediaType.TEXT_PLAIN_VALUE, "\"16777240\",\"16777243\",\"AU\",\"AUSTRALIA\",\"SOUTH AUSTRALIA\",\"PROSPECT\",\"-34.8839\",\"PR\",\"?\"".getBytes());
			csvLoadHelper.csvToIps(file);
		} catch (ClientErrorException ex) {
			statusCode = ex.getStatusCode();
		}
		assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
	
	/**
	 * Validar carga IPS.
	 */
	@Test
	public void clientErrorExceptionFileNull() {
		HttpStatus statusCode = null;
		try {
			csvLoadHelper.csvToIps(null);
		} catch (ClientErrorException ex) {
			statusCode = ex.getStatusCode();
		}
		assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}

	
	/**
	 * Armar lista de ips.
	 * @return
	 */
	public List<Ip> makeIp(){
		List<Ip> listIps = new ArrayList<Ip>();
		Ip ip = new Ip();
		ip.setIpFrom(IP_FROM);
		ip.setIpTo(IP_TO);
		ip.setCountryCode(COUNTRY_CODE);
		ip.setCountry(COUNTRY);
		ip.setRegion(REGION);
		ip.setCity(CITY);
		ip.setLatitude(LATITUDE);
		ip.setLongitude(LONGITUDE);
		ip.setTimezone(TIMEZONE);
		listIps.add(ip);
		return listIps;
	}
	
}
