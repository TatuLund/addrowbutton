package com.vaadin.tatu.addrowbutton;

/*
 * This is AddRowButton for use with Grid
 * 
 * Use case: Create a new item to grid as a new row, and edit it. If user hits cancel
 *  button, delete the row and cleanup. Allow adding one row at the time.
 * 
 * Limitations
 * - Currently works with BeanItemContainer, since ItemSetChangeListener is needed
 * - Requires Vaadin Push to be enabled, since solution uses threads
 * 
 * Usage
 * - Create button 
 * 
 *  AddRowButton<MyBean> button = new AddRowButton(String caption, Grid grid,Class<?> MyBean.class);
 *  
 *  caption: 	the caption of the button
 *  MyBean:		the beantype of your bean
 *  grid:		the grid you use this utility with
 * 
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.vaadin.data.Container;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

public class AddRowButton<BEANTYPE> extends Button {

	Boolean polling = false;
	Object editedItemId = null;
	String originalBeanItem = null;
	Object itemId = null;

	private static final ScheduledExecutorService worker = Executors
			.newSingleThreadScheduledExecutor();

	/**
	 * 
	 * @param caption	Caption for the button
	 * @param grid		Grid where AddRowButton is applied
	 * @param beanClass	Class of the bean
	 */
	public AddRowButton(String caption, final Grid grid,
			final Class<?> beanClass) {
		super(caption);

		final BeanItemContainer<BEANTYPE> container = (BeanItemContainer<BEANTYPE>) grid
				.getContainerDataSource();

		// Add listener for the button
		addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (!grid.isEditorActive()) {
					try {
						itemId = beanClass.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					grid.getContainerDataSource().addItem(itemId);
					// Store original data to string for later comparison
					originalBeanItem = new BeanItem<BEANTYPE>((BEANTYPE) itemId).toString();
					grid.editItem(itemId);
					setEnabled(false);
				}
			}

		});

		setupItemChangeListener(grid, container);

	}

	private void setupItemChangeListener(final Grid grid,
			BeanItemContainer<BEANTYPE> container) {
		container.addItemSetChangeListener(new ItemSetChangeListener() {

			@Override
			public void containerItemSetChange(
					Container.ItemSetChangeEvent event) {
				if (grid.isEditorEnabled() && !polling) {
					polling = true;
					// Create thread which polls if user is still editing
					Thread t = new Thread() {
						public void run() {
							while (grid.isEditorActive()) {
								editedItemId = grid.getEditedItemId();
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							// Create runnable which updates the grid once polling ends
							// Vaadin Push needs to be enabled in order to work
							getUI().getCurrent().access(new Runnable() {
								public void run() {
									String beanItem = new BeanItem<BEANTYPE>(
											(BEANTYPE) editedItemId).toString();
									// Check if bean was changed due editing, if not delete the row
									if (beanItem.equals(originalBeanItem)) {
										grid.getContainerDataSource()
												.removeItem(editedItemId);
										setEnabled(true);
										editedItemId = null;
									} else {
										setEnabled(true);
									}
								}
							});
							polling = false;
						}
					};
					// Start the thread process
					// Timeout is needed for editor opening to complete
					worker.schedule(t, 500, TimeUnit.MILLISECONDS);
				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (!isEnabled())
						setEnabled(true);
				}
			}
		});
	}

}
