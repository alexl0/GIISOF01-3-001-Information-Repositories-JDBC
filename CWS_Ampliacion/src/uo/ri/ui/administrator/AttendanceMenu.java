package uo.ri.ui.administrator;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class AttendanceMenu extends BaseMenu {

	public AttendanceMenu() {
		menuOptions = new Object[][] {
			{"Manager > Training management > Attendance", null},

			//{ "Register", 			RegisterAttendanceAction.class },
			//{ "Remove", 			RemoveAttendanceAction.class },
			//{ "List attendance",	ListAttendanceToCourseAction.class },
			
			{ "Register", 			NotYetImplementedAction.class },
			{ "Remove", 			NotYetImplementedAction.class },
			{ "List attendance",	NotYetImplementedAction.class },
		};
	}

}
