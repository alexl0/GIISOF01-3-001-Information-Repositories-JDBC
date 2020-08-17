package uo.ri.conf;

import uo.ri.persistence.CertificatesGateway;
import uo.ri.persistence.CourseCrudGateway;
import uo.ri.persistence.CourseReportGateway;
import uo.ri.persistence.InvoiceGateway;
import uo.ri.persistence.MechanicGateway;
import uo.ri.persistence.impl.CertificatesGatewayImpl;
import uo.ri.persistence.impl.CourseCrudGatewayImpl;
import uo.ri.persistence.impl.CourseReportGatewayImpl;
import uo.ri.persistence.impl.InvoiceGatewayImpl;
import uo.ri.persistence.impl.MechanicGatewayImpl;
/**
 * La intención de utilizar esta clase es no acceder directamente a la capa de persistencia desde la capa de lógica de negocio.
 * Así evitamos el acoplamiento.
 * @author UO258774
 *
 */
public class PersistenceFactory {

	public static MechanicGateway getMechanicGateway() {
		return new MechanicGatewayImpl();
	}
	public static InvoiceGateway getInvoiceGateway() { 
		return new InvoiceGatewayImpl();
	}
	public static CourseCrudGateway getCourseCrudGateway() {
		return new CourseCrudGatewayImpl();
	}
	public static CourseReportGateway getCourseReportGateway() {
		return new CourseReportGatewayImpl();
	}
	public static CertificatesGateway getCertificatesGateway() {
		return new CertificatesGatewayImpl();
	}
}
