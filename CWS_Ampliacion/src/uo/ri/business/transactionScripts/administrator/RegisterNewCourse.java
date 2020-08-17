package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.CourseCrudGateway;

public class RegisterNewCourse {

	CourseDto course;

	public RegisterNewCourse(CourseDto course) {
		this.course=course;
	}

	/**
	 * Éste transaction script tiene una comprobación de lógica: comprobar si ya existe un curso con el código que ha introducido
	 * el usuario, ya que no puede haber dos cursos con el mismo código.
	 * @throws BusinessException
	 */
	public void execute() throws BusinessException {

		try(Connection c=Jdbc.getConnection()){

			CourseCrudGateway ccg=PersistenceFactory.getCourseCrudGateway();
			c.setAutoCommit(false);

			ccg.setConnection(c);

			//Curso existe con el mismo código
			if(ccg.findCourseByCode(course.code)!=null) {
				c.rollback();
				throw new BusinessException("Ya existe un curso con ese código.");
			}
			//Curso existe con el mismo nombre
			if(ccg.existsCourseWithName(course.name)) {
				c.rollback();
				throw new BusinessException("Ya existe un curso con ese nombre.");
			}
			ccg.registerNew(course);
			c.commit();//Es necesario el commit, ya que si no se guardan los cambios no se puede añadir los pocentajes a la tabla tdedications porque no existe todavía el nuevo curso
			ccg.registerNew_Percentages(course);
			c.commit();

		} catch (SQLException e) {
			throw new RuntimeException("Error de conexión");
		}

	}

}
