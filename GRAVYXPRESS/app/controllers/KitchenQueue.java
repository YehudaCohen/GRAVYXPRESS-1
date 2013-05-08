// This is where the functionality of the Kitchen Queue is defined. When a task is performed/requested
// this file will access the Task.java file to execute specific tasks. The results of those tasks 
// are collected here and then routed to the Kitchen.scala.html interface.

package controllers;

// This is to use the ArrayList data structure.
import java.util.*;

// Import all Play Framework functionalities.
import play.*;
import play.mvc.*;
import play.data.*;
import models.*;

// This is to allow rendering to the scala.html file.
import views.html.*;

public class KitchenQueue extends Controller {
	// If program is ever routed to this function, then just re-route to tasks() function.
    public static Result Kitchen() {
        return redirect(routes.KitchenQueue.tasks());
    }
	
	// This will take the resulting List data structure and ArrayList data structure
	// and render them to the Kitchen.scala.html file.
	public static Result tasks() {
		return ok(views.html.Kitchen.render(Task.all(), Global.kitchenOrders));
	}
	
	// When the "Complete" button is pressed in the Kitchen Interface, two things happen.
	// One of those events is to signal the waiter in a different interface to come and pick
	// up the order. This function will render all the completed orders stored in another
	// ArrayList to the waiter.scala.html interface.
	public static Result waiters() {
		return ok(views.html.waiter.render(Global.waiter));
	}
	
	// This function is used to define the "Transfer to Kitchen Queue" button.
	public static Result moveTask(Long id) {
		// Get the order item with the corresponding button id. get(id) defined in Task.java
		Task tomove = Task.get(id);
		
		// Make the Boolean attribute true (initially false before botton is pressed). The
		// Kitchen.scala.html will remove the button after it has been pressed.
		tomove.isFetched = true;
		// Save the new value of isFetched attribute. Defined in Task.java.
		Task.saveThis(tomove);
		
		// Copy the order from Order Queue into this ArrayList data structure.
		Global.kitchenOrders.add(tomove);
		
		// Go to the rendering function to send results to the scala.html file.
		return redirect(routes.KitchenQueue.tasks());
	}
	
	// This function defines the functionality of the "Complete" button.
	public static Result completeTask(Long id) {
		// Get the message for the waiter using the signalWaiter function (defined in Task.java)
		String message = Task.signalWaiter(id);
		
		// Add this message to another ArrayList defined global.
		Global.waiter.add(message);
		
		// Then delete the order from the Order Queue and from Kitchen Queue.
		Task.delete(id);
		Global.kitchenOrders.remove(Task.get(id));
		
		// Redirect to waiter's interface to send the information.
		return redirect(routes.KitchenQueue.tasks());
	}
	
	// This defines the functionality of the "Acknowledged" button in the Waiter interface.
	public static Result acknowledge(String message) {
		// Search through the ArrayList. If program finds the message, delete it from the list.
		int i;
		for (i = 0; i < Global.waiter.size(); i++) {
			if (Global.waiter.get(i).equals(message)) {
				Global.waiter.remove(i);
			}
		}
		return redirect(routes.KitchenQueue.waiters());
	}
}
