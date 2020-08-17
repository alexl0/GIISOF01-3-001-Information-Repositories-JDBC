package uo.ri.ui;

import alb.util.console.Printer;
import alb.util.menu.BaseMenu;
import uo.ri.cws.application.service.BusinessFactory;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.manager.mechanic.MechanicsMenu;
import uo.ri.ui.manager.sparepart.SparepartsMenu;
import uo.ri.ui.manager.training.TrainingMenu;
import uo.ri.ui.manager.vehicletype.VehicleTypesMenu;

public class AdminMain {

	private static class MainMenu extends BaseMenu {
		MainMenu() {
			menuOptions = new Object[][] {
				{ "Manager", null },

				{ "Mechanics management", 		MechanicsMenu.class },
				{ "Spareparts management", 		SparepartsMenu.class },
				{ "Vehicle types management", 	VehicleTypesMenu.class },
				{ "Training management", 		TrainingMenu.class },
			};
		}
	}

	public static void main(String[] args) {
		new AdminMain()
			.configure()
			.run()
			.close();
	}

	private AdminMain configure() {
		Factory.service = new BusinessFactory();

		return this;
	}

	private AdminMain run() {
		try {
			new MainMenu().execute();

		} catch (RuntimeException rte) {
			Printer.printRuntimeException(rte);
		}
		return this;
	}

	private void close() {

	}

}
