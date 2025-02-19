package com.example.application.services;

import org.atmosphere.inject.annotation.ApplicationScoped;
import org.springframework.stereotype.Component;

import com.example.application.data.dto.SampleProductDTO;
import com.example.application.data.entity.SampleProduct;

@ApplicationScoped
@Component
public class SampleProductDTOConverter implements DTOConverter<SampleProduct, SampleProductDTO> {

	@Override
	public SampleProductDTO convertToDTO(SampleProduct entity) {
		if (entity == null) {
			return null;
		}
		SampleProductDTO dto = new SampleProductDTO();
		dto.setId(entity.getId());
		dto.setConsistencyVersion(entity.getConsistencyVersion());
		dto.setName(entity.getName());
		dto.setCategory(entity.getCategory());
		return dto;
	}

	@Override
	public SampleProduct convertToEntity(EntityProvider<SampleProductDTO, SampleProduct> entityProvider,
			SampleProductDTO dto) {
		if (dto == null) {
			return null;
		}
		SampleProduct entity = new SampleProduct();
		entity.setId(dto.getId());
		entity.setConsistencyVersion(dto.getConsistencyVersion());
		entity.setName(dto.getName());
		entity.setCategory(dto.getCategory());
		return entity;
	}

}
