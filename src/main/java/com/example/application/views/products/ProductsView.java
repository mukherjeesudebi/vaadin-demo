package com.example.application.views.products;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.example.application.data.SampleProduct;
import com.example.application.data.SampleProductDataProvider;
import com.example.application.services.SampleProductService;
import com.example.application.views.ViewLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Products")
@Route("products")
@Menu(order = 1, icon = LineAwesomeIconUrl.TH_LIST_SOLID)
@Uses(Icon.class)
public class ProductsView extends ViewLayout {

	private Crud<SampleProduct> crud;
	private String NAME = "name";
	private String CATEGORY = "category";
	private String EDIT_COLUMN = "vaadin-crud-edit-column";

	private Grid<SampleProduct> grid;
	private final SampleProductService sampleProductService;
	private final SampleProductDataProvider dataProvider;

	public ProductsView(@Autowired SampleProductService sampleProductService,
			@Autowired SampleProductDataProvider dataProvider) {
		this.sampleProductService = sampleProductService;
		this.dataProvider = dataProvider;
		setSizeFull();
	}

	public Component renderComponent() {
		crud = new Crud<>(SampleProduct.class, createEditor());

        setupGrid();
        setupDataProvider();

//		grid = new Grid<>(SampleProduct.class, false);
//		grid.setHeightFull();
//		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
//		grid.setAllRowsVisible(false);
//		grid.addColumn("name");
//		grid.addColumn("category");
//		grid.addColumn(new ComponentRenderer<>(person -> {
//			Div options = new Div();
//			Button detailsButton = new Button("Details");
//			detailsButton.addClassNames(Margin.Right.MEDIUM);
//			Button editButton = new Button("Edit", new Icon(VaadinIcon.EDIT));
//			options.add(detailsButton, editButton);
//			return options;
//		})).setHeader("Options").setAutoWidth(true);
//
//		grid.setItems(query -> sampleProductService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());

		return crud;
	}

	private CrudEditor<SampleProduct> createEditor() {
		TextField name = new TextField("Name");
		TextField category = new TextField("Category");
		FormLayout form = new FormLayout(name, category);

		Binder<SampleProduct> binder = new Binder<>(SampleProduct.class);
		binder.forField(name).asRequired().bind(SampleProduct::getName, SampleProduct::setName);
		binder.forField(category).asRequired().bind(SampleProduct::getName, SampleProduct::setCategory);

		return new BinderCrudEditor<>(binder, form);
	}

	private void setupGrid() {
		Grid<SampleProduct> grid = crud.getGrid();

		// Only show these columns (all columns shown by default):
		List<String> visibleColumns = Arrays.asList(NAME, CATEGORY, EDIT_COLUMN);
		grid.getColumns().forEach(column -> {
			String key = column.getKey();
			if (!visibleColumns.contains(key)) {
				grid.removeColumn(column);
			}
		});
	}

	private void setupDataProvider() {
		//SampleProductDataProvider dataProvider = new SampleProductDataProvider(this.sampleProductService);
		crud.setDataProvider(dataProvider);
		crud.addDeleteListener(deleteEvent -> dataProvider.delete(deleteEvent.getItem()));
		crud.addSaveListener(saveEvent -> dataProvider.persist(saveEvent.getItem()));
	}

	public String viewTitle() {
		return "Products";
	}

	public Button addButton() {
		return new Button("Add Product", new Icon(VaadinIcon.PLUS));
	}

}
