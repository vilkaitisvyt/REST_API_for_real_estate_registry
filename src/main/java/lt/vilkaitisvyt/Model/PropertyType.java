package lt.vilkaitisvyt.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PropertyType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Property type cannot be missing or empty")
    @Size(min=2, message="Property type must not be less than 2 characters")
	private String propertyType;
	
	@Min(0)
	@Max(100)
	@NotNull(message="Tax rate cannot be missing or empty")
	private Double taxRatePercentage;
	
	@JsonIgnore
	@OneToMany(mappedBy = "propertyType", fetch = FetchType.LAZY)
	private List<BuildingRecord> buildingRecords = new ArrayList<>();
	

	public PropertyType() {
		
	}
	
	public PropertyType(String propertyType, Double taxRatePercentage) {
		super();
		this.propertyType = propertyType;
		this.taxRatePercentage = taxRatePercentage;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Double getTaxRatePercentage() {
		return taxRatePercentage;
	}

	public void setTaxRatePercentage(Double taxRatePercentage) {
		this.taxRatePercentage = taxRatePercentage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<BuildingRecord> getBuildingRecords() {
		return buildingRecords;
	}

	public void setBuildingRecords(List<BuildingRecord> buildingRecords) {
		this.buildingRecords = buildingRecords;
		for(BuildingRecord record : buildingRecords) {
			record.setPropertyType(this);
		}
	}

	@Override
	public String toString() {
		return "PropertyType {id=" + id + ", propertyType=" + propertyType + ", taxRatePercentage=" + taxRatePercentage + "%}";
	}
}
