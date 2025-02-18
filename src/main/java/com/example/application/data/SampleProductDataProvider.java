package com.example.application.data;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.services.SampleProductService;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@Component
public class SampleProductDataProvider extends AbstractBackEndDataProvider<SampleProduct, CrudFilter> {
	private final SampleProductService sampleProductService;

	public SampleProductDataProvider(@Autowired SampleProductService sampleProductService) {
		this.sampleProductService = sampleProductService;
	}

	@Override
	protected Stream<SampleProduct> fetchFromBackEnd(Query<SampleProduct, CrudFilter> query) {
		return sampleProductService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream();
	}

	@Override
	protected int sizeInBackEnd(Query<SampleProduct, CrudFilter> query) {
		long count = fetchFromBackEnd(query).count();
        return (int) count;
	}

	public void persist(SampleProduct item) {
		sampleProductService.save(item);
    }

    public void delete(SampleProduct item) {
    	sampleProductService.delete(item.getId());
    }
}
