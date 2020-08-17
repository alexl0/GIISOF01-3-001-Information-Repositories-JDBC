package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.MechanicGateway;

public class MechanicGatewayImpl implements MechanicGateway {

	/*
	 * OJO! Los tdg's solo hacen estas operaciones, NO SE HACEN OPERACIOENS DE LÓGICA DE NEGOCIO Y PERSISTENCIA,
	 * (Eso se hace en los transactionScripts)
	 */

	private Connection c;
	
	@Override
	public void add(MechanicDto mechanic) {
		//Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		//Leemos las SQL del fichero
		String SQL=Conf.getInstance().getProperty("SQL_INSERT_MECHANIC");

		try {
			//c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL);
			pst.setString(1, mechanic.dni);
			pst.setString(2, mechanic.name);
			pst.setString(3, mechanic.surname);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			//Jdbc.close(rs, pst, c);
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public void delete(Long idMechanic) {
		//Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			//c = Jdbc.getConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("SQL_DELETE_MECHANIC"));
			pst.setLong(1, idMechanic);

			pst.executeUpdate();


		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public void update(MechanicDto mechanic) {
		//Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			//c = Jdbc.getConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("SQL_UPDATE_MECHANIC"));
			pst.setString(1, mechanic.name);
			pst.setString(2, mechanic.surname);
			pst.setLong(3, mechanic.id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public List<MechanicDto> findAll() {
		List<MechanicDto> mechanics=null;
		MechanicDto mechanic=null;
		String SQL=Conf.getInstance().getProperty("SQL_FIND_ALL_MECHANICS");

		//Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			//c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL);

			rs = pst.executeQuery();
			mechanics=new ArrayList<MechanicDto>();
			while(rs.next()) {
				mechanic=new MechanicDto();
				mechanic.id=rs.getLong("id");
				mechanic.dni=rs.getString("dni");
				mechanic.name=rs.getString("name");
				mechanic.surname=rs.getString("surname");
				mechanics.add(mechanic);

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst);
		}
		return mechanics;
	}

	@Override
	public MechanicDto findByDNI(String dni) {
		MechanicDto mechanic=null;
		String SQL=Conf.getInstance().getProperty("SQL_FIND_MECHANIC_BY_DNI");

		//Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			//c = Jdbc.getConnection();

			pst = c.prepareStatement(SQL);
			pst.setString(1, dni);
			rs = pst.executeQuery();

			if(rs.next()==false)
				return mechanic;//mechanic=null si se cumple el if

			mechanic=new MechanicDto();
			mechanic.id=rs.getLong("id");
			mechanic.dni=rs.getString("dni");
			mechanic.name=rs.getString("name");
			mechanic.surname=rs.getString("surname");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst);
		}
		return mechanic;
	}

	@Override
	public MechanicDto findById(Long idMechanic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnection(Connection c) {
		this.c=c;		
	}

}
