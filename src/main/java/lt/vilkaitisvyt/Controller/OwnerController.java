package lt.vilkaitisvyt.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import lt.vilkaitisvyt.Exception.OwnerNotFoundException;
import lt.vilkaitisvyt.Model.BuildingRecord;
import lt.vilkaitisvyt.Model.Owner;
import lt.vilkaitisvyt.Model.TotalYearlyTaxes;
import lt.vilkaitisvyt.Repository.OwnerRepository;
import lt.vilkaitisvyt.Util.View;

@RestController
public class OwnerController {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	
	@GetMapping("/owners")
	@JsonView(View.Summary.class)
	List<Owner> getAllOwners() {
		return ownerRepository.findAll();
	}
	
	@GetMapping("/owners/{id}")
	@JsonView(View.Summary.class)
	Owner getOne(@PathVariable Long id) {
	    return ownerRepository.findById(id)
	      .orElseThrow(() -> new OwnerNotFoundException(id));
	}
	
	@GetMapping("/owners/totalyearlytaxes/{id}")
	TotalYearlyTaxes calculateTaxes(@PathVariable Long id) {
	    Owner owner =  ownerRepository.findById(id)
	      .orElseThrow(() -> new OwnerNotFoundException(id));
	    
	    Double taxes = 0.0;
	    
	    for(BuildingRecord record: owner.getBuildingRecords()) {
	    	if(record.getPropertyType() != null) {
	    		taxes += record.getMarketValue() * (record.getPropertyType().getTaxRatePercentage() / 100);
	    	}
	    }
	    return new TotalYearlyTaxes(taxes);
	}
	
	@PostMapping("/owners")
	@JsonView(View.Summary.class)
	Owner newOwner(@Valid @RequestBody Owner newOwner) {
	    return ownerRepository.save(newOwner);
	}
	
	@PutMapping("/owners/{id}")
	@JsonView(View.Summary.class)
	Owner replaceOwner(@Valid @RequestBody Owner newOwner, @PathVariable Long id) {
	    return ownerRepository.findById(id)
	      .map(owner -> {
	        owner.setFirstName(newOwner.getFirstName());
	        owner.setLastName(newOwner.getLastName());
	        owner.setEmail(newOwner.getEmail());
	        return ownerRepository.save(owner);
	      })
	      .orElseGet(() -> {
	        newOwner.setId(id);
	        return ownerRepository.save(newOwner);
	      });
	}
	
	@DeleteMapping("/owners/{id}")
	void deleteOwner(@PathVariable Long id) {
	    ownerRepository.deleteById(id);
	}

}
