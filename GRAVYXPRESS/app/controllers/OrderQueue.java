// This is where the functionality of the Order Queue is defined. When a task is performed/requested
// this file will access the Task.java file to execute specific tasks. The results of those tasks 
// are collected here and then routed to the orderQueue.scala.html interface.

package controllers;

import java.util.*;

// Import all Play Framework functionalities.
import play.*;
import play.mvc.*;
import play.data.*;
import models.*;

// This is to allow rendering to the scala.html file.
import views.html.*;

public class OrderQueue extends Controller {
	// A form is created in order to create the textbox and take input.
	static Form<Task> taskForm = Form.form(Task.class);
	
	// If program is ever routed to this function, then just re-route to tasks() function.
    public static Result orderQueue() {
        return redirect(routes.OrderQueue.tasks());
    }
	
	// This will take the resulting List data structure and the form for the textbox
	// and render them to the orderQueue.scala.html file.
	public static Result tasks() {
		return ok(
			views.html.orderQueue.render(Task.all(), taskForm)
		);
	}
	
	// When something is input into the textbox, the first thing the program does
	// is start a new task. This is almost like the main() method in Java and C/C++.
	// The routes file will direct the program here.
	public static Result newTask() {
		// This is to bind the data, entered in the textbox, to a Form vairable of type
		// task.
		Form<Task> filledForm = taskForm.bindFromRequest();
		if(filledForm.hasErrors()) {
			// If the form variable has errors, then do not proceed further. Redirect the
			// data back to the scala.html file.
			return badRequest(
				views.html.orderQueue.render(Task.all(), filledForm)
			);
		} else {
			// If no errors, then get the data from the textbox and call the create function
			// (defined in Task.java) to create save the data into a List.
			Task.create(filledForm.get());
			
			//Then, return to tasks() to send the information back to the scala.html
			return redirect(routes.OrderQueue.tasks());
		}
	}
	
	// This is to define the "Delete" button in the Order Queue. Every time a button is pressed,
	// a unique integer id is generated. Use this id to call the delete function (defined in Task.java)
	// and return the results to the scala.html file.
	public static Result deleteTask(Long id) {
		Task.delete(id);
		return redirect(routes.OrderQueue.tasks());
	}
	
}