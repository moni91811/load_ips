package com.monica.garcia.ips.exception;

import java.util.NoSuchElementException;

import javax.validation.UnexpectedTypeException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.monica.garcia.ips.util.Response;

@ControllerAdvice
public class ExceptionController {

	final static Logger LOGGER = LogManager.getLogger(ExceptionController.class);


	/**
	 * Server error
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ClientErrorException.class)
	public ResponseEntity<Response> handleHttpServerClientException(ClientErrorException e) {
		
		Response response = new Response();
		response.setMessage(e.getMessage());
		
		return new ResponseEntity<>(response, e.getStatusCode());
	}
	
	/**
	 * Not found in database
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Response> handleNoSuchElementException(NoSuchElementException e) {
		
		LOGGER.error("Error! " + e.getCause());
		
		Response response = new Response();
		response.setMessage("El registro no fue encontrado en la busqueda");
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Error en ingreso de formatos tipo date no puede ser nulo o vacio
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UnexpectedTypeException.class)
	public ResponseEntity<Response> handleUnexpectedTypeException(UnexpectedTypeException e) {
		
		LOGGER.error("Error! " + e.getCause());
		
		Response response = new Response();
		response.setMessage("Error de input, datos tipo fecha no pueden estar vacios");
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Error cuando desde el cliente no se envia adecuadamente el formato json /error token json
	 * @param e
	 * @return
	 */
	@ExceptionHandler(JsonEOFException.class)
	public ResponseEntity<Response> handleJsonEOFException(JsonEOFException e) {
		
		LOGGER.error("Error! " + e.getMessage());
		
		Response response = new Response();
		response.setMessage("Error en el envio de datos desde el cliente al servidor tipo json");
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Error en ingreso de formatos numericos
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Response> handleNumberFormatException(NumberFormatException e) {
		
		LOGGER.error("Error! " + e.getMessage());
		
		Response response = new Response();
		response.setMessage("Error de input, no se puede convertir un alfanumerico a un tipo int");
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Empty inputs
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException e) {
		
		LOGGER.error("Error! " + e.getMessage());
		
		Response response = new Response();
		StringBuilder strBuild = new StringBuilder();
		e.getBindingResult().getAllErrors().forEach(error -> {
			//String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			if(!strBuild.toString().trim().equals("")) {
				strBuild.append(",");
			}
			//strBuild.append(fieldName);
			strBuild.append(" ");
			strBuild.append(errorMessage);
		});
		
		response.setMessage("Verificar los siguientes inputs: "+strBuild.toString());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Error cuando no se puede deserializar el json
	 * @param e
	 * @return
	 */
	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<Response> handleJsonParseException(JsonParseException e) {
		
		LOGGER.error("Error! " + e.getMessage());
		
		Response response = new Response();
		response.setMessage("No se puede deserializar el request desde el cliente");
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Error cuando no está presente el archivo en la carga.
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<Response> handleMultipartException(MultipartException e) {
		LOGGER.error("Error! " + e.getMessage());
		
		Response response = new Response();
		response.setMessage("Ocurrió un error con el archivo.");
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Error cuando no está presente el archivo en la carga.
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<Response> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
		LOGGER.error("Error! " + e.getMessage());
		
		Response response = new Response();
		response.setMessage("El archivo file no se encuentra presente en la invocación del servicio.");
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}
