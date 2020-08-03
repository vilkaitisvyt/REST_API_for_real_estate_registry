package lt.vilkaitisvyt.Controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lt.vilkaitisvyt.Dao.PropertyTypeRepository;
import lt.vilkaitisvyt.Exception.PropertyTypeNotFoundException;
import lt.vilkaitisvyt.Model.PropertyType;
import lt.vilkaitisvyt.Util.PropertyTypeModelAssembler;

@RestController
public class PropertyTypeController {
	
	@Autowired
	private PropertyTypeRepository propertyTypeRepository;
	
	@Autowired
	private PropertyTypeModelAssembler assembler;
	
	
	@GetMapping("/propertytypes")
	public CollectionModel<EntityModel<PropertyType>> getAll() {
		List<EntityModel<PropertyType>> propertyTypes = propertyTypeRepository.findAll().stream()
				.map(assembler::toModel)
			    .collect(Collectors.toList());

			  return CollectionModel.of(propertyTypes, linkTo(methodOn(PropertyTypeController.class).getAll()).withSelfRel());
	}
	
	@GetMapping("/propertytypes/{id}")
	public EntityModel<PropertyType> getOne(@PathVariable Long id) {		
		PropertyType propertyType =  propertyTypeRepository.findById(id)
				.orElseThrow(() -> new PropertyTypeNotFoundException(id));
		
		return assembler.toModel(propertyType);
	}
	
	@PostMapping("/propertytypes")
	ResponseEntity<?> newPropertyType(@Valid @RequestBody PropertyType newPropertyType) {		
		EntityModel<PropertyType> entityModel = assembler.toModel(propertyTypeRepository.save(newPropertyType));
		
		return ResponseEntity
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			      .body(entityModel);
	}
	
	@PutMapping("/propertytypes/{id}")
	ResponseEntity<?> replacePropertyType(@Valid @RequestBody PropertyType newPropertyType, @PathVariable Long id) {		
		PropertyType updatedPropertyType =  propertyTypeRepository.findById(id)
	      .map(propertyType -> {
	    	propertyType.setPropertyType(newPropertyType.getPropertyType());
	    	propertyType.setTaxRatePercentage(newPropertyType.getTaxRatePercentage());
	        return propertyTypeRepository.save(propertyType);
	      })
	      .orElseGet(() -> {
	        return propertyTypeRepository.save(newPropertyType);
	      });
		
		EntityModel<PropertyType> entityModel = assembler.toModel(updatedPropertyType);

	    return ResponseEntity
	        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
	        .body(entityModel);
	}
	
	@DeleteMapping("/propertytypes/{id}")
	ResponseEntity<?> deletePropertyType(@PathVariable Long id) {		
		propertyTypeRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
