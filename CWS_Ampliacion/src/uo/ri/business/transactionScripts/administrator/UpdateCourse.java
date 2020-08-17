package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.CourseCrudGateway;

public class UpdateCourse {

	CourseDto course;

	public UpdateCourse(CourseDto course) {
		this.course=course;
	}

	/**
	 * Éste transaction script hace uso de la factoría de persistencia para hacer la modificación de un curso.
	 * Hace varias comprobaciones de lógica de negocio que se detallan más adelante, antes de llamar al método de la capa de persistencia updateCourse().
	 * @throws BusinessException
	 */
	public void execute() throws BusinessException {
		try (Connection c=Jdbc.getConnection()){

			CourseCrudGateway ccg=PersistenceFactory.getCourseCrudGateway();
			c.setAutoCommit(false);//Para hacer el commit/roollback según interese, mas adelante al comprobar.

			ccg.setConnection(c);

			//Comprobar que el curso existe
			CourseDto cursoAModificar = ccg.findCourseById(course.id);
			if(cursoAModificar==null) {
				c.rollback();
				throw new BusinessException("No existe ningún curso con ese id.");
			}

			//El curso tiene mecanicos asignados
			if(ccg.existsMechanicsInCourse(course.id)) {
				c.rollback();
				throw new BusinessException("Existen mecánicos registrados en ese curso, por lo que no se puede modificar.");				
			}

			//Comprobar que no haya sido ni esté siendo impartido
			Calendar today=Calendar.getInstance();
			today.set(Calendar.MONTH, today.get(Calendar.MONTH)+1);
			//System.out.println("today: "+today.get(Calendar.YEAR)+" "+(today.get(Calendar.MONTH))+" "+today.get(Calendar.DATE)); //Hay que sumarle uno al month para que salga el mes correctamente, no se por que
			if(today.after(cursoAModificar.startDate)) {
				c.rollback();
				throw new BusinessException("El curso no se puede modificar porque ha sido o está siendo impartido.");
			}

			//Comprobar que el codigo que se quiera asignar al curso no sea igual que ninguno de los que hay ya en la base de datos
			if(ccg.findCourseByCode(course.code)!=null) {
				c.rollback();
				throw new BusinessException("Ya existe un curso con ese código.");
			}
			//Comprobar que el nombre que se quiera asignar al curso no sea igual que ninguno de los que hay ya en la base de datos
			if(ccg.existsCourseWithName(course.name)) {
				c.rollback();
				throw new BusinessException("Ya existe un curso con ese nombre.");
			}

			ccg.updateCourse(course);
			c.commit();

		}
		catch(SQLException e) {
			throw new RuntimeException("Error de conexión");
		}
	}

}
