// This file defines all attributes of all Task objects created.

package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class TableTask extends Model {
	// A unique id number for each block of data in the database. This is always required
	// in order to get anything done.
	@Id
	public Long id;
	
	// This is to place the string "Table " for the list of tables.
	public String tableName;
	
	// This is the table number obtained from the static int variable from Tables.java
	public int tabNum;
	
	// Defines the status of table.
	public String status;
	
	// Is the table free (0), occupied (1), or needs cleaning (2)
	public int isOccupied;
	
	// Find all data in the database.
	public static Finder<Long,TableTask> find = new Finder(
		Long.class, TableTask.class
	);
	
	// Get all data in the List data structure.
	public static List<TableTask> all() {
		return find.all();
	}
	
	// The process of creating a table.
	public static void create(TableTask task, int tableNum) {
		// Put the name of the table.
		task.tableName = "Table ";
		
		// Put table number.
		task.tabNum = tableNum;
		
		// Put status, initially open until customer occupies it.
		task.status = " OPEN";
		
		// Initially status is free. See above to see what the number means.
		task.isOccupied = 0;
		
		// Save this all to the database.
		task.save();
	}
	
	// Delete the selected data with the unique id from database.
	public static void delete(Long id) {
		find.ref(id).delete();
	}
	
	// Get the data with the unique id.
	public static TableTask get(Long id) {
		return find.ref(id);
	}
	
	// Save any other objects to database if attributes are updated outside of this file.
	public static void saveThis(TableTask task) {
		task.save();
	}
}