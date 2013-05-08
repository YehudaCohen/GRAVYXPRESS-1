// This file defines 2 global ArrayList which will be accessed by any file.

package controllers;

import java.util.*;
import play.*;
import play.mvc.*;
import play.data.*;
import models.*;

import views.html.*;

public class Global extends Controller{
	// Used to move an order from the Order Queue to the Kitchen Queue.
	public static ArrayList<Task> kitchenOrders = new ArrayList<Task>();
	
	// Used to store the message formed to display to the waiter.
	public static ArrayList<String> waiter = new ArrayList<String>();
}