// This is the file that initiates and creates new tables for a restaurant.

package controllers;

// Load all Play functionalities.
import play.*;
import play.mvc.*;
import play.data.*;
import models.*;

// Handle rendering to HTML file.
import views.html.*;

public class Tables extends Controller {
	// This is to count for table numbers as a new table is created.
	static int tableNum = 0;
	
	// Render to html.scala file.
	public static Result tasks() {
		return ok(
			views.html.Tables.render(TableTask.all())
		);
	}
	
	// Creation of a new table.
	public static Result newTable() {
		// Update count of table, so it goes Table 1, Table 2, etc.
		tableNum++;
		
		// New table object with Task attributes defined in Task.java.
		TableTask table = new TableTask();
		TableTask.create(table, tableNum);
		
		// Return to tasks() for rendering.
		return redirect(routes.Tables.tasks());
	}
	
	// This is to delete a table.
	public static Result deleteTask(Long id) {
		TableTask.delete(id);
		return redirect(routes.Tables.tasks());
	}
	
	// What happens when the customer has chosen to sit at a table?
	public static Result sitHere(Long id) {
		// Create occupied object with Task attributes.
		TableTask occupied = TableTask.get(id);
		
		// isOccupied is initially 0 defined in create() function in Task.java.
		occupied.isOccupied = 1;
		
		// Table is now occupied.
		occupied.status = " OCCUPIED";
		
		// Save all of this into Play database.
		TableTask.saveThis(occupied);
		
		return redirect(routes.Tables.tasks());
	}
	
	// What happens when customer finished paying cheque and left the restaurant?
	public static Result customerLeft(Long id) {
		// Create "left" object with Task attributes.
		TableTask left = TableTask.get(id);
		
		// isOccupied is initially 1 from the sitHere function above.
		left.isOccupied = 2;
		
		// Table needs to be cleaned.
		left.status = " NEEDS CLEANING";
		
		// Save all of this into Play database.
		TableTask.saveThis(left);

		return redirect(routes.Tables.tasks());
	}
	
	// What happens when waiter finishes cleaning the table?
	public static Result finishedCleaning(Long id) {
		// Create "finCleaning" object with Task attributes.
		TableTask finCleaning = TableTask.get(id);
		
		// isOccupied is initially 2 from the customerLeft function above.
		finCleaning.isOccupied = 0;
		
		// Table is clean and ready for a new customer.
		finCleaning.status = " OPEN";
		
		// Save all of this into Play database.
		TableTask.saveThis(finCleaning);	

		return redirect(routes.Tables.tasks());
	}
	
	// This is when creating new tables, manager may want to start over from Table 1.
	public static Result resetCount() {
		tableNum = 0;
		return redirect(routes.Tables.tasks());
	}
}
