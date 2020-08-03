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

import lt.vilkaitisvyt.Dao.BuildingRecordRepository;
import lt.vilkaitisvyt.Dao.OwnerRepository;
import lt.vilkaitisvyt.Dao.PropertyTypeRepository;
import lt.vilkaitisvyt.Exception.BuildingRecordNotFoundException;
import lt.vilkaitisvyt.Exception.OwnerNotFoundException;
import lt.vilkaitisvyt.Exception.PropertyTypeNotFoundException;
import lt.vilkaitisvyt.Model.BuildingRecord;
import lt.vilkaitisvyt.Model.Owner;
import lt.vilkaitisvyt.Model.OwnerToBuildingRecord;
import lt.vilkaitisvyt.Model.PropertyType;
import lt.vilkaitisvyt.Model.PropertyTypeToBuildingRecord;
import lt.vilkaitisvyt.Util.BuildingRecordModelAssembler;

@RestController
public class BuildingRecordController {
	
	@Autowired
	private BuildingRecordRepository buildingRecordRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private PropertyTypeRepository propertyTypeRepository;
	
	@Autowired
	private BuildingRecordModelAssembler assembler;
	
	
	@GetMapping("/buildingrecords")
	public CollectionModel<EntityModel<BuildingRecord>> getAll() {
		List<EntityModel<BuildingRecord>> buildingRecords =  buildingRecordRepository.findAll().stream()
				.map(assembler::toModel)
			    .collect(Collectors.toList());

			return CollectionModel.of(buildingRecords, linkTo(methodOn(BuildingRecordController.class).getAll()).withSelfRel());
	}
	
	@GetMapping("/buildingrecords/{id}")
	public EntityModel<BuildingRecord> getOne(@PathVariable Long id) {
		BuildingRecord buildingRecord = buildingRecordRepository.findById(id)
	      .orElseThrow(() -> new BuildingRecordNotFoundException(id));
		
		return assembler.toModel(buildingRecord);
	}
	
	@PostMapping("/buildingrecords")
	ResponseEntity<?> newBuildingRecord(@Valid @RequestBody BuildingRecord newBuildingRecord) {		
		BuildingRecord buildingRecord = new BuildingRecord(newBuildingRecord.getAdress(),
				newBuildingRecord.getSizeInSquareMeters(),
				newBuildingRecord.getMarketValue());
		
		EntityModel<BuildingRecord> entityModel = assembler.toModel(buildingRecordRepository.save(buildingRecord));
		
		return ResponseEntity
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			      .body(entityModel);
	}
	
	@PutMapping("/buildingrecords/{id}")
	ResponseEntity<?> replaceBuildingRecord(@Valid @RequestBody BuildingRecord newBuildingRecord, @PathVariable Long id) {
		BuildingRecord updatedBuildingRecord =  buildingRecordRepository.findById(id)
	      .map(buildingRecord -> {
	    	buildingRecord.setAdress(newBuildingRecord.getAdress());
	    	buildingRecord.setSizeInSquareMeters(newBuildingRecord.getSizeInSquareMeters());
	    	buildingRecord.setMarketValue(newBuildingRecord.getMarketValue());
	        return buildingRecordRepository.save(buildingRecord);
	      })
	      .orElseGet(() -> {
	        return buildingRecordRepository.save(newBuildingRecord);
	      });
		
		EntityModel<BuildingRecord> entityModel = assembler.toModel(updatedBuildingRecord);

	    return ResponseEntity
	        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
	        .body(entityModel);
	}
	
	@PutMapping("/buildingrecords/addowner")
	ResponseEntity<?> addOwnerToBuildingRecord(@Valid @RequestBody OwnerToBuildingRecord ownerToBuildingRecord) {
		BuildingRecord record =  buildingRecordRepository.findById(ownerToBuildingRecord.getBuildingRecordId())
				.orElseThrow(() -> new BuildingRecordNotFoundException(ownerToBuildingRecord.getBuildingRecordId()));
		
		Owner owner = ownerRepository.findById(ownerToBuildingRecord.getOwnerId())
				.orElseThrow(() -> new OwnerNotFoundException(ownerToBuildingRecord.getOwnerId()));
		
		record.setOwner(owner);
		
	    EntityModel<BuildingRecord> entityModel = assembler.toModel(buildingRecordRepository.save(record));

		return ResponseEntity
		        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
		        .body(entityModel);
	}
	
	@PutMapping("/buildingrecords/addpropertytype")
	ResponseEntity<?> addPropertyTypeToBuildingRecord(@Valid @RequestBody PropertyTypeToBuildingRecord propertyTypeToBuildingRecord) {
		BuildingRecord record =  buildingRecordRepository.findById(propertyTypeToBuildingRecord.getBuildingRecordId())
				.orElseThrow(() -> new BuildingRecordNotFoundException(propertyTypeToBuildingRecord.getBuildingRecordId()));
		
		PropertyType propertyType = propertyTypeRepository.findById(propertyTypeToBuildingRecord.getPropertyTypeId())
				.orElseThrow(() -> new PropertyTypeNotFoundException(propertyTypeToBuildingRecord.getPropertyTypeId()));
		
		record.setPropertyType(propertyType);
		
		EntityModel<BuildingRecord> entityModel = assembler.toModel(buildingRecordRepository.save(record));
		
		return ResponseEntity
		        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
		        .body(entityModel);
	}
	
	@DeleteMapping("/buildingrecords/{id}")
	ResponseEntity<?> deleteBuildingRecords(@PathVariable Long id) {
		buildingRecordRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

}
