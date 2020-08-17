package uo.ri.ui.administrator.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.serviceLayer.CourseCrudService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServiceFactory;

/**
 * Clase de interfaz de usuario. Utiliza el método deleteCourse() de la capa de lógica de negocio a través de una factoría para que no haya acoplamiento entre
 * las clases. La comprobación de si existe el curso a borrar y si tiene mecánicos asignados se hace en la capa de lógica de negocio.
 * @author UO258774
 *
 */
public class DeleteCourseAction implements Action{

	@Override
	public void execute() throws Exception {
		long id=Console.readLong("Inserte id del curso a borrar");
		if(id<=0 || id>100000)
			throw new BusinessException("El id debe ser mayor o igual a 1 y menor que 100 000");

		CourseCrudService ccs=ServiceFactory.getCourseCrudService();
		ccs.deleteCourse(id);
		Console.println("Course removed");

	}

}
