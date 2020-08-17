package uo.ri.ui.manager.training;

import alb.util.menu.BaseMenu;
import uo.ri.ui.manager.training.attendance.AttendanceMenu;
import uo.ri.ui.manager.training.certificates.GenerateCertificatesAction;
import uo.ri.ui.manager.training.course.CourseCrudMenu;
import uo.ri.ui.manager.training.reports.ReportsMenu;

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
