package com.lucasbrandao.mycryptolist.exceptions;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionInterceptor {
	
	@Value("${spring.application.name}")
	private String appName;
	
	@ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
	public ResponseEntity<ExceptionModel> handleNoSuchElementException(final HttpRequestMethodNotSupportedException exception, WebRequest request) {
		return new ResponseEntity<>(generateException(exception, request), HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
	public ResponseEntity<ExceptionModel> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException exception, WebRequest request) {
		return new ResponseEntity<>(generateException(exception, request), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {IllegalArgumentException.class})
	public ResponseEntity<ExceptionModel> handleIllegalArgumentException(final IllegalArgumentException exception, WebRequest request) {
		return new ResponseEntity<>(generateException(exception, request), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {HttpMessageNotReadableException.class})
	public ResponseEntity<ExceptionModel> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception, WebRequest request) {
		return new ResponseEntity<>(generateException(exception, request), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {RuntimeException.class})
	public ResponseEntity<ExceptionModel> handleRuntimeExceptionException(final RuntimeException exception, WebRequest request) {
		return new ResponseEntity<>(generateException(exception, request), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {NotFoundException.class})
	public ResponseEntity<ExceptionModel> handleNotFound(final NotFoundException exception, WebRequest request) {
		return new ResponseEntity<>(generateException(exception, request), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {MissingServletRequestParameterException.class})
	public ResponseEntity<ExceptionModel> handleMissingServletRequestParameterException(final MissingServletRequestParameterException exception, WebRequest request) {
		return new ResponseEntity<>(generateException(exception, request), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {DuplicateItemException.class})
	public ResponseEntity<ExceptionModel> handleDuplicateItemException(final DuplicateItemException exception, WebRequest request) {
		return new ResponseEntity<>(generateException(exception, request), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = {GenericException.class})
	public ResponseEntity<ExceptionModel> handleGenericException(final GenericException exception, WebRequest request) {
		return new ResponseEntity<ExceptionModel>(generateException(exception, request), exception.getHttpCode());
	}
	
	@ExceptionHandler(value = {DBException.class})
	public ResponseEntity<ExceptionModel> handleDBException(final DBException exception, WebRequest request) {
		return new ResponseEntity<ExceptionModel>(generateException(exception, request), exception.getHttpCode());
	}
	
	@ExceptionHandler(value = {DataIntegrityViolationException.class})
	public ResponseEntity<ExceptionModel> handleSQLConstraintViolationException(final DataIntegrityViolationException exception, WebRequest request) {
		return new ResponseEntity<ExceptionModel>(generateSQLDataAccessException(exception, request), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	private final ExceptionModel generateException(Exception ex, WebRequest request) {
		log.error(ex.getMessage());
		
		return new ExceptionModel(
				ex.getLocalizedMessage(), 
				((ServletWebRequest) request).getRequest().getRequestURI(), 
				new Date(), 
				((ServletWebRequest) request).getRequest().getMethod(),
				appName
			);
	}
	
	private final ExceptionModel generateException(DBException ex, WebRequest request) {
		log.error(ex.getMessage());
		
		return new ExceptionModel(
				"Ocorreu uma falha ao executar a operação solicitada.", 
				((ServletWebRequest) request).getRequest().getRequestURI(), 
				new Date(), 
				((ServletWebRequest) request).getRequest().getMethod(),
				appName
			);
	}
	
	private final ExceptionModel generateSQLDataAccessException(DataAccessException ex, WebRequest request) {
		log.error(ex.getMessage());
		
		return new ExceptionModel(
				"Ocorreu uma falha ao executar a operação solicitada.", 
				((ServletWebRequest) request).getRequest().getRequestURI(), 
				new Date(), 
				((ServletWebRequest) request).getRequest().getMethod(),
				appName
			);
	}
}
