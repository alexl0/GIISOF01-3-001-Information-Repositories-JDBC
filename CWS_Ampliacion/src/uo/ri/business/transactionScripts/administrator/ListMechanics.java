package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MechanicGateway;


public class ListMechanics {

	public List<MechanicDto> execute() {
		try(Connection c=Jdbc.getConnection()){
			//MechanicGateway mg=new MechanicGatewayImpl();//Ahora hay que montar un PersistenceFactory con estos new (OJO, es un apaño, no es una factoría de verdad)
			MechanicGateway mg=PersistenceFactory.getMechanicGateway();
			mg.setConnection(c);
			return mg.findAll();	
		}
		catch(SQLException e){
			throw new RuntimeException("Error de conexión");
		}
	}
}
