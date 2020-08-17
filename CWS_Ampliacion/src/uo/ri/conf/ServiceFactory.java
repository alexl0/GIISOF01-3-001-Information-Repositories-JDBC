package uo.ri.conf;

import uo.ri.business.serviceLayer.CertificateService;
import uo.ri.business.serviceLayer.CourseCrudService;
import uo.ri.business.serviceLayer.CourseReportService;
import uo.ri.business.serviceLayer.InvoiceService;
import uo.ri.business.serviceLayer.MechanicCrudService;
import uo.ri.business.serviceLayer.impl.CertificateServiceImpl;
import uo.ri.business.serviceLayer.impl.CourseCrudServiceImpl;
import uo.ri.business.serviceLayer.impl.CourseReportServiceImpl;
import uo.ri.business.serviceLayer.impl.InvoiceServiceImpl;
import uo.ri.business.serviceLayer.impl.MechanicCrudServiceImpl;

/**
 * La intención de utilizar esta clase es no acceder directamente a la capa de negocio desde la capa de interfaz de usuario.
 * Así evitamos el acoplamiento.
 * @author UO258774
 *
 */
public class ServiceFactory {
	public static MechanicCrudService getMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}
	public static InvoiceService getInvoiceService() {
		return new InvoiceServiceImpl();
	}
	public static CourseCrudService getCourseCrudService() {
		return new CourseCrudServiceImpl();
	}
	public static CourseReportService getCourseReportService() {
		return new CourseReportServiceImpl();
	}
	public static CertificateService getCertificateService() {
		return new CertificateServiceImpl();
	}
}
