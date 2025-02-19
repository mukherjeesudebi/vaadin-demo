package com.example.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.application.data.dto.SampleProductDTO;
import com.example.application.data.entity.SampleProduct;
import com.example.application.repository.SampleProductRepository;

@Service
public class SampleProductService {

	private final SampleProductRepository repository;
	private final SampleProductDTOConverter sampleProductDTOConverter;

	public SampleProductService(@Autowired SampleProductRepository repository,
			@Autowired SampleProductDTOConverter sampleProductDTOConverter) {
		this.repository = repository;
		this.sampleProductDTOConverter = sampleProductDTOConverter;
	}

	public Optional<SampleProductDTO> get(Long id) {
		return repository.findById(id).map(sampleProductDTOConverter::convertToDTO);
	}

	public SampleProductDTO save(SampleProductDTO dto) {
		return sampleProductDTOConverter.convertToDTO(
				repository.saveAndFlush(sampleProductDTOConverter.convertToEntity(this::findEntity, dto)));
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<SampleProductDTO> list(Pageable pageable) {
		return repository.findAll(pageable).map(sampleProductDTOConverter::convertToDTO);
	}

	public int count() {
		return (int) repository.count();
	}

	private SampleProduct findEntity(SampleProductDTO item) {
		if (item.getId() == null) {
			return new SampleProduct();
		}
		Optional<SampleProduct> existingEntity = repository.findById(item.getId());
		if (existingEntity.isEmpty()) {
			throw new RuntimeException("Attempt to modify an entity that does not exist");
		}
		return existingEntity.get();
	}
}
