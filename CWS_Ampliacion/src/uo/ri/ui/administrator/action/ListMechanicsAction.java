package uo.ri.ui.administrator.action;

import java.util.List;
import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.serviceLayer.impl.MechanicCrudServiceImpl;
import uo.ri.common.BusinessException;
import uo.ri.ui.util.Printer;

public class ListMechanicsAction implements Action {


	@Override
	public void execute() throws BusinessException {
		Console.println("\nList of mechanics \n");  

		MechanicCrudServiceImpl mcs=new MechanicCrudServiceImpl();//este acoplamiento se resuelve con una factoría en la versión 3
		List<MechanicDto>mechanics=mcs.findAllMechanics();

		for (MechanicDto m:mechanics) {
			Printer.printMechanic(m);
		}

	}
}
