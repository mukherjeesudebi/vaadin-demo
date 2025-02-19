package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.application.data.entity.SampleProduct;

public interface SampleProductRepository
		extends JpaRepository<SampleProduct, Long>, JpaSpecificationExecutor<SampleProduct> {

}
