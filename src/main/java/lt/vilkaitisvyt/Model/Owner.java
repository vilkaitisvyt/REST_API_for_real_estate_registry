package lt.vilkaitisvyt.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Owner {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="First name cannot be missing or empty")
    @Size(min=2, message="First name must not be less than 2 characters")
	private String firstName;
	
	@NotBlank(message="Last name cannot be missing or empty")
    @Size(min=2, message="Last name must not be less than 2 characters")
	private String lastName;
	
	@Email
	private String email;
	
	@JsonIgnore
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<BuildingRecord> buildingRecords = new ArrayList<>();
	
	
	public Owner () {
		
	}
	
	public Owner(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
			record.setOwner(this);
		}
	}

	@Override
	public String toString() {
		return "Owner {id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "}";
	}
}
