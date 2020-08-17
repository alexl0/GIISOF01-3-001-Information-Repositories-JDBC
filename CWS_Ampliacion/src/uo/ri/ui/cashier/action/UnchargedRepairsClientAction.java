package uo.ri.ui.cashier.action;

import alb.util.menu.Action;
import uo.ri.common.BusinessException;

public class UnchargedRepairsClientAction implements Action {

	/**
	 * Process:
	 * 
	 *   - Ask customer dni
	 *    
	 *   - Display all uncharged breakdowns  
	 *   		(status <> 'CHARGED'). For each breakdown, display 
	 *   		id, date, status, amount and description
	 */
	@Override
	public void execute() throws BusinessException {

		/*
		String dni=Console.readString("Intruduce dni");

		InvoiceService is=ServiceFactory.getInvoiceService();
		List<BreakdownDto> lista=is.findRepairsByClient(dni);

		for (BreakdownDto b:lista) {
			Printer.printRepairing(b);
		}
		 */
	}

}
