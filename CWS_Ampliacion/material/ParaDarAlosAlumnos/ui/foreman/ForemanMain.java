package uo.ri.ui;

import alb.util.console.Printer;
import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.cws.application.service.BusinessFactory;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.foreman.cliente.ClientsMenu;
import uo.ri.ui.foreman.reception.ReceptionMenu;
import uo.ri.ui.foreman.vehicle.VehiclesMenu;

public class ForemanMain {

	private static class MainMenu extends BaseMenu {
		MainMenu() {
			menuOptions = new Object[][] {
				{ "Foreman", null },
				{ "Vehicle reception", 		ReceptionMenu.class },
				{ "Client management", 		ClientsMenu.class },
				{ "Vehicle management", 	VehiclesMenu.class },
				{ "Review client history", 	NotYetImplementedAction.class },
			};
		}
	}

	public static void main(String[] args) {
		new ForemanMain()
			.config()
			.run()
			.close();
	}

	private ForemanMain config() {
		Factory.service = new BusinessFactory();

		return this;
	}

	public ForemanMain run() {
		try {
			new MainMenu().execute();

		} catch (RuntimeException rte) {
			Printer.printRuntimeException(rte);
		}
		return this;
	}

	private void close() {}

}
