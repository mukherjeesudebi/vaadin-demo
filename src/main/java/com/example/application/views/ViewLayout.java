package com.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility.Border;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

abstract public class ViewLayout extends Div {
	private static final long serialVersionUID = 1L;

	public ViewLayout() {
		addClassNames(Padding.XLARGE);

		var commonLayout = new Div();
		commonLayout.addClassNames(Border.ALL, BorderRadius.LARGE, Padding.XLARGE, Height.FULL);
		commonLayout.add(renderComponent());
		add(commonLayout);
	}

	abstract public Component renderComponent();
}
