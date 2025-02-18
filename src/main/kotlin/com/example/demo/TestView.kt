package com.example.demo

import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route(value = "", layout = MainLayout::class)
class TestView : VerticalLayout() {

    init {
        this.setSizeFull()
        this.setId("test-view")
    }

}