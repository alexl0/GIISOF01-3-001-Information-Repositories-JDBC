package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.CourseReportGateway;

/**
 * Table Data Gateway para la gestión de certificados (listados)
 * @author UO258774
 *
 */
public class CourseReportGatewayImpl implements CourseReportGateway {

	private Connection c;

	@Override
	public void setConnection(Connection c) {
		this.c=c;
	}

	/**
	 * Localiza todos los tipos de vehículos existentes en la base de datos. Así luego se pueden recorrer, tanto como para añadir certificados como
	 * para listarlos.
	 */
	@Override
	public List<VehicleTypeDto> findVehicleTypes() {
		List<VehicleTypeDto> tipos=new ArrayList<VehicleTypeDto>();
		PreparedStatement pst=null;
		ResultSet rs=null;
		String SQL=Conf.getInstance().getProperty("SQL_FIND_VEHICLE_TYPES");

		try {
			pst=c.prepareStatement(SQL);
			rs=pst.executeQuery();
			while(rs.next()) {
				VehicleTypeDto tipo=new VehicleTypeDto();
				tipo.name=rs.getString(1);
				tipos.add(tipo);
			}
			return tipos;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(rs,pst);
		}
	}

	/**
	 * Devuelve una lista de certificados dado un tipo de vehículo. Se utiliza tanto para obtener los listados como para luego listarlos.
	 */
	@Override
	public List<CertificateDto> findCertificatesByVehicleType(String vehicleType) {

		//Certificados
		List<CertificateDto> certificados=new ArrayList<CertificateDto>();
		PreparedStatement pst=null;
		ResultSet rs=null;
		String SQL=Conf.getInstance().getProperty("SQL_FIND_CERTIFICATES_BY_VECHICLE_TYPE");

		//Sacar nombre a partir de id mecanico
		String SQLNombre=Conf.getInstance().getProperty("SQL_MECHANIC_NAME_BY_ID");
		PreparedStatement pstNombre=null;
		ResultSet rsNombre=null;

		try {
			pst=c.prepareStatement(SQL);
			pst.setString(1, vehicleType);
			rs=pst.executeQuery();
			while(rs.next()) {

				//Mecanico
				MechanicDto m=new MechanicDto();
				pstNombre=c.prepareStatement(SQLNombre);
				pstNombre.setLong(1, rs.getLong("mechanic_id"));
				rsNombre=pstNombre.executeQuery();
				rsNombre.next();
				m.name=rsNombre.getString("name");

				//Certificado
				CertificateDto c=new CertificateDto();
				c.obtainedAt=rs.getDate("date");
				c.mechanic=m;

				//Ya está listo el certificado para añadirlo
				certificados.add(c);
			}
			return certificados;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(rs,pst);
		}
	}

}
