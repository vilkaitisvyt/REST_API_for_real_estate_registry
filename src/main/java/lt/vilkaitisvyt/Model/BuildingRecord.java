package lt.vilkaitisvyt.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BuildingRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String adress;	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(referencedColumnName = "id")
	private Owner owner;
	private Integer sizeInSquareMeters;
	private Double marketValue;
	private String propertyType;
	
	
	public BuildingRecord() {
		
	}
	
	public BuildingRecord(String adress, Owner owner, Integer sizeInSquareMeters, Double marketValue, String propertyType) {
		super();
		this.adress = adress;
		this.owner = owner;
		this.sizeInSquareMeters = sizeInSquareMeters;
		this.marketValue = marketValue;
		this.propertyType = propertyType;
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
	}


	public Integer getSize() {
		return sizeInSquareMeters;
	}


	public void setSize(Integer sizeInSquareMeters) {
		this.sizeInSquareMeters = sizeInSquareMeters;
	}


	public Double getMarketValue() {
		return marketValue;
	}


	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}


	public String getPropertyType() {
		return propertyType;
	}


	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}


	public Long getId() {
		return id;
	}
}
