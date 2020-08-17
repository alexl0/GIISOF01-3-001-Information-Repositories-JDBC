package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CertificateDto;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.CourseReportGateway;

public class FindCertificatedByVehicleType {

	String vehicleType; //Se le pasa como parámetro al constructor en lugar de al execute() porque así no habría problema en que la clase implemente Action

	public FindCertificatedByVehicleType(String vehicleType) {
		this.vehicleType=vehicleType;
	}

	/**
	 * Transaction script que se encarga de llamar al método findCertificatesByVehicleType() de la capa de persistencia mediante una factoría (evitando el
	 * acoplamiento).
	 * Antes de ello hace una pequeña comprobación de que existen cursos en la base de datos.
	 * @return Lista de certificados de un determinado tipo de vehículo.
	 * @throws BusinessException
	 */
	public List<CertificateDto> execute() throws BusinessException{
		try(Connection c=Jdbc.getConnection()){

			CourseReportGateway crg=PersistenceFactory.getCourseReportGateway();
			c.setAutoCommit(false);

			crg.setConnection(c);
			//Comprobaciones
			List<CertificateDto> courses=crg.findCertificatesByVehicleType(vehicleType);
			if(courses==null) {
				c.rollback();
				throw new BusinessException("No hay certificados que mostrar.");
			}
			else 
				return courses;
		} catch (SQLException e) {
			throw new RuntimeException("Error de conexión.");
		}
	}
}
