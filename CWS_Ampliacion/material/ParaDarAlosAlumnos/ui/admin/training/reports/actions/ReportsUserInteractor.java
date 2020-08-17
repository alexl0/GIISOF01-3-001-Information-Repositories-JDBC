package uo.ri.ui.manager.training.reports.actions;

import java.util.List;

import alb.util.console.Console;
import uo.ri.cws.application.dto.MechanicDto;
import uo.ri.cws.application.dto.VehicleTypeDto;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseAttendanceService;
import uo.ri.cws.application.service.training.CourseCrudService;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.util.Printer;

public class ReportsUserInteractor {

	public Long askForMechanicId() throws BusinessException {
		showMechanics();
		return Console.readLong("Mechanic id");
	}

	public String askForVehicleTypeId() throws BusinessException {
		showVehicleTypes();
		return Console.readString("Vehicle type id");
	}

	private void showVehicleTypes() throws BusinessException {
		CourseCrudService cs = Factory.service.forCourseCrudService();
		List<VehicleTypeDto> mechanics = cs.findAllVehicleTypes();
		Console.println("List of vehicle types");
		mechanics.forEach((vt) -> Printer.printVehicleType( vt ) );
	}

	private void showMechanics() throws BusinessException {
		CourseAttendanceService cs = Factory.service.forCourseAttendanceService();
		List<MechanicDto> mechanics = cs.findAllActiveMechanics();
		Console.println("List of mechanics");
		mechanics.forEach((m) -> Printer.printMechanic(m) );
	}

}
