package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.CourseCrudGateway;

/**
 * El objetivo de esta clase es comprobar que existe un curso con un determinado id.
 * Se utiliza para que no se pueda modificar un curso cuyo id no existe en la base de datos.
 * @author UO258774
 *
 */
public class FindCourseById {

	Long cId;

	public FindCourseById(Long cId) {
		this.cId=cId;
	}

	public CourseDto execute() {
		try(Connection c=Jdbc.getConnection()){

			CourseCrudGateway ccg=PersistenceFactory.getCourseCrudGateway();
			c.setAutoCommit(false);

			ccg.setConnection(c);

			ccg.findCourseById(cId);
			c.commit();

		}catch (SQLException e) {
			throw new RuntimeException("Error de conexión");
		}
		return null;
	}

}
