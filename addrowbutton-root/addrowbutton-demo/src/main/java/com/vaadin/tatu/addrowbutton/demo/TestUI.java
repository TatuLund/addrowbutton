package com.vaadin.tatu.addrowbutton.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.tatu.addrowbutton.AddRowButton;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Field;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ProgressBarRenderer;

@Theme("valo")
@Push
@SuppressWarnings("serial")
public class TestUI extends UI {
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(widgetset = "com.vaadin.tatu.addrowbutton.demo.DemoWidgetSet", productionMode = false, ui = TestUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        final Grid grid = new Grid();

        initializeGrid(grid);
        grid.setWidth("100%");
        grid.setHeight("500px");

        AddRowButton<Order> button = new AddRowButton("Add row",grid,Order.class);
        layout.addComponent(button);

        layout.addComponent(grid);
                  
    }

    private void initializeGrid(final Grid grid) {

        /*
         * Let's use a full-featured container instead
         */

        BeanItemContainer<Order> orderContainer = createOrderContainer();
        grid.setContainerDataSource(orderContainer);

        /*
         * Changing the column order and adjusting column headers
         */

        grid.setColumnOrder("customer", "product", "orderAmount",
                "reservedAmount", "completePercentage", "priority",
                "customized", "orderTime");

        grid.getColumn("orderAmount").setHeaderCaption("Ordered");
        grid.getColumn("reservedAmount").setHeaderCaption("Reserved");
        grid.getColumn("completePercentage").setHeaderCaption("Complete");
        grid.getColumn("customized").setHeaderCaption("Custom");

        /*
         * Adjusting column sizes
         */

        grid.getColumn("customer").setMinimumWidth(200);
        grid.getColumn("product").setMinimumWidth(200);

        grid.getColumn("product").setLastFrozenColumn();
        
        grid.getColumn("completePercentage").setRenderer(
                new ProgressBarRenderer());

        grid.setEditorEnabled(true);
        grid.setFrozenColumnCount(0);

        grid.getColumn("completePercentage").setEditable(false);

        grid.getColumn("customized").getEditorField().setCaption("");
              
        Field<?> customerField = grid.getColumn("customer").getEditorField();
        customerField.setRequired(true);
        customerField.setRequiredError("Value is required");

    }
    
    private static BeanItemContainer<Order> createOrderContainer() {
        return OrderUtil.createOrderContainer();
    }
    
}
