package uo.ri.ui.foreman.reception.actions;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.dto.WorkOrderDto;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderService;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListWorkOrdersByPlateNumberAction implements Action {

	@Override
	public void execute() throws BusinessException {

		String plate = Console.readString("Plate number");

		WorkOrderService as = Factory.service.forWorkOrderService();
		List<WorkOrderDto> wos = as.findWorkOrdersByPlateNumber(plate);

		Console.println("Work orders for vehicle " + plate);
		for(WorkOrderDto wo: wos) {
			Printer.printWorkOrderDetail( wo );
		}

	}
}
