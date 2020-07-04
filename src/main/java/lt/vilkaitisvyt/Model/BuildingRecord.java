package lt.vilkaitisvyt.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import lt.vilkaitisvyt.Util.View;

@Entity
public class BuildingRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.Summary.class)
	private Long id;
	
	@NotBlank(message="Adress cannot be missing or empty")
    @Size(min=2, message="Adress must not be less than 2 characters")
	@JsonView(View.Summary.class)
	private String adress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	@JsonView(View.Summary.class)
	private Owner owner;
	
	@Min(1)
	@NotNull(message="Size cannot be missing or empty")
	@JsonView(View.Summary.class)
	private Integer sizeInSquareMeters;
	
	@Min(0)
	@NotNull(message="Value cannot be missing or empty")
	@JsonView(View.Summary.class)
	private Double marketValue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "propertyType_id")
	@JsonView(View.Summary.class)
	private PropertyType propertyType;
	
	
	public BuildingRecord() {
		
	}
	
	public BuildingRecord(String adress, Integer sizeInSquareMeters, Double marketValue) {
		super();
		this.adress = adress;
		this.sizeInSquareMeters = sizeInSquareMeters;
		this.marketValue = marketValue;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
		owner.getBuildingRecords().add(this);
	}

	public Integer getSizeInSquareMeters() {
		return sizeInSquareMeters;
	}	

	public void setSizeInSquareMeters(Integer sizeInSquareMeters) {
		this.sizeInSquareMeters = sizeInSquareMeters;
	}	

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
		propertyType.getBuildingRecords().add(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BuildingRecord {id=" + id + ", adress=" + adress + ", owner=" + owner.getFirstName() + ", sizeInSquareMeters="
				+ sizeInSquareMeters + " m^2, marketValue=" + marketValue + ", propertyType=" + propertyType.getPropertyType() + "}";
	}
}
