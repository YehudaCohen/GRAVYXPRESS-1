// This file defines the Task class which is used to perform
// specific tasks. All three interfaces use this class to
// perform tasks.

package models;

import java.util.*;

// To produce random numbers for the order ID.
import java.util.Random;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Task extends Model {
	// ID attribute for lists. This is required to save information
	// in system database.
	@Id
	public Long id;
	
	// Required to print the order item name.
	@Required
	public String label;
	
	// The order ID number
	public int orderID;
	
	// To decide when to remove the "Transfer to Kitchen Queue" button.
	public Boolean isFetched;
	
	// Function to find the requested elements from the List.
	public static Finder<Long,Task> find = new Finder(
		Long.class, Task.class
	);
	
	// Function to get all elements from the list.
	public static List<Task> all() {
		return find.all();
	}
	
	// Function to save order item, generate an order ID, and initialize isFetched.
	public static void create(Task task) {
		// Initializes the isFetched attribute to suggest that the order item
		// has not yet been moved to the Kitchen Queue.
		task.isFetched = false;
		
		// Generate random number between 1 and 1000000 for the order ID number
		// and to virtually eliminate repetition.
		Random num = new Random();
		task.orderID = 1 + num.nextInt(1000000);
		
		// Save order item, order id, and isFetched.
		task.save();
	}
	
	// This is to delete an item from the database.
	public static void delete(Long id) {
		find.ref(id).delete();
	}
	
	// This is to get an item from the database.
	public static Task get(Long id) {
		return find.ref(id);
	}
	
	// This is to save the status of the isFetched attribute into database.
	public static void saveThis(Task fetch) {
		fetch.save();
	}
	
	// Forms the message to be displayed to the waiter.
	public static String signalWaiter(Long id) {
		Task ready = Task.get(id);
		String message = "Order " + Integer.toString(ready.orderID) + " " + ready.label + " Ready! Please pick up.";
		return message;
	}
}