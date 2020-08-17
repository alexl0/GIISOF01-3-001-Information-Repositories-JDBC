package uo.ri.ui.util;

import alb.util.console.Console;
import uo.ri.cws.application.dto.MechanicDto;
import uo.ri.cws.application.dto.WorkOrderDto;

public class Printer {

	public static void printWorkOrder(WorkOrderDto rep) {
		
		Console.printf("\t%d \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f\n",  
				rep.id
				, rep.description 
				, rep.date
				, rep.status
				, rep.total
		);
	}

	public static void printMechanic(MechanicDto m) {

		Console.printf("\t%d %-10.10s %-25.25s %-25.25s\n",  
				m.id
				, m.dni
				, m.name
				, m.surname
			);
	}

}
