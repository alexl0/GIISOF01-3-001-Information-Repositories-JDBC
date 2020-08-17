package uo.ri.ui.administrator;

import alb.util.menu.BaseMenu;
import uo.ri.ui.administrator.action.GenerateCertificatesAction;

public class TrainingMenu extends BaseMenu {

	public TrainingMenu() {
		menuOptions = new Object[][] {
			{"Manager > Training management", null},

			{ "Course management", 			CourseCrudMenu.class },
			{ "Attendance registration", 	AttendanceMenu.class },
			{ "Reports", 					ReportsMenu.class },
			{ "", null },
			{ "Certificate generation", 	GenerateCertificatesAction.class },

		};
	}

}
