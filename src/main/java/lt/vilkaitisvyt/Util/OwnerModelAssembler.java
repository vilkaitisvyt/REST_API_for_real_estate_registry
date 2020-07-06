package lt.vilkaitisvyt.Util;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import lt.vilkaitisvyt.Controller.OwnerController;
import lt.vilkaitisvyt.Model.Owner;

@Component
public class OwnerModelAssembler implements RepresentationModelAssembler<Owner, EntityModel<Owner>> {
	
	@Override
	public EntityModel<Owner> toModel(Owner owner) {

	    return EntityModel.of(owner,
	    	linkTo(methodOn(OwnerController.class).getOne(owner.getId())).withSelfRel(),
	        linkTo(methodOn(OwnerController.class).getAll()).withRel("owners"));
	}
}
