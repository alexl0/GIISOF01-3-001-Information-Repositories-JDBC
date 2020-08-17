package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.CourseCrudGateway;

/**
 * Transaction script para listar los cursos. Hace uso de una factoria (para que no haya acoplamiento) que llama al
 * gateway de courses. En este caso se utiliza para listar los cursos, llama al método findAllCourses() de la capa
 * de persistencia.
 * @author UO258774
 *
 */
public class ListCourses {
	public List<CourseDto> execute() throws BusinessException {
		try(Connection c=Jdbc.getConnection()){

			CourseCrudGateway ccg=PersistenceFactory.getCourseCrudGateway();
			c.setAutoCommit(false);

			ccg.setConnection(c);
			//Comprobaciones
			List<CourseDto> courses=ccg.findAllCourses();
			if(courses==null) {
				c.rollback();
				throw new BusinessException("No hay cursos que mostrar.");
			}
			else 
				return courses;
		} catch (SQLException e) {
			throw new RuntimeException("Error de conexión");
		}
	}
}
