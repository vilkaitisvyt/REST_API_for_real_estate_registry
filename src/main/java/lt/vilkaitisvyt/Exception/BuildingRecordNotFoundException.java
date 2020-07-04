package lt.vilkaitisvyt.Exception;

public class BuildingRecordNotFoundException extends RuntimeException {


	public BuildingRecordNotFoundException(Long id) {
	    super("Could not find building record " + id);
	}
}
