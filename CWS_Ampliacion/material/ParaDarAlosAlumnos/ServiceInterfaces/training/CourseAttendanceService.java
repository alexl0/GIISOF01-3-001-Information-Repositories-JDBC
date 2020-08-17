package uo.ri.cws.application.service.training;

import java.util.List;

import uo.ri.cws.application.dto.CourseDto;
import uo.ri.cws.application.dto.EnrollmentDto;
import uo.ri.cws.application.dto.MechanicDto;
import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Manager
 * It follows the ISP principle (@see SOLID principles from RC Martin)
 */
public interface CourseAttendanceService {

	/**
	 * Registers the attendance of a mechanic to a course.
	 * @param dto, with this fields filled: mechanicId, courseId, attendance and
	 * 		the boolean passed.
	 *
	 * @return the same dto but with the id field set to the generated UUID value
	 *
	 * @throws BusinessException if:
	 * 	- the mechanic id does not exit, or
	 * 	- the course id does not exist, or
	 *  - there already is another enrollment registered for the same mechanic
	 *  	and course, or
	 *  - the attendance is under 85% and the course is marked as passed, or
	 *  - the course is not yet finished, or  <- IGNORE THIS, complicates testing
	 *  - the value for percentage is not in the range 0..100
	 */
	EnrollmentDto registerNew(EnrollmentDto dto) throws BusinessException;

	/**
	 * Removes the attendance record specified by the id
	 * @param id of the attendance record
	 * @throws BusinessException if the attendance record does not exist
	 */
	void deleteAttendace(Long id) throws BusinessException;

	/**
	 * Lists all the attendance records for the specified course id.
	 * @return the list of attendances or an empty list if the course
	 * 		does not exist or the course has no attendance registered yet
	 * @throws BusinessException
	 */
	List<EnrollmentDto> findAttendanceByCourseId(Long id) throws BusinessException;

	List<CourseDto> findAllActiveCourses() throws BusinessException;
	List<MechanicDto> findAllActiveMechanics() throws BusinessException;
}
