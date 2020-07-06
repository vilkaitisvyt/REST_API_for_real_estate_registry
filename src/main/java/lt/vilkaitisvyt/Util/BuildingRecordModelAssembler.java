package lt.vilkaitisvyt.Util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import lt.vilkaitisvyt.Controller.BuildingRecordController;
import lt.vilkaitisvyt.Model.BuildingRecord;

@Component
public class BuildingRecordModelAssembler implements RepresentationModelAssembler<BuildingRecord, EntityModel<BuildingRecord>> {
	
	@Override
	public EntityModel<BuildingRecord> toModel(BuildingRecord buildingRecord) {

	    return EntityModel.of(buildingRecord,
	    	linkTo(methodOn(BuildingRecordController.class).getOne(buildingRecord.getId())).withSelfRel(),
	        linkTo(methodOn(BuildingRecordController.class).getAll()).withRel("buildingrecords"));
	}
}
