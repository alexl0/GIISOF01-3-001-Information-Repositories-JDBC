package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Foreman", null },
			{ "Client reception ", 		ClientReceptionsMenu.class }, 
			{ "Client management", 		ClientsMenu.class },
			{ "Vehicle management", 	VehiclesMenu.class },
			{ "Client history review", 	NotYetImplementedAction.class }, 
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}
