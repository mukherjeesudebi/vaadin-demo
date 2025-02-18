package com.example.demo

import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

@CssImport("./styles.css")
class MainLayout : AppLayout() {

    init {
        addToNavbar(getHeader())
        addToDrawer(getSideNav())

        element.appendChild(
            DrawerToggle().apply {
                this.setId("elementToggle")
            }.element
        )

    }

    private fun getHeader() = HorizontalLayout().apply {
        add(H1("Demo Application"))
        add(DrawerToggle().apply { this.setId("headerToggle") })

        this.setWidthFull()
        this.setId("header")

    }

    private fun getSideNav() = VerticalLayout().apply {

        add(DrawerToggle().apply {this.setId("sideToggle")})

        this.setWidthFull()
        this.setId("sideNav")

    }
}