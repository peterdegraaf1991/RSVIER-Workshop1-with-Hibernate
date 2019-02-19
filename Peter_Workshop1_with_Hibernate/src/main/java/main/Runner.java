package main;


import controller.DatabaseController;
import controller.LoginController;

public class Runner {
	public static int databaseOption;
	
	public static void main(String[] args) {

		DatabaseController databaseController = new DatabaseController();
		databaseController.clearDatabase();
		databaseController.initDatabase();

		LoginController loginController = new LoginController();
		loginController.runController();
	}
}

