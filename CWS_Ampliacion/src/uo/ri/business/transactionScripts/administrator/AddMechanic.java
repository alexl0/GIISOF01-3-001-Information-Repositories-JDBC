package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MechanicGateway;

public class AddMechanic {

	//private static String SQL = "";//Me la llevo al fichero de propiedades
	MechanicDto mechanic;

	public AddMechanic(MechanicDto mechanic) {
		this.mechanic=mechanic;
	}

	/*
	 * El método execute() es un mero intermediario entre interfaz de usuario y persistencia, de moomento no hay
	 * lógica de negocio. En el caso de listar todos los mecanicos no hace falta, no hay que comprobar nada,
	 * pero aqui si, por ejemplo si metes un dni de un número, tampoco se comprueba aquí, sino en interfaz de usuario,
	 * lo que si se comprueba en logica es que metas un mecanico diferente con el mismo dni que ya tenia otro.
	 * 
	 * 1. Metodo que devuelva los mecánicos por dni
	 * 2. Se utiliza para comprobar si el mecanico que quiero insertar existe
	 */
	public void execute() throws BusinessException {
		//Me lo he llevao todo a MechanincGatewayImpl al metodo add

		try (Connection c=Jdbc.getConnection()){

			//MechanicGateway mg=new MechanicGatewayImpl();
			MechanicGateway mg=PersistenceFactory.getMechanicGateway();
			c.setAutoCommit(false);//Para hacer el commit/roollback según interese, mas adelante al comprobar. En este caso no tiene mucho sentido pero esa es la esencia

			mg.setConnection(c);

			//Comprobación de lógica
			if(mg.findByDNI(mechanic.dni)!=null) {
				c.rollback();
				throw new BusinessException("Ya existe un mecánico con ese dni.");
			}
			else {
				mg.add(mechanic);
				c.commit();
			}

		}
		catch(SQLException e) {
			throw new RuntimeException("Error de conexión");
		}

	}
}
