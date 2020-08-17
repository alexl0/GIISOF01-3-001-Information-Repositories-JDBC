package uo.ri.ui.manager.training.attendance.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.dto.EnrollmentDto;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseAttendanceService;
import uo.ri.ui.conf.Factory;

public class RegisterAttendanceAction implements Action {

	private AttendanceUserInteractor user = new AttendanceUserInteractor();

	@Override
	public void execute() throws BusinessException {

		// Ask the user for data
		EnrollmentDto att = new EnrollmentDto();
		user.fill( att );

		// Invoke the service
		CourseAttendanceService cs = Factory.service.forCourseAttendanceService();
		cs.registerNew( att );

		// Show result
		Console.println("Attendance registered:" + att.id);
	}

}
