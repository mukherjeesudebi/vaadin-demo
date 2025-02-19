package com.example.application.views.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.example.application.data.dto.SamplePersonDTO;
import com.example.application.services.SamplePersonService;
import com.example.application.views.ViewLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
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
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

@PageTitle("Users")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.USER)
@Uses(Icon.class)
public class UsersView extends ViewLayout {

	private static final Logger log = LoggerFactory.getLogger(UsersView.class);
	private Grid<SamplePersonDTO> grid;
	private final SamplePersonService samplePersonService;
	private Dialog dialog;
	private SamplePersonDTO selectedPerson;
	private Binder<SamplePersonDTO> binder;

	public UsersView(SamplePersonService SamplePersonService) {
		this.samplePersonService = SamplePersonService;
		dialog = new Dialog();
		binder = new Binder<>(SamplePersonDTO.class);
		createDialogLayout();
		setSizeFull();
	}

	public Component renderComponent() {
		grid = new Grid<>(SamplePersonDTO.class, false);
		grid.setHeightFull();
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		grid.setAllRowsVisible(false);
		grid.addColumn(new ComponentRenderer<>(person -> {
			Div firstNameWithAvatar = new Div();
			firstNameWithAvatar.addClassNames(Display.FLEX, AlignItems.CENTER);
			Avatar avatarImage = new Avatar();
			avatarImage.setImage(person.getImg());
			Div firstName = new Div(person.getFirstName());
			firstName.addClassNames(Padding.Left.MEDIUM);
			firstNameWithAvatar.add(avatarImage, firstName);
			return firstNameWithAvatar;
		})).setHeader("firstName").setAutoWidth(true);
		grid.addColumn("lastName");
		grid.addColumn("email");
		grid.addColumn(new ComponentRenderer<>(person -> {
			Div options = new Div();
			Button detailsButton = new Button("Details");
			detailsButton.addClickListener(event -> {
				if (!grid.isDetailsVisible(person)) {
					grid.setDetailsVisible(person, true);
				} else {
					grid.setDetailsVisible(person, false);
				}
			});
			detailsButton.addClassNames(Margin.Right.MEDIUM);
			Button editButton = new Button("Edit", new Icon(VaadinIcon.EDIT));
			editButton.addClickListener(event -> {
				selectedPerson = person;
				binder.readBean(selectedPerson);
				dialog.open();
			});
			options.add(detailsButton, editButton);
			return options;
		})).setHeader("Options").setAutoWidth(true);

		grid.setItems(query -> samplePersonService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());
		grid.setItemDetailsRenderer(new ComponentRenderer<>(person -> {
			VerticalLayout detailsLayout = new VerticalLayout();
			detailsLayout.add(new NativeLabel("First Name: " + person.getFirstName()));
			detailsLayout.add(new NativeLabel("Last Name: " + person.getLastName()));
			return detailsLayout;
		}));
		grid.setDetailsVisibleOnClick(false);
		grid.setSelectionMode(SelectionMode.MULTI);
		return grid;
	}

	private void createDialogLayout() {
		dialog.setHeaderTitle("Edit user");

		TextField firstNameField = new TextField("First Name");
		TextField lastNameField = new TextField("Last Name");

		binder.forField(firstNameField).asRequired().bind(SamplePersonDTO::getFirstName, SamplePersonDTO::setFirstName);
		binder.forField(lastNameField).asRequired().bind(SamplePersonDTO::getLastName, SamplePersonDTO::setLastName);

		VerticalLayout dialogLayout = new VerticalLayout(firstNameField, lastNameField);
		dialogLayout.setPadding(false);
		dialogLayout.setSpacing(false);
		dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
		dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");
		dialog.add(dialogLayout);

		Button saveButton = new Button("Save", saveEvent -> {
			try {
				binder.writeBean(selectedPerson);
				var updatedPerson = samplePersonService.save(selectedPerson);
				dialog.close();
				grid.getDataProvider().refreshItem(updatedPerson);
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
		return "Users";
	}

	public Button addButton() {
		Button button = new Button("Add User", new Icon(VaadinIcon.PLUS));
		button.addThemeName("add-items-button");
		return button;
	}

}
