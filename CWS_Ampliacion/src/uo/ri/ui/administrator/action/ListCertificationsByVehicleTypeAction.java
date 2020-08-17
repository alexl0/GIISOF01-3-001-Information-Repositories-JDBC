package uo.ri.ui.administrator.action;

import java.util.List;
import alb.util.menu.Action;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.business.serviceLayer.CourseReportService;
import uo.ri.conf.ServiceFactory;

/**
 * Clase de interfaz de usuario que se encarga de llamar al método findCertificatedByVehicleType() de la capa de lógica de negocio a través de una factoría.
 * @author UO258774
 *
 */
public class ListCertificationsByVehicleTypeAction implements Action {

	@Override
	public void execute() throws Exception {

		CourseReportService crs = ServiceFactory.getCourseReportService();

		List<VehicleTypeDto> lv=crs.findVehicleTypes();

		System.out.println("Certificates by vehicle type");
		for (VehicleTypeDto vt : lv) {
			System.out.println("\t-"+vt.name);
			List<CertificateDto> rows = crs.findCertificatedByVehicleType(vt.name);
			if(rows==null) 
				System.out.println("\t\tNo hay certificados para el tipo de vehículo: "+vt.name);
			for(CertificateDto c : rows) {
				System.out.println("\t\t"+c.mechanic.name+", "+c.obtainedAt.toString());
			}
		}

	}

}
