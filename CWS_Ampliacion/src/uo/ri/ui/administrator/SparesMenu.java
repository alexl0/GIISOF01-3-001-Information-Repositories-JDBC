package uo.ri.ui.administrator;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class SparesMenu extends BaseMenu {

	public SparesMenu() {
		menuOptions = new Object[][] { 
			{"Manager > Spare parts management", null},

			{ "Add spare part", 				NotYetImplementedAction.class }, 
			{ "Update spare part", 	NotYetImplementedAction.class }, 
			{ "Delete spare part", 				NotYetImplementedAction.class }, 
			{ "List spare parts", 				NotYetImplementedAction.class },
		};
	}

}
