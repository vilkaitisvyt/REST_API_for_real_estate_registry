package lt.vilkaitisvyt.Exception;

public class PropertyTypeNotFoundException extends RuntimeException {


	public PropertyTypeNotFoundException(Long id) {
	    super("Could not find property type " + id);
	}

}
