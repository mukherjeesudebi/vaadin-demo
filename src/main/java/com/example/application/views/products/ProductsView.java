package com.example.application.views.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.example.application.data.SampleProduct;
import com.example.application.data.SampleProductDataProvider;
import com.example.application.services.SampleProductService;
import com.example.application.views.ViewLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
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

	private static final Logger log = LoggerFactory.getLogger(ProductsView.class);
	private Grid<SampleProduct> grid;
	private final SampleProductService sampleProductService;
	private Dialog dialog;
	private SampleProduct selectedProduct;
	private Binder<SampleProduct> binder;

	public ProductsView(@Autowired SampleProductService sampleProductService,
			@Autowired SampleProductDataProvider dataProvider) {
		this.sampleProductService = sampleProductService;
		dialog = new Dialog();
		binder = new Binder<>(SampleProduct.class);
		createDialogLayout();
		setSizeFull();
	}

	public Component renderComponent() {
		grid = new Grid<>(SampleProduct.class, false);
		grid.setHeightFull();
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		grid.setAllRowsVisible(false);
		grid.addColumn("name");
		grid.addColumn("category");
		grid.addColumn(new ComponentRenderer<>(product -> {
			Div options = new Div();
			Button detailsButton = new Button("Details");
			detailsButton.addClickListener(event -> {
				if (!grid.isDetailsVisible(product)) {
					grid.setDetailsVisible(product, true);
				} else {
					grid.setDetailsVisible(product, false);
				}
			});
			detailsButton.addClassNames(Margin.Right.MEDIUM);
			Button editButton = new Button("Edit", new Icon(VaadinIcon.EDIT));
			editButton.addClickListener(event -> {
				selectedProduct = product;
				binder.readBean(selectedProduct);
				dialog.open();
			});
			options.add(detailsButton, editButton);
			return options;
		})).setHeader("Options").setAutoWidth(true);
		grid.setItemDetailsRenderer(new ComponentRenderer<>(product -> {
			VerticalLayout detailsLayout = new VerticalLayout();
			detailsLayout.add(new NativeLabel("Name: " + product.getName()));
			detailsLayout.add(new NativeLabel("Category: " + product.getCategory()));
			return detailsLayout;
		}));
		grid.setDetailsVisibleOnClick(false);
		grid.setItems(query -> sampleProductService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());
		
		return grid;
	}
	
	private void createDialogLayout() {
		dialog.setHeaderTitle("Edit user");

		TextField name = new TextField("Name");
		TextField category = new TextField("Category");

		binder.forField(name).asRequired().bind(SampleProduct::getName, SampleProduct::setName);
		binder.forField(category).asRequired().bind(SampleProduct::getCategory, SampleProduct::setCategory);

		VerticalLayout dialogLayout = new VerticalLayout(name, category);
		dialogLayout.setPadding(false);
		dialogLayout.setSpacing(false);
		dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
		dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
		dialog.add(dialogLayout);

		Button saveButton = new Button("Save", saveEvent -> {
			try {
				binder.writeBean(selectedProduct);
				var updatedProduct = sampleProductService.save(selectedProduct);
				dialog.close();
				grid.getDataProvider().refreshItem(updatedProduct);
			} catch (ValidationException e) {
				log.error(e.getMessage());
			}

		});
		Button cancelButton = new Button("Cancel", e -> {
			dialog.close();
		});
		dialog.getFooter().add(cancelButton);
		dialog.getFooter().add(saveButton);
		add(dialog);
	}

	public String viewTitle() {
		return "Products";
	}

	public Button addButton() {
		return new Button("Add Product", new Icon(VaadinIcon.PLUS));
	}

}
