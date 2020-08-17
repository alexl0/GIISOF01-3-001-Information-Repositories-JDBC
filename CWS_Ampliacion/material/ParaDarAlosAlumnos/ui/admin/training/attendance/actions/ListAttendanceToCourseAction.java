package uo.ri.ui.manager.training.attendance.actions;

import java.util.List;

import alb.util.menu.Action;
import uo.ri.cws.application.dto.EnrollmentDto;
import uo.ri.cws.application.service.training.CourseAttendanceService;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListAttendanceToCourseAction implements Action {

	private AttendanceUserInteractor user = new AttendanceUserInteractor();

	@Override
	public void execute() throws Exception {
		Long cId = user.askForCourseId();

		CourseAttendanceService s = Factory.service.forCourseAttendanceService();
		List<EnrollmentDto> attendance = s.findAttendanceByCourseId( cId );

		attendance.forEach( att -> Printer.printAttendingMechanic(att) );
	}

}
