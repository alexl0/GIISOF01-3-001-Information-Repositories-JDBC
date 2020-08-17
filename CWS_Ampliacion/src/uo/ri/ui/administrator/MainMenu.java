package uo.ri.ui.administrator;

import alb.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Administrator", null },
			{ "Mechanics management", 			MechanicsMenu.class }, 
			{ "Spare parts management", 			SparesMenu.class },
			{ "Vehicle types management", 	VehicleTypeMenu.class },
			{ "Training management", 	TrainingMenu.class },
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}
