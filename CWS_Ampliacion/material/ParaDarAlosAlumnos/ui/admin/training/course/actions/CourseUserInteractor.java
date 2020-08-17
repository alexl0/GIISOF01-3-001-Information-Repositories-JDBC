package uo.ri.ui.manager.training.course.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import alb.util.console.Console;
import alb.util.date.Dates;
import uo.ri.cws.application.dto.CourseDto;
import uo.ri.cws.application.dto.VehicleTypeDto;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseCrudService;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.util.Printer;

/**
 * Asks the user for basic data about a course through the console
 */
public class CourseUserInteractor {

	public void fill(CourseDto c) throws BusinessException {
		c.code = Console.readString("Code");
		c.name = Console.readString("Name");
		c.description = Console.readString("Description");
		c.startDate = askForDate("Start date");
		c.endDate = askForDate("End date");
		c.hours = Console.readInt("Duration in hours");

		showAllVehicleTypes();
		askDedicationPercentages(c.percentages);
	}

	private void askDedicationPercentages(Map<Long, Integer> percentages)
			throws BusinessException {
		percentages.clear();
		int total = 0;
		while ( total < 100 ) {
			Long vtId = Console.readLong("Vehicle type Id");
			Integer percentage = Console.readInt("Percentage");
			percentages.put(vtId, percentage);

			total += percentage;
		}
	}

	private void showAllVehicleTypes() throws BusinessException {
		CourseCrudService cs = Factory.service.forCourseCrudService();

		List<VehicleTypeDto> vts = cs.findAllVehicleTypes();
		Console.print("Vehicle types");
		for(VehicleTypeDto vt: vts) {
			Printer.printVehicleType( vt );
		}
	}

	private Date askForDate(String msg) {
		while (true) {
			try {
				String asString = Console.readString(msg);
				return Dates.fromString(asString);
			} catch (NumberFormatException nfe) {
				Console.println("Invalid date");
			}
		}
	}

}
