package uo.ri.ui.manager.training.reports.actions;

import java.util.Comparator;
import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.dto.TrainingHoursRow;
import uo.ri.cws.application.service.training.CourseReportService;
import uo.ri.ui.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListTrainingByVehicleTypeAction implements Action {

	@Override
	public void execute() throws Exception {

		CourseReportService rs = Factory.service.forCourseReportService();
		List<TrainingHoursRow> rows = rs.findTrainingByVehicleTypeAndMechanic();

		Console.println("Training by vehicle type");
		rows.sort( new TVTRComparator() );
		rows.forEach( r ->
			Printer.printTrainingHoursRow( r )
		);
	}

	/**
	 * The sorting can be done in the query, but is also frequently done
	 * at the presentation layer
	 */
	private class TVTRComparator implements Comparator<TrainingHoursRow> {

		@Override
		public int compare(TrainingHoursRow a,
				TrainingHoursRow b) {

			int res = a.vehicleTypeName.compareTo( b.vehicleTypeName );
			if ( res == 0)  {
				res = a.mechanicFullName.compareTo( b.mechanicFullName);
			}
			return res;
		}

	}

}
