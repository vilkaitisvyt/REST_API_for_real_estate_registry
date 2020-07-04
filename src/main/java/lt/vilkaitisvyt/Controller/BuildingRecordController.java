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

import lt.vilkaitisvyt.Exception.BuildingRecordNotFoundException;
import lt.vilkaitisvyt.Exception.OwnerNotFoundException;
import lt.vilkaitisvyt.Exception.PropertyTypeNotFoundException;
import lt.vilkaitisvyt.Model.BuildingRecord;
import lt.vilkaitisvyt.Model.Owner;
import lt.vilkaitisvyt.Model.OwnerToBuildingRecord;
import lt.vilkaitisvyt.Model.PropertyType;
import lt.vilkaitisvyt.Model.PropertyTypeToBuildingRecord;
import lt.vilkaitisvyt.Repository.BuildingRecordRepository;
import lt.vilkaitisvyt.Repository.OwnerRepository;
import lt.vilkaitisvyt.Repository.PropertyTypeRepository;
import lt.vilkaitisvyt.Util.View;

@RestController
public class BuildingRecordController {
	
	@Autowired
	private BuildingRecordRepository buildingRecordRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private PropertyTypeRepository propertyTypeRepository;
	
	
	@GetMapping("/buildingrecords")
	@JsonView(View.Summary.class)
	List<BuildingRecord> getAllBuildingRecords() {
		return buildingRecordRepository.findAll();
	}
	
	@GetMapping("/buildingrecords/{id}")
	@JsonView(View.Summary.class)
	BuildingRecord getOne(@PathVariable Long id) {
	    return buildingRecordRepository.findById(id)
	      .orElseThrow(() -> new BuildingRecordNotFoundException(id));
	}
	
	@PostMapping("/buildingrecords")
	BuildingRecord newBuildingRecord(@Valid @RequestBody BuildingRecord newBuildingRecord) {
	    return buildingRecordRepository.save(newBuildingRecord);
	}
	
	@PutMapping("/buildingrecords/{id}")
	@JsonView(View.Summary.class)
	BuildingRecord replaceBuildingRecord(@Valid @RequestBody BuildingRecord newBuildingRecord, @PathVariable Long id) {
	    return buildingRecordRepository.findById(id)
	      .map(buildingRecord -> {
	    	buildingRecord.setAdress(newBuildingRecord.getAdress());
	    	buildingRecord.setSizeInSquareMeters(newBuildingRecord.getSizeInSquareMeters());
	    	buildingRecord.setMarketValue(newBuildingRecord.getMarketValue());
	        return buildingRecordRepository.save(buildingRecord);
	      })
	      .orElseGet(() -> {
	    	  newBuildingRecord.setId(id);
	        return buildingRecordRepository.save(newBuildingRecord);
	      });
	}
	
	@PutMapping("/buildingrecords/addowner")
	@JsonView(View.Summary.class)
	BuildingRecord addOwnerToBuildingRecord(@Valid @RequestBody OwnerToBuildingRecord ownerToBuildingRecord) {
		BuildingRecord record =  buildingRecordRepository.findById(ownerToBuildingRecord.getBuildingRecordId())
				.orElseThrow(() -> new BuildingRecordNotFoundException(ownerToBuildingRecord.getBuildingRecordId()));
		
		Owner owner = ownerRepository.findById(ownerToBuildingRecord.getOwnerId())
				.orElseThrow(() -> new OwnerNotFoundException(ownerToBuildingRecord.getOwnerId()));
		
		record.setOwner(owner);
	        return buildingRecordRepository.save(record);	      
	}
	
	@PutMapping("/buildingrecords/addpropertytype")
	@JsonView(View.Summary.class)
	BuildingRecord addPropertyTypeToBuildingRecord(@Valid @RequestBody PropertyTypeToBuildingRecord propertyTypeToBuildingRecord) {
		BuildingRecord record =  buildingRecordRepository.findById(propertyTypeToBuildingRecord.getBuildingRecordId())
				.orElseThrow(() -> new BuildingRecordNotFoundException(propertyTypeToBuildingRecord.getBuildingRecordId()));
		
		PropertyType propertyType = propertyTypeRepository.findById(propertyTypeToBuildingRecord.getPropertyTypeId())
				.orElseThrow(() -> new PropertyTypeNotFoundException(propertyTypeToBuildingRecord.getPropertyTypeId()));
		
		record.setPropertyType(propertyType);
	        return buildingRecordRepository.save(record);	      
	}
	
	@DeleteMapping("/buildingrecords/{id}")
	void deleteBuildingRecords(@PathVariable Long id) {
		buildingRecordRepository.deleteById(id);
	}

}
