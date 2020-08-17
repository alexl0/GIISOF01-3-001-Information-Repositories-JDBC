package uo.ri.ui.manager.training.course.actions;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.dto.CourseDto;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseCrudService;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListCoursesAction implements Action {

	@Override
	public void execute() throws BusinessException {

		CourseCrudService as = Factory.service.forCourseCrudService();
		List<CourseDto> courses = as.findAllCourses();

		Console.println("\nList of courses\n");
		for(CourseDto c : courses) {
			Printer.printCourse( c );
		}

	}
}
