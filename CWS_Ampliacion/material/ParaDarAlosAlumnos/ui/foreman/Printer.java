package uo.ri.ui.util;

import alb.util.console.Console;
import uo.ri.cws.application.dto.CertificateDto;
import uo.ri.cws.application.dto.VehicleDto;
import uo.ri.cws.application.dto.WorkOrderDto;

public class Printer {

	public static void printCertifiedMechanic(CertificateDto c) {

		Console.printf("%d\t%-10.10s %-25.25s %-25.25s\n",
				c.mechanic.id
				, c.mechanic.dni
				, c.mechanic.name
				, c.vehicleType.name
			);
	}

	public static void printWorkOrderDetail(WorkOrderDto wo) {

		Console.printf("%d for vehicle %s\n\t%-25.25s\n\t%tm/%<td/%<tY\n\t%s\n",
				wo.id
				, wo.vehicleId
				, wo.description
				, wo.date
				, wo.status
			);
	}

	public static void printVehicleDetail(VehicleDto v) {

		Console.printf("%d\t%-8.8s\t%s\t%s\n",
				v.id
				, v.plate
				, v.make
				, v.model
			);
	}

}
