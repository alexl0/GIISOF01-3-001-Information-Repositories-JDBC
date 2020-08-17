package uo.ri.ui.foreman.reception;

import alb.util.menu.BaseMenu;
import uo.ri.ui.foreman.reception.actions.AssignWorkOrderAction;
import uo.ri.ui.foreman.reception.actions.ListCertifiedMechanicsAction;
import uo.ri.ui.foreman.reception.actions.ListUnfinishedWorkOrdersAction;
import uo.ri.ui.foreman.reception.actions.ListWorkOrdersByPlateNumberAction;
import uo.ri.ui.foreman.reception.actions.RegisterWorkOrderAction;
import uo.ri.ui.foreman.reception.actions.RemoveWorkOrderAction;
import uo.ri.ui.foreman.reception.actions.UpdateWorkOrderAction;
import uo.ri.ui.foreman.reception.actions.ViewWorkOrderDetailAction;

public class ReceptionMenu extends BaseMenu {

	public ReceptionMenu() {
		menuOptions = new Object[][] { 
			{"Foreman > Vehicle reception", null},
			
			{"Register a work order", 	 RegisterWorkOrderAction.class }, 
			{"Update a work order", 	 UpdateWorkOrderAction.class },
			{"Remove a work order", 	 RemoveWorkOrderAction.class },
			{"", null},
			{"List unfinished work orders", ListUnfinishedWorkOrdersAction.class }, 
			{"List work orders by plate", ListWorkOrdersByPlateNumberAction.class }, 
			{"View work order detail", 	 ViewWorkOrderDetailAction.class },
			{"", null},
			{"List certified mechanics", ListCertifiedMechanicsAction.class }, 
			{"Assign a work order",  	 AssignWorkOrderAction.class },
		};
	}

}
