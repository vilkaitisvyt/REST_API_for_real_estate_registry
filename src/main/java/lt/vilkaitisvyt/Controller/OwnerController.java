package lt.vilkaitisvyt.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

import lt.vilkaitisvyt.Dao.OwnerRepository;
import lt.vilkaitisvyt.Exception.OwnerNotFoundException;
import lt.vilkaitisvyt.Model.BuildingRecord;
import lt.vilkaitisvyt.Model.Owner;
import lt.vilkaitisvyt.Model.TotalYearlyTaxes;
import lt.vilkaitisvyt.Util.OwnerModelAssembler;

@RestController
public class OwnerController {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private OwnerModelAssembler assembler;
	
	
	@GetMapping("/owners")
	public CollectionModel<EntityModel<Owner>> getAll() {		
		List<EntityModel<Owner>> owners = ownerRepository.findAll().stream()
				.map(assembler::toModel)
			    .collect(Collectors.toList());

			return CollectionModel.of(owners, linkTo(methodOn(OwnerController.class).getAll()).withSelfRel());
	}
	
	@GetMapping("/owners/{id}")
	public EntityModel<Owner> getOne(@PathVariable Long id) {		
		Owner owner = ownerRepository.findById(id)
			      .orElseThrow(() -> new OwnerNotFoundException(id));
		
	    return assembler.toModel(owner);
	}
	
	@GetMapping("/owners/{id}/totalyearlytaxes")
	EntityModel<TotalYearlyTaxes> calculateTaxes(@PathVariable Long id) {
	    Owner owner =  ownerRepository.findById(id)
	      .orElseThrow(() -> new OwnerNotFoundException(id));
	    
	    BigDecimal taxes = new BigDecimal(0.0);
	    
	    for(BuildingRecord record: owner.getBuildingRecords()) {
	    	if(record.getPropertyType() != null) {
	    		taxes = taxes.add(record.getMarketValue().multiply(record.getPropertyType().getTaxRatePercentage().movePointLeft(2)));
	    	}
	    }
	    
	    return EntityModel.of(new TotalYearlyTaxes(taxes),
	    	      linkTo(methodOn(OwnerController.class).calculateTaxes(id)).withSelfRel(),
	    	      linkTo(methodOn(OwnerController.class).getAll()).withRel("owners"));
	}
	
	@PostMapping("/owners")
	ResponseEntity<?> newOwner(@Valid @RequestBody Owner newOwner) {		
		EntityModel<Owner> entityModel = assembler.toModel( ownerRepository.save(newOwner));
		
		return ResponseEntity
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			      .body(entityModel);
	}
	
	@PutMapping("/owners/{id}")
	ResponseEntity<?> replaceOwner(@Valid @RequestBody Owner newOwner, @PathVariable Long id) {		
	    Owner updatedOwner = ownerRepository.findById(id)
	      .map(owner -> {
	        owner.setFirstName(newOwner.getFirstName());
	        owner.setLastName(newOwner.getLastName());
	        owner.setEmail(newOwner.getEmail());
	        return ownerRepository.save(owner);
	      })
	      .orElseGet(() -> {
	        return ownerRepository.save(newOwner);
	      });
	    
	    EntityModel<Owner> entityModel = assembler.toModel(updatedOwner);

	    return ResponseEntity
	        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
	        .body(entityModel);
	}
	
	@DeleteMapping("/owners/{id}")
	ResponseEntity<?> deleteOwner(@PathVariable Long id) {		
	    ownerRepository.deleteById(id);
	    
	    return ResponseEntity.noContent().build();
	}

}
