package com.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Border;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

abstract public class ViewLayout extends Div {
	private static final long serialVersionUID = 1L;
	
	public ViewLayout() {
		addClassNames(Padding.XLARGE);

		var commonLayout = new Div();		
		commonLayout.addClassNames(Border.ALL, BorderRadius.LARGE, Padding.XLARGE, Height.FULL);
		
		var titleLayout = new Div();
		titleLayout.add(new H3(viewTitle()), addButton());
		titleLayout.addClassNames(Display.FLEX,JustifyContent.BETWEEN,AlignItems.CENTER,Padding.MEDIUM);
		
		commonLayout.add(titleLayout,renderComponent());
		add(commonLayout);
	}

	abstract public Component renderComponent();
	
	abstract public String viewTitle();
	
	abstract public Button addButton();
}
