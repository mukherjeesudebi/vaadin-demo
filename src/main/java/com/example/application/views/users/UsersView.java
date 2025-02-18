package com.example.application.views.users;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.example.application.data.SamplePerson;
import com.example.application.services.SamplePersonService;
import com.example.application.views.ViewLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@PageTitle("Users")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.USER)
@Uses(Icon.class)
public class UsersView extends ViewLayout {

	private Grid<SamplePerson> grid;
	private final SamplePersonService samplePersonService;

	public UsersView(SamplePersonService SamplePersonService) {
		this.samplePersonService = SamplePersonService;
		setSizeFull();
	}

	public Component renderComponent() {
		grid = new Grid<>(SamplePerson.class, false);
		grid.setHeightFull();
		grid.setAllRowsVisible(false);
		grid.addColumn("firstName").setAutoWidth(true);
		grid.addColumn("lastName").setAutoWidth(true);
		grid.addColumn("email").setAutoWidth(true);
		grid.addColumn("phone").setAutoWidth(true);
		grid.addColumn("dateOfBirth").setAutoWidth(true);
		grid.addColumn("occupation").setAutoWidth(true);
		grid.addColumn("role").setAutoWidth(true);

		grid.setItems(query -> samplePersonService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());

		return grid;
	}

}
