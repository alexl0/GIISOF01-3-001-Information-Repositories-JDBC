package uo.ri.ui.administrator;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.ui.administrator.action.ListCertificationsByVehicleTypeAction;

public class ReportsMenu extends BaseMenu {

	public ReportsMenu() {
		menuOptions = new Object[][] {
			{ "Manager > Training management > Reports", null },

			{ "Training of a mechanic",
				NotYetImplementedAction.class },
			{ "Training by vehicle types",
					NotYetImplementedAction.class },
			{ "Certifications by vehicle type",
						ListCertificationsByVehicleTypeAction.class } 
		};
	}

}
