package lt.vilkaitisvyt.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BuildingRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String adress;
	private String owner;
	private Integer size;
	private Double marketValue;
	private String propertyType;
	
	
	public BuildingRecord(String adress, String owner, Integer size, Double marketValue, String propertyType) {
		super();
		this.adress = adress;
		this.owner = owner;
		this.size = size;
		this.marketValue = marketValue;
		this.propertyType = propertyType;
	}


	public String getAdress() {
		return adress;
	}


	public void setAdress(String adress) {
		this.adress = adress;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public Integer getSize() {
		return size;
	}


	public void setSize(Integer size) {
		this.size = size;
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
