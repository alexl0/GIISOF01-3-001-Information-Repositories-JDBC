package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class ClientReceptionsMenu extends BaseMenu {

	public ClientReceptionsMenu() {
		menuOptions = new Object[][] { 
			{"Foreman > Check in menu", null},
			
			{"Add breakdown", 			NotYetImplementedAction.class }, 
			{"Update breakdown", 		NotYetImplementedAction.class },
			{"Delete breakdown", 		NotYetImplementedAction.class },
			{"", null},
			{"List breakdown", 			NotYetImplementedAction.class }, 
			{"Check a breakdown", 		NotYetImplementedAction.class },
			{"", null},
			{"List mechanics", 			NotYetImplementedAction.class }, 
			{"Assign a breakdown",  	NotYetImplementedAction.class },
		};
	}

}
