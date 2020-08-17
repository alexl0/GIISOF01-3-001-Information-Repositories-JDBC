package uo.ri.business.transactionScripts.administrator;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.CertificatesGateway;

/**
 * Transaction script para generar certificados. Utiliza el método generateCertificates() de la capa de persistencia.
 * Lo hace siempre desde una factoría para evitar el acoplamiento.
 * @author UO258774
 *
 */
public class GenerateCertificates {

	public int execute() {
		try(Connection c=Jdbc.getConnection()){

			CertificatesGateway cg=PersistenceFactory.getCertificatesGateway();
			c.setAutoCommit(false);

			cg.setConnection(c);

			// No hay comprobaciones de lógica

			// Generate certificates
			int num=cg.generateCertificates();
			c.commit();
			return num;
		} catch (SQLException e) {
			throw new RuntimeException("Error de conexión");
		}
	}

}
