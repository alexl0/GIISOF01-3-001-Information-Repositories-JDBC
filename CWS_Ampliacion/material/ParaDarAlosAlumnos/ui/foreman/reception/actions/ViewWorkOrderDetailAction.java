package uo.ri.ui.foreman.reception.actions;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.dto.WorkOrderDto;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderService;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.util.Printer;

public class ViewWorkOrderDetailAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Long woId = Console.readLong("Work order id");

		WorkOrderService as = Factory.service.forWorkOrderService();
		Optional<WorkOrderDto> oWo = as.findWorkOrderById(woId);

		if ( oWo.isPresent() ) {
			Printer.printWorkOrderDetail( oWo.get() );
		} else {
			Console.println("There is no work order with that id");
		}

	}
}
