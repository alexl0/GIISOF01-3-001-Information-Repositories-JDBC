package uo.ri.ui.administrator.action;


import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.serviceLayer.MechanicCrudService;
//import uo.ri.business.impl.MechanicCrudServiceImpl;
import uo.ri.common.BusinessException;
//import uo.ri.conf.ServiceFactory;
import uo.ri.conf.ServiceFactory;

public class AddMechanicAction implements Action {


	@Override
	public void execute() throws BusinessException {

		MechanicDto mechanic=new MechanicDto();

		// Get info
		mechanic.dni = Console.readString("Dni"); //Una de las lógicas de negocio sería que el dni esté en su correcto formato y no se repita
		mechanic.name = Console.readString("Name"); 
		mechanic.surname = Console.readString("Surname");

		/*
		 * Utilizamos la factoría
		 */
		//MechanicCrudService mcs=new MechanicCrudServiceImpl();//este acoplamiento se resuelve con una factoría en la versión 3
		MechanicCrudService mcs=ServiceFactory.getMechanicCrudService();
		mcs.addMechanic(mechanic);

		// Print result
		Console.println("Mechanic added");
	}

}
