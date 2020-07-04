package lt.vilkaitisvyt.ControllerAdvice;

import org.springframework.http.HttpStatus;
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
}
