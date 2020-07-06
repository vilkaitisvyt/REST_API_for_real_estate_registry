package lt.vilkaitisvyt.Util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import lt.vilkaitisvyt.Controller.PropertyTypeController;
import lt.vilkaitisvyt.Model.PropertyType;

@Component
public class PropertyTypeModelAssembler implements RepresentationModelAssembler<PropertyType, EntityModel<PropertyType>> {
	
	@Override
	public EntityModel<PropertyType> toModel(PropertyType propertyType) {

	    return EntityModel.of(propertyType,
	    	linkTo(methodOn(PropertyTypeController.class).getOne(propertyType.getId())).withSelfRel(),
	        linkTo(methodOn(PropertyTypeController.class).getAll()).withRel("propertytypes"));
	}
}
