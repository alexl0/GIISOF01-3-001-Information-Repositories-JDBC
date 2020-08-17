package uo.ri.ui.administrator.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.serviceLayer.MechanicCrudService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServiceFactory;

public class UpdateMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {

		MechanicDto mechanic=new MechanicDto();

		// Get info
		mechanic.id = Console.readLong("Type mechahic id to update"); 
		mechanic.name = Console.readString("Name"); 
		mechanic.surname = Console.readString("Surname");

		//MechanicCrudServiceImpl mcs=new MechanicCrudServiceImpl();//este acoplamiento se resuelve con una factoría en la versión 3
		MechanicCrudService mcs=ServiceFactory.getMechanicCrudService();
		mcs.updateMechanic(mechanic);

		// Print result
		Console.println("Mechanic updated");
	}

}
