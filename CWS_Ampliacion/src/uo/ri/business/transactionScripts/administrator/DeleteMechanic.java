package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MechanicGateway;

public class DeleteMechanic {

	Long id;

	public DeleteMechanic(Long id) {
		this.id=id;
	}

	public void execute() {
		//Me lo he llevao todo a MechanincGatewayImpl al metodo delete

		try (Connection c=Jdbc.getConnection()){

			//MechanicGateway mg=new MechanicGatewayImpl();
			MechanicGateway mg=PersistenceFactory.getMechanicGateway();
			c.setAutoCommit(false);//Para hacer el commit/roollback según interese, mas adelante al comprobar. En este caso no tiene mucho sentido pero esa es la esencia

			mg.setConnection(c);


			mg.delete(id);
			c.commit();

		}
		catch(SQLException e) {
			throw new RuntimeException("Error de conexión");
		}

	}
}
