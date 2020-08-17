package uo.ri.ui.administrator.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.ServiceFactory;
import uo.ri.business.serviceLayer.CertificateService;

/**
 * Clase de interfaz de usuario que se encarga de llamar al método generateCertificates() de la capa de lógica de negocio a través de una factoría.
 * @author UO258774
 *
 */
public class GenerateCertificatesAction implements Action {

	@Override
	public void execute() throws Exception {

		Console.println("Generating certificates...");

		CertificateService cs = ServiceFactory.getCertificateService();
		int qty = cs.generateCertificates();

		Console.println(qty + " certificates generated");
	}

}
