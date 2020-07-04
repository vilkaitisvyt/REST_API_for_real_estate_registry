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

import lt.vilkaitisvyt.Exception.PropertyTypeNotFoundException;
import lt.vilkaitisvyt.Model.PropertyType;
import lt.vilkaitisvyt.Repository.PropertyTypeRepository;
import lt.vilkaitisvyt.Util.View;

@RestController
public class PropertyTypeController {
	
	@Autowired
	private PropertyTypeRepository propertyTypeRepository;
	
	
	@GetMapping("/propertytypes")
	@JsonView(View.Summary.class)
	List<PropertyType> getAllPropertyTypes() {
		return propertyTypeRepository.findAll();
	}
	
	@GetMapping("/propertytypes/{id}")
	@JsonView(View.Summary.class)
	PropertyType getOne(@PathVariable Long id) {
	    return propertyTypeRepository.findById(id)
	      .orElseThrow(() -> new PropertyTypeNotFoundException(id));
	}
	
	@PostMapping("/propertytypes")
	@JsonView(View.Summary.class)
	PropertyType newPropertyType(@Valid @RequestBody PropertyType newPropertyType) {
	    return propertyTypeRepository.save(newPropertyType);
	}
	
	@PutMapping("/propertytypes/{id}")
	@JsonView(View.Summary.class)
	PropertyType replacePropertyType(@Valid @RequestBody PropertyType newPropertyType, @PathVariable Long id) {
	    return propertyTypeRepository.findById(id)
	      .map(propertyType -> {
	    	propertyType.setPropertyType(newPropertyType.getPropertyType());
	    	propertyType.setTaxRatePercentage(newPropertyType.getTaxRatePercentage());
	        return propertyTypeRepository.save(propertyType);
	      })
	      .orElseGet(() -> {
	    	newPropertyType.setId(id);
	        return propertyTypeRepository.save(newPropertyType);
	      });
	}
	
	@DeleteMapping("/propertytypes/{id}")
	void deletePropertyType(@PathVariable Long id) {
		propertyTypeRepository.deleteById(id);
	}
}
