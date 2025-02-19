package com.example.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.application.data.dto.SamplePersonDTO;
import com.example.application.data.entity.SamplePerson;
import com.example.application.repository.SamplePersonRepository;

@Service
public class SamplePersonService {

	private final SamplePersonRepository repository;
	private final SamplePersonDTOConverter samplePersonDTOConverter;

	public SamplePersonService(@Autowired SamplePersonRepository repository,
			@Autowired SamplePersonDTOConverter samplePersonDTOConverter) {
		this.repository = repository;
		this.samplePersonDTOConverter = samplePersonDTOConverter;
	}

	public Optional<SamplePersonDTO> get(Long id) {
		return repository.findById(id).map(samplePersonDTOConverter::convertToDTO);
	}

	public SamplePersonDTO save(SamplePersonDTO dto) {
		return samplePersonDTOConverter
				.convertToDTO(repository.saveAndFlush(samplePersonDTOConverter.convertToEntity(this::findEntity, dto)));
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<SamplePersonDTO> list(Pageable pageable) {
		return repository.findAll(pageable).map(samplePersonDTOConverter::convertToDTO);
	}


	public int count() {
		return (int) repository.count();
	}
	
	private SamplePerson findEntity(SamplePersonDTO item) {
		if (item.getId() == null) {
			return new SamplePerson();
		}
		Optional<SamplePerson> existingEntity = repository.findById(item.getId());
		if (existingEntity.isEmpty()) {
			throw new RuntimeException("Attempt to modify an entity that does not exist");
		}
		return existingEntity.get();
	}

}
