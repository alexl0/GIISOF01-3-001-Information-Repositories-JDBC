package uo.ri.ui.foreman.reception.actions;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.dto.WorkOrderDto;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderService;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListUnfinishedWorkOrdersAction implements Action {

	@Override
	public void execute() throws BusinessException {

		WorkOrderService as = Factory.service.forWorkOrderService();
		List<WorkOrderDto> wos = as.findUnfinishedWorkOrders();

		Console.println("In process work orders");
		for(WorkOrderDto wo: wos) {
			Printer.printWorkOrderDetail( wo );
		}

	}
}
