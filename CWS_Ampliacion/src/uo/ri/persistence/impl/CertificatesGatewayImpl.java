package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.Conf;
import uo.ri.persistence.CertificatesGateway;

/**
 * Table Data Gateway para generar certificados. Se le llama desde los transaction scripts a través de una factoría para evitar el acompaliento entre las capas.
 * @author UO258774
 *
 */
public class CertificatesGatewayImpl implements CertificatesGateway {

	private Connection c;

	@Override
	public void setConnection(Connection c) {
		this.c=c;
	}

	/**
	 * 
	 * Genera certificados de los mecánicos y los almacena en la tabla tcertificates. Si se ejecuta sobre la bd virgen expide 23 certificados, y contando los que ya había en la bd, habrá
	 * un total de 35 certificados.
	 * 
	 * Algoritmo
	 * 
	 * 		para cada id_mechanic {
	 * 			para cada tipo de vehículo{
	 * 				nhoras=0
	 * 				para cada curso aprobado, con asistencia>85 que se dedica a ese tipo de vehículo
	 * 					nhoras += duracion * attendance * percentage
	 * 				if(nhoras>=minTrainingHours && noEstaCertificado)
	 * 					expedir(id_mechanic, vehicletype_id, curso.enddate)
	 * 			}
	 * 		}
	 */
	@Override
	public int generateCertificates() {
		int numCertificatesGenerated=0;
		try {
			String SQL_SELECT_ID_FROM_TMECHANICS=Conf.getInstance().getProperty("SQL_SELECT_ID_FROM_TMECHANICS");
			String SQL_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES=Conf.getInstance().getProperty("SQL_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES");
			String SQL_COURSES_PASSED_VEHICLE_TYPE=Conf.getInstance().getProperty("SQL_COURSES_PASSED_VEHICLE_TYPE");
			String SQL_ATTENDANCE=Conf.getInstance().getProperty("SQL_ATTENDANCE");
			String SQL_PERCENTAJE=Conf.getInstance().getProperty("SQL_PERCENTAJE");

			PreparedStatement pst_SELECT_ID_FROM_TMECHANICS=c.prepareStatement(SQL_SELECT_ID_FROM_TMECHANICS);
			ResultSet rs_SELECT_ID_FROM_TMECHANICS=pst_SELECT_ID_FROM_TMECHANICS.executeQuery();

			//Para cada mecánico
			while(rs_SELECT_ID_FROM_TMECHANICS.next()) {
				PreparedStatement pst_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES=c.prepareStatement(SQL_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES);
				ResultSet rs_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES=pst_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES.executeQuery();

				//Para cada tipo de vehículo
				while(rs_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES.next()) {

					Long idMech=rs_SELECT_ID_FROM_TMECHANICS.getLong(1);
					Long idVehicleType=rs_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES.getLong(1);
					int minTrain=rs_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES.getInt(2);

					double nHoras=0;

					PreparedStatement pst_COURSES_PASSED_VEHICLE_TYPE=c.prepareStatement(SQL_COURSES_PASSED_VEHICLE_TYPE);
					pst_COURSES_PASSED_VEHICLE_TYPE.setLong(1, idMech);
					pst_COURSES_PASSED_VEHICLE_TYPE.setLong(2, idVehicleType);
					ResultSet rs_COURSES_PASSED_VEHICLE_TYPE=pst_COURSES_PASSED_VEHICLE_TYPE.executeQuery();

					//Para cada curso, que ha aprobado el mecanico y que se dedica a el tipo de vehiculo que estamos iterando
					while(rs_COURSES_PASSED_VEHICLE_TYPE.next()) {

						Long idCourse=rs_COURSES_PASSED_VEHICLE_TYPE.getLong(1);
						int hCourse=rs_COURSES_PASSED_VEHICLE_TYPE.getInt(2);
						Date date=rs_COURSES_PASSED_VEHICLE_TYPE.getDate(3);

						PreparedStatement pst_SQL_ATTENDANCE=c.prepareStatement(SQL_ATTENDANCE);
						pst_SQL_ATTENDANCE.setLong(1, idMech);
						pst_SQL_ATTENDANCE.setLong(2, idCourse);
						PreparedStatement pst_SQL_PERCENTAJE=c.prepareStatement(SQL_PERCENTAJE);
						pst_SQL_PERCENTAJE.setLong(1, idCourse);
						pst_SQL_PERCENTAJE.setLong(2, idVehicleType);
						ResultSet rs_SQL_ATTENDANCE=pst_SQL_ATTENDANCE.executeQuery();
						ResultSet rs_SQL_PERCENTAJE=pst_SQL_PERCENTAJE.executeQuery();

						int attendance=0;
						int percentage=0;

						while(rs_SQL_ATTENDANCE.next())
							attendance=rs_SQL_ATTENDANCE.getInt(1);
						while(rs_SQL_PERCENTAJE.next())
							percentage=rs_SQL_PERCENTAJE.getInt(1);

						pst_SQL_ATTENDANCE.close();
						pst_SQL_PERCENTAJE.close();
						rs_SQL_ATTENDANCE.close();
						rs_SQL_PERCENTAJE.close();

						//Comprobamos horas
						nHoras+=hCourse*(attendance/100.0)*(percentage/100.0);

						// Generamos certificado si supera las horas necesarias para ese tipo de vehiculo, y no está certificado
						if(nHoras>=minTrain && !yaEstaCertificado(idMech, idVehicleType)) {
							expedir(idMech, idVehicleType, date);
							numCertificatesGenerated++;
						}
					}
					pst_COURSES_PASSED_VEHICLE_TYPE.close();
					rs_COURSES_PASSED_VEHICLE_TYPE.close();
				}
				pst_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES.close();
				rs_SELECT_ID_MINTRAININGHOURS_FROM_TVEHICLETYPES.close();
			}
			pst_SELECT_ID_FROM_TMECHANICS.close();
			rs_SELECT_ID_FROM_TMECHANICS.close();

			return numCertificatesGenerated;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *  ######################## Métodos que se han extraido para una mejor comprensión del código ########################
	 */

	/**
	 * Se utiliza para no darle dos veces un certificado a un mecánico
	 * @param id_mechanic
	 * @param id_vehicle_type
	 * @return
	 */
	private boolean yaEstaCertificado(long id_mechanic, long id_vehicle_type) {
		PreparedStatement pst =null;
		ResultSet rs=null;
		String SQL_COMPROBAR_CERTIFICADO=Conf.getInstance().getProperty("SQL_COMPROBAR_CERTIFICADO");
		try {
			pst=c.prepareStatement(SQL_COMPROBAR_CERTIFICADO);
			pst.setLong(1, id_mechanic);
			pst.setLong(2, id_vehicle_type);
			rs=pst.executeQuery();
			if(rs.next())
				return true;
			return false;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(rs, pst);
		}
	}

	/**
	 * Dado un mecánico, un tipo de vehículo y una fecha, añade una fila a la tabla tcertificates
	 * @param id_mechanic
	 * @param id_vehicle_type
	 * @param date
	 */
	private void expedir(long id_mechanic, long id_vehicle_type, Date date) {
		PreparedStatement pst =null;
		String SQL_INSERT_CERTIFICATE=Conf.getInstance().getProperty("SQL_INSERT_CERTIFICATE");
		try {
			pst=c.prepareStatement(SQL_INSERT_CERTIFICATE);
			pst.setDate(1, date);
			pst.setLong(2, id_mechanic);
			pst.setLong(3, id_vehicle_type);
			pst.executeUpdate();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(pst);
		}
	}

}
