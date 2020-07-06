package lt.vilkaitisvyt.ControllerAdvice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lt.vilkaitisvyt.Exception.BuildingRecordNotFoundException;
import lt.vilkaitisvyt.Exception.OwnerNotFoundException;
import lt.vilkaitisvyt.Exception.PropertyTypeNotFoundException;

@ControllerAdvice
public class ResourceNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(OwnerNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String ownerNotFoundHandler(OwnerNotFoundException ex) {
	    return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(PropertyTypeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String PropertyTypeNotFoundHandler(PropertyTypeNotFoundException ex) {
	    return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(BuildingRecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String BuildingRecordNotFoundHandler(BuildingRecordNotFoundException ex) {
	    return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String DeleteEmptyHandler(EmptyResultDataAccessException ex) {
	    return ex.getMessage();
	}
	
	
	@ResponseBody
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	String DeleteWithForeignKeyPresentHandler(DataIntegrityViolationException ex) {
	    return ex.getMessage();
	}
	
	
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String ValidationFailHandler(MethodArgumentNotValidException ex) {
	    return ex.getMessage();
	}
}
