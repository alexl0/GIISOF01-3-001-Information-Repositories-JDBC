package uo.ri.ui.manager.training.reports.actions;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.dto.CertificateDto;
import uo.ri.cws.application.service.training.CourseReportService;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListCertificationsByVehicleTypeAction implements Action {

	@Override
	public void execute() throws Exception {

		CourseReportService rs = Factory.service.forCourseReportService();
		List<CertificateDto> rows = rs.findCertificatedByVehicleType();

		Console.println("Certificates by vehicle type");
		rows.forEach( r ->
			Printer.printCertificateRow( r )
		);
	}

}
