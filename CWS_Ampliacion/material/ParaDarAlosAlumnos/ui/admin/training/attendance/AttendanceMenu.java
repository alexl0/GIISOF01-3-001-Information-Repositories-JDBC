package uo.ri.ui.manager.training.attendance;

import alb.util.menu.BaseMenu;
import uo.ri.ui.manager.training.attendance.actions.ListAttendanceToCourseAction;
import uo.ri.ui.manager.training.attendance.actions.RegisterAttendanceAction;
import uo.ri.ui.manager.training.attendance.actions.RemoveAttendanceAction;

public class AttendanceMenu extends BaseMenu {

	public AttendanceMenu() {
		menuOptions = new Object[][] {
			{"Manager > Training management > Attendance", null},

			{ "Register", 			RegisterAttendanceAction.class },
			{ "Remove", 			RemoveAttendanceAction.class },
			{ "List attendance",	ListAttendanceToCourseAction.class },
		};
	}

}
