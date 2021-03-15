package com.monica.garcia.ips.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.monica.garcia.ips.exception.ClientErrorException;
import com.monica.garcia.ips.models.Ip;
import com.monica.garcia.ips.repository.LoadIpsRepository;
import com.monica.garcia.ips.util.Util;

@Service
public class LoadIpsService {

	
	final static Logger LOGGER = LogManager.getLogger(LoadIpsService.class);

	@Autowired
	LoadIpsRepository repository;
	
	@Autowired
	private CSVLoadHelper csvLoadHelper;
	
	/**
	 * Guardar la información de las ips del archivo.
	 * @param file
	 */
	public void loadIps(MultipartFile file) {
		LOGGER.info("::: LoadIpsService - loadIps ::: INIT LOAD :::");
		List<Ip> ips = csvLoadHelper.csvToIps(file);		
		try {			
			LOGGER.info("::: LoadIpsService - loadIps ::: INIT SAVE DATA :::");
			repository.saveAll(ips);
			LOGGER.info("::: LoadIpsService - loadIps ::: FINISH SAVE DATA :::");
		} catch (Exception e) {	
			LOGGER.error("ERROR LoadIpsService::: " + e.getMessage());
			throw new ClientErrorException("Ocurrió un error al almacenar la información del archivo.", HttpStatus.BAD_REQUEST);
		}
		LOGGER.info("::: LoadIpsService - loadIps ::: FINISH LOAD :::");
	}
	
	/**
	 * Consultar información de una ip.
	 * @param ip
	 * @return
	 */
	public List<Ip> getLoadIps(String ip) {
		LOGGER.info("::: LoadIpsService - getLoadIps ::: INIT GET LOAD IP :::");
		//Transformar la ip a decimal.
		long decimalIp = Util.getDecimalIp(ip);
		LOGGER.info("ip: "+ip+ " :::: -> Decimal : "+decimalIp);
		LOGGER.info("::: LoadIpsService - getLoadIps ::: FINISH GET LOAD IP :::");
		//Consultar la información de la ip.
		return repository.getIpByDecimalIp(decimalIp);
		
	}

}
