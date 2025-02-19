package com.example.application.services;

import java.io.Serializable;
import java.util.function.Function;

import com.example.application.data.entity.AbstractEntity;

public interface EntityProvider<DTO, ENTITY extends AbstractEntity> extends Function<DTO, ENTITY>, Serializable {

	@Override
	ENTITY apply(DTO dto);

}
