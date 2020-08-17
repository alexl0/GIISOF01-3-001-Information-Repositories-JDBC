package uo.ri.ui.manager.training.course.actions;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.dto.CourseDto;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseCrudService;
import uo.ri.ui.conf.Factory;

public class UpdateCourseAction implements Action {

	private CourseUserInteractor user = new CourseUserInteractor();

	@Override
	public void execute() throws BusinessException {

		// Ask the user for data
		Long cId = Console.readLong("Course id");
		CourseCrudService cs = Factory.service.forCourseCrudService();
		Optional<CourseDto> oc = cs.findCourseById( cId );
		assertPresent( oc );

		CourseDto c = oc.get();
		user.fill( c );

		// Invoke the service
		cs.updateCourse( c );

		// Show result
		Console.println("Course updated");
	}

	private void assertPresent(Optional<CourseDto> oc) throws BusinessException {
		if ( oc.isPresent() ) return;
		throw new BusinessException("Entity not found");
	}

}
