package uo.ri.ui.cashier;


import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.ui.cashier.action.WorkOrderBillingAction;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Cashier", null },
			{ "Search not invoiced repairs by client",	NotYetImplementedAction.class }, 
			{ "Search not invoiced repairs by plate", 	NotYetImplementedAction.class }, 
			{ "Work order billing", 					WorkOrderBillingAction.class },
			{ "Pay off invoice", 						NotYetImplementedAction.class },
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}
