package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.CourseReportGateway;

/**
 * La razón de ser de esta clase es localizar todos los tipos de vehículos.
 * Se utiliza a la hora de generar certificados, para poder generar certificados para todos los tipos de vehículos.
 * También para listar los certificados de cada tipo de vehículo.
 * @author UO258774
 *
 */
public class FindVehicleTypes {
	public List<VehicleTypeDto> execute() throws BusinessException{
		try(Connection c=Jdbc.getConnection()){

			CourseReportGateway ccg=PersistenceFactory.getCourseReportGateway();
			c.setAutoCommit(false);

			ccg.setConnection(c);
			//Comprobaciones
			List<VehicleTypeDto> tipos=ccg.findVehicleTypes();
			if(tipos==null) {
				c.rollback();
				throw new BusinessException("No se encontraron tipos de vehículo en la base de datos.");
			}
			else 
				return tipos;
		} catch (SQLException e) {
			throw new RuntimeException("Error de conexión");
		}
	}
}
