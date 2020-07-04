package lt.vilkaitisvyt.Model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OwnerToBuildingRecord {
	
	@Min(1)
	@NotNull(message="Building record id cannot be missing or empty")
	private Long buildingRecordId;
	
	@Min(1)
	@NotNull(message="Owner id cannot be missing or empty")
	private Long ownerId;
	
	
	public OwnerToBuildingRecord() {
		
	}

	public OwnerToBuildingRecord(Long buildingRecordId, Long ownerId) {
		super();
		this.buildingRecordId = buildingRecordId;
		this.ownerId = ownerId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getBuildingRecordId() {
		return buildingRecordId;
	}

	public void setBuildingRecordId(Long buildingRecordId) {
		this.buildingRecordId = buildingRecordId;
	}
}
