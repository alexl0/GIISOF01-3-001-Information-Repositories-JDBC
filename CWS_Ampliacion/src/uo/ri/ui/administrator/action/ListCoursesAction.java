package uo.ri.ui.administrator.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.serviceLayer.CourseCrudService;
import uo.ri.conf.ServiceFactory;
import uo.ri.ui.util.Printer;

/**
 * Clase de interfaz de usuario que se encarga de llamar al método findAllCourses() de la capa de lógica de negocio a través de una factoría.
 * @author UO258774
 *
 */
public class ListCoursesAction implements Action{

	@Override
	public void execute() throws Exception {
		CourseCrudService as = ServiceFactory.getCourseCrudService();
		List<CourseDto> courses = as.findAllCourses();

		Console.println("\nList of courses\n");
		for(CourseDto c : courses) {
			Printer.printCourse( c );
		}

	}

}
