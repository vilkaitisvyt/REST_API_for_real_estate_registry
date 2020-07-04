package lt.vilkaitisvyt.Model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PropertyTypeToBuildingRecord {
	
	@Min(1)
	@NotNull(message="Building record id cannot be missing or empty")
	private Long buildingRecordId;
	
	@Min(1)
	@NotNull(message="Property type id cannot be missing or empty")
	private Long propertyTypeId;
	
	
	public PropertyTypeToBuildingRecord() {
		
	}

	public PropertyTypeToBuildingRecord(Long buildingRecordId, Long propertyTypeId) {
		super();
		this.buildingRecordId = buildingRecordId;
		this.propertyTypeId = propertyTypeId;
	}

	public Long getBuildingRecordId() {
		return buildingRecordId;
	}

	public void setBuildingRecordId(Long buildingRecordId) {
		this.buildingRecordId = buildingRecordId;
	}

	public Long getPropertyTypeId() {
		return propertyTypeId;
	}

	public void setPropertyTypeId(Long propertyTypeId) {
		this.propertyTypeId = propertyTypeId;
	}
}
