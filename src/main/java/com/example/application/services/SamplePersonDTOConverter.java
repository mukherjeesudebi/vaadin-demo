package com.example.application.services;

import org.atmosphere.inject.annotation.ApplicationScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.data.dto.SamplePersonDTO;
import com.example.application.data.entity.SamplePerson;
import com.example.application.repository.SamplePersonRepository;

@ApplicationScoped
@Component
public class SamplePersonDTOConverter implements DTOConverter<SamplePerson, SamplePersonDTO> {

	@Override
	public SamplePersonDTO convertToDTO(SamplePerson entity) {
		if (entity == null) {
			return null;
		}
		SamplePersonDTO dto = new SamplePersonDTO();
		dto.setId(entity.getId());
		dto.setConsistencyVersion(entity.getConsistencyVersion());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setEmail(entity.getEmail());
		dto.setOccupation(entity.getOccupation());
		dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setImg(entity.getImg());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        dto.setImportant(entity.isImportant());
		return dto;
	}

	@Override
	public SamplePerson convertToEntity(EntityProvider<SamplePersonDTO, SamplePerson> entityProvider,
			SamplePersonDTO dto) {
		if (dto == null) {
            return null;
        }
		SamplePerson entity = entityProvider.apply(dto);
        entity.setId(dto.getId());
        entity.setConsistencyVersion(dto.getConsistencyVersion());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());  
        entity.setOccupation(dto.getOccupation());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setImg(dto.getImg());
        entity.setPhone(dto.getPhone());
        entity.setRole(dto.getRole());
        entity.setImportant(dto.isImportant());        
        return entity;
	}

}
