package uo.ri.ui.manager.training.course.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseCrudService;
import uo.ri.ui.conf.Factory;

public class RemoveCourseAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Ask the user for data
		Long cId = Console.readLong("Course id");

		// Invoke the service
		CourseCrudService cs = Factory.service.forCourseCrudService();
		cs.deleteCourse( cId );

		// Show result
		Console.println("Course removed");
	}

}
