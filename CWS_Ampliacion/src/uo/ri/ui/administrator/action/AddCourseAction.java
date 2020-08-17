package uo.ri.ui.administrator.action;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.serviceLayer.CourseCrudService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServiceFactory;

/**
 * Clase de interfaz de usuario que pide una serie de datos y hace una serie de comprobaciones de lógica sencillas para detectar errores lo antes posible para no hacer
 * al usuario seguir introduciendo campos cuando ya hay algún campo erróneo.
 * @author UO258774
 *
 */
public class AddCourseAction implements Action{

	@Override
	public void execute() throws BusinessException {

		CourseDto course=new CourseDto();
		/**
		 * 	###################################################################################################################################################################################
		 * 	Se harán una serie de comprobaciones en esta capa para detectar posibles errores lo antes posible y no hacer perder tiempo al usuario introduciendo valores
		 * 	después de haber introducido algún valor erróneo
		 * 	###################################################################################################################################################################################
		 */

		// ######################################################## Get info ########################################################

		course.code=Console.readString("Code").toUpperCase();
		if(course.code.contains(" "))
			throw new BusinessException("El codigo no puede contener espacios");
		course.name=Console.readString("Name");
		if(course.name.startsWith(" "))
			throw new BusinessException("El nombre no puede empezar por un espacio");
		course.description=Console.readString("Description");
		int hours=Console.readInt("Hours");
		if(hours<=0)
			throw new BusinessException("El curso debe durar una hora o más");
		course.hours=hours;
		Map<Long, Integer> percentages = new HashMap<>();
		//Como en ésta versión de la CWS no se puede añadir más tipos de vehículo, podemos preguntar al usuario uno por uno qué porcentaje tendrá el curso para cada tipo, no hace falte leer los tipos de la bd
		System.out.println("\nA continuación, introduzca por favor los porcentajes de cada tipo de vehículo a los que se dedicarán las horas del curso. No introduzca decimales.");
		int pCamion=Console.readInt("Porcentaje camión");
		percentages.put((long) 1, pCamion);
		int pMoto=Console.readInt("Porcentaje moto");
		percentages.put((long) 2, pMoto);
		int pFurgo=Console.readInt("Porcentaje furgoneta");
		percentages.put((long) 3, pFurgo);
		int pTractor=Console.readInt("Porcentaje tractor");
		percentages.put((long) 4, pTractor);
		int pCoche=Console.readInt("Porcentaje coche");
		percentages.put((long) 5, pCoche);
		int pQuad=Console.readInt("Porcentaje quad");
		percentages.put((long) 6, pQuad);

		// ######################################################## Percentages comprobation ########################################################

		if(pCamion+pMoto+pFurgo+pTractor+pCoche+pQuad!=100)
			throw new BusinessException("La suma de los porcentajes debe ser de exactamente el 100%");

		if(pCamion<0||pMoto<0||pFurgo<0||pTractor<0||pCoche<0||pQuad<0)
			throw new BusinessException("Los porcentajes no pueden ser negativos");

		course.percentages=percentages;

		// ######################################################## Get dates ########################################################

		//Start date
		Calendar startDate=Calendar.getInstance();
		int sy=Console.readInt("Start year");
		int sm=Console.readInt("Start month");
		int sd=Console.readInt("Start day");
		if(sy<1||sy>2200||sm<1||sm>12||sd<1||sd>31)
			throw new BusinessException("Fecha mal introducida.");
		startDate.set(sy, sm , sd);

		//End date
		Calendar endDate=Calendar.getInstance();
		int ey=Console.readInt("End year");
		int em=Console.readInt("End month");
		int ed=Console.readInt("End day");
		if(ey<1||ey>2200||em<1||em>12||ed<1||ed>31)
			throw new BusinessException("Fecha mal introducida.");
		endDate.set(ey, em , ed);


		// ######################################################## Dates comprobation ########################################################

		//Fechas con dias>31, meses>12 o años mayores que 2300
		if(startDate.get(Calendar.DATE)>31 || startDate.get(Calendar.MONTH)>12 || startDate.get(Calendar.YEAR)>2300 ||
				endDate.get(Calendar.DATE)>31 || endDate.get(Calendar.MONTH)>12 || endDate.get(Calendar.YEAR)>2300)
			throw new BusinessException("La fecha contiene datos incorrectos.");

		//Fecha inicio mas tarde que fecha fin
		if(startDate.after(endDate)) 
			throw new BusinessException("La fecha de inicio es más tarde que la de fin.");

		//Pasamos las fechas a Date para poder usar diffDays
		Date startDate2=Dates.fromDdMmYyyy(startDate.get(Calendar.DATE), startDate.get(Calendar.MONTH), startDate.get(Calendar.YEAR));
		Date endDate2=Dates.fromDdMmYyyy(endDate.get(Calendar.DATE), endDate.get(Calendar.MONTH), endDate.get(Calendar.YEAR));
		int days=Dates.diffDays(endDate2, startDate2)+1;//+1 porque contamos el dia que empieza y el que acaba el curso
		if(hours>Math.abs(((days)*12)))
			throw new BusinessException("No da tiempo a dar ese curso, ni siquiera impartiendo clase 12 horas al día todos los días.");

		//Solo se pueden insertar cursos que empezarán en un futuro
		if(startDate.getTimeInMillis()<System.currentTimeMillis())
			throw new BusinessException("Solo se pueden registrar cursos que empezarán en un futuro");

		//Podemos asignar las fechas despues de las comprobaciones
		course.startDate=startDate;
		course.endDate=endDate;	

		//######################################################## Register course ########################################################

		CourseCrudService ccs=ServiceFactory.getCourseCrudService();
		ccs.registerNew(course);

		Console.println("Course added");

	}

}
