package uo.ri.cws.application.service.training;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.dto.CourseDto;
import uo.ri.cws.application.dto.VehicleTypeDto;
import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Manager
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface CourseCrudService {

	/**
	 * Registers a new course in the system
	 *
	 * @param dto, it must specify: name, description, startDate, endDate, hours
	 * 		and the table of percentages. The id and the version fields
	 * 		must be null (will be assigned by the system).
	 *
	 * @return the same Dto with the id field assigned to the created UUID
	 *
	 * @throws BusinessException, if:
	 *  - any field other than id and version is null or empty, or
	 * 	- there already exists a course with the same name, or
	 * 	- there is percentage devoted to a non existing vehicle type, or
	 * 	- the initial and final dates are in the past or inverted, or
	 * 	- the number of hours are zero or negative, or
	 *  - there are no dedications specified, or
	 *  - the sum of devoted percentages does not equals 100%, or
	 *  - the are any dedication with an invalid percentage (empty, zero, negative)
	 */
	CourseDto registerNew(CourseDto dto) throws BusinessException;

	/**
	 * Updates the course specified by the id with the new data.
	 * A course an only be modified if it has not yet started. If the start
	 * date is wrong then remove the course and start again...
	 * The dedications of the course to the vehicle types are not modified
	 * by this operation.
	 *
	 * @param dto. Must specify all the fields. The id and version fields must
	 * 	match the current state of the course. All the rest of fields must be
	 * 	filled, even if there is no change in the data. So it must pass the same
	 * 	validation as for new courses.
	 *
	 * @throws BusinessException if:
	 * 	- it does not exist the course with the specified id, or
	 *  - the current version of the course does not match the version of the dto, or
	 *  - the course has its start date in the past, or
	 * 	- the new data does not pass the validation specified
	 * 		in @see registerNew
	 *
	 */
	void updateCourse(CourseDto dto) throws BusinessException;

	/**
	 * A course can only be deleted if it still has no attendance registered.
	 * Delete a course also implies remove all its dedications in cascade.
	 *
	 * Note: A course and its dedications form an aggregate.
	 *
	 * @param id
	 * @throws BusinessException if:
	 * 	- there is no course with the specified id, or
	 * 	- the course already has enrollments registered.
	 */
	void deleteCourse(Long id) throws BusinessException;

	/**
	 *
	 * @return a list of CourseDto. Each one represents a course.
	 * @see CourseDto class for details.
	 *
	 * DOES NOT @throws BusinessException
	 */
	List<CourseDto> findAllCourses() throws BusinessException;

	/**
	 * @return a list of VehicleTypeDto.
	 * @see VehicleTypeDto class for details.
	 *
	 * DOES NOT @throws BusinessException
	 */
	List<VehicleTypeDto> findAllVehicleTypes() throws BusinessException;

	/**
	 * @param cId
	 * @return an Optional, what if there is no course with the specified id?
	 * DOES NOT @throws BusinessException
	 * @throws BusinessException
	 */
	CourseDto findCourseById(Long cId) throws BusinessException;
}
