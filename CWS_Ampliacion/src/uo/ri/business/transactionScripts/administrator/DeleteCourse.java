package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.CourseCrudGateway;

public class DeleteCourse {

	public long id;

	public DeleteCourse(long id) {
		this.id=id;
	}

	/**
	 * Transaction script hace uso de la factoría de persistencia (evitando el acoplamiento) para utilizar el método deleteCourse() de la capa de persistencia para borrar un curso.
	 * Antes de llamar a dicho método comprueba que el curso existe y no tiene mecánicos asignados (lógica de negocio).
	 * 
	 * @throws BusinessException
	 */
	public void execute() throws BusinessException {
		try(Connection c=Jdbc.getConnection()){

			CourseCrudGateway ccg=PersistenceFactory.getCourseCrudGateway();
			c.setAutoCommit(false);

			ccg.setConnection(c);

			//Comprobaciones de lógica

			//No existe curso a borrar
			if(ccg.existsCourse(id)==false) {
				c.rollback();
				throw new BusinessException("No existe un curso con ese id.");
			}
			//El curso tiene mecanicos asignados
			if(ccg.existsMechanicsInCourse(id)) {
				c.rollback();
				throw new BusinessException("Existen mecánicos registrados en ese curso, por lo que no se puede borrar.");				
			}

			ccg.deleteCourse(id);
			c.commit();

		} catch (SQLException e) {
			throw new RuntimeException("Error de conexión");
		}

	}
}
