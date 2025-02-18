package com.example.application.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.application.data.SampleProduct;
import com.example.application.data.SampleProductRepository;

@Service
public class SampleProductService {

    private final SampleProductRepository repository;

    public SampleProductService(SampleProductRepository repository) {
        this.repository = repository;
    }

    public Optional<SampleProduct> get(Long id) {
        return repository.findById(id);
    }

    public SampleProduct save(SampleProduct entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<SampleProduct> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<SampleProduct> list(Pageable pageable, Specification<SampleProduct> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }



}
