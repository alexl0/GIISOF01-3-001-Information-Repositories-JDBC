package uo.ri.ui.administrator.action;


import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.serviceLayer.MechanicCrudService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServiceFactory;

public class DeleteMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {


		long id = Console.readLong("Type mechanic id "); 

		//OJO, NO SE HACEN COMPROBACIONES, POR EJEMPLO, NO SE COMPRUEBA QUE EL MECANICO ESTE ASIGNADO A UNA AVERÍA
		//(EN CUYO CASO NO SE PODRÍA BORRAR)

		//MechanicCrudServiceImpl mcs=new MechanicCrudServiceImpl();//este acoplamiento se resuelve con una factoría en la versión 3
		MechanicCrudService mcs=ServiceFactory.getMechanicCrudService();
		mcs.deleteMechanic(id);

		Console.println("Mechanic deleted");
	}

}
