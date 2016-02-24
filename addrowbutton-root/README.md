add-row-button
==============

Vaadin 7 Grid does not yet have editor event. This is a small short coming
if you want to create button to add a row to your grid with the following
logic

1. Click button to add a row
2. Activate editor on the new row
3. Delete row if user selected to click cancel instead of save

Hence I wrote this experimental add-on, which encapsulates needed logic
in form of extended Vaadin Button. 

Notes
 * Works currently only with BeanItemContainer
 * Uses threads, hence Push needs to be enabled
 * Beans used with AddRowButton must have default constructor without 
   parameters in order to intanstiate
 * Doesn't work with Vaadin 7.6.1 - 7.6.2 due layout bug in Editor
 * Doesn't work with Vaadin 7.5.4 and older due bug Grid row book 
   keeping

Status of this utility is experimental. I hope this is also useful demo
case how to work with Vaadin using threads.

### Usage:

 Setup the Grid first.
 
 Usage
 - Create new AddRowButton using its constructor. 
 
  AddRowButton<MyBean> button = new AddRowButton(String caption, Grid grid,Class<?> MyBean.class);
  
  caption: 	the caption of the button
  MyBean:	the beantype of your bean
  grid:		the grid you use this utility with
 

### License

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

AddRowButton is written by Tatu Lund

