package com.monica.garcia.ips.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.monica.garcia.ips.dto.IpDTO;
import com.monica.garcia.ips.models.Ip;
import com.monica.garcia.ips.service.LoadIpsService;
import com.monica.garcia.ips.util.Response;
import com.monica.garcia.ips.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/ip")
@Api(value = "Clase dedicada a ser el controlador para gestionar la carga y consulta de ips.")
@CrossOrigin 
public class LoadIpController {
	
	final static Logger LOGGER = LogManager.getLogger(LoadIpController.class);

	@Autowired
	private LoadIpsService loadIpsService;
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public IpDTO getDto(Ip entity) {
		IpDTO ipDto = new IpDTO();
		ipDto.setCity(entity.getCity());
		ipDto.setRegion(entity.getRegion());
		ipDto.setCountryCode(entity.getCountryCode());
		ipDto.setTimezone(entity.getTimezone());
		return ipDto;
	}
	
	/**
	 * Cargar información de ips.
	 * @param file
	 * @return
	 */
	@PostMapping(path = "/upload", produces = "application/json")
	@ApiOperation(value = "Esta operacion guardar el cargue de ips.")
	@ResponseBody
	public ResponseEntity<Object> saveLoadIps(@RequestParam MultipartFile file) {
		LOGGER.info("LOAD SAVE IPS::  file::: " + file);
		Response response = new Response();
		
		//Validar que exista el archivo.
		if (file == null) {
			response.setMessage("El archivo a cargar es obligatorio.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		// Validar si es el formato es correcto.
		if (!Util.isCSVFile(file)) {
			response.setMessage("El archivo no tiene el formato correcto.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		//Cargar el archivo.
		loadIpsService.loadIps(file);
		response.setMessage(file.getOriginalFilename() == null ? "Archivo " : "Archivo " + file.getOriginalFilename() + " cargado exitosamente.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Consultar información.
	 * @param ip
	 * @return
	 */
	@GetMapping(path = "/{ip}", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Esta operacion consulta la información de las ip.")
	@ResponseBody
	public ResponseEntity<Object> getLoadIps(@Valid @PathVariable String ip) {
		LOGGER.info("GET LOAD :: IP :: "+ip);
		List<IpDTO> listIpsDto = new ArrayList<IpDTO>();
		Response response = new Response();
		
		//Validar el campo ip.
		if (ip == null || ip.isEmpty()) {
			response.setMessage("El valor de la ip es obligatorio.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		// Validar si la ip es válida.
		if (!Util.validateIp(ip)) {
			response.setMessage("La ip no es válida.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		//Consultar información.
		List<Ip> listIps = loadIpsService.getLoadIps(ip);
		if(listIps != null && !listIps.isEmpty()) {
			listIps.forEach(i -> {
				listIpsDto.add(getDto(i));
			});
			response.setData(listIpsDto);
			response.setMessage("Información encontrada con éxito.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("No se encontró información para la IP "+ip);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
}
