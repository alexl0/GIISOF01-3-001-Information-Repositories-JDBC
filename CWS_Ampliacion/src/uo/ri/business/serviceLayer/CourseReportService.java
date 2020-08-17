package uo.ri.business.serviceLayer;

import java.util.List;

import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.TrainingForMechanicRow;
import uo.ri.business.dto.TrainingHoursRow;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.common.BusinessException;

/**
 * This service is intended to be used by the Manager
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface CourseReportService {

	/**
	 * Returns a report summary of the training of the specified mechanic
	 *
	 * @param id of the mechanic
	 *
	 * @return the list of lines, one for each vehicle type the mechanic
	 * 	have had some training
	 *
	 * @throws BusinessException, DOES NOT
	 */
	List<TrainingForMechanicRow> findTrainigByMechanicId(Long id)
			throws BusinessException;

	/**
	 * Returns a list of rows, one for each vehicle type and mechanic that had
	 * attended to a course.
	 *
	 * @return the list, that might be empty if no mechanic has been trained
	 * 		for any vehicle type.
	 * @throws BusinessException, DOES NOT
	 */
	List<TrainingHoursRow> findTrainingByVehicleTypeAndMechanic()
			throws BusinessException;

	/**
	 * Returns a list of rows, one for each mechanic that had been certified,
	 * sorted by vehicle type and mechanic.
	 * @param name 
	 *
	 * @return the list, that might be empty if no mechanic has been certified
	 * @throws BusinessException, DOES NOT
	 */
	List<CertificateDto> findCertificatedByVehicleType(String name)
			throws BusinessException;

	/**
	 * @return lista con los tipos de vehículos que existen
	 * @throws BusinessException
	 */
	List<VehicleTypeDto> findVehicleTypes() 
			throws BusinessException;
}
