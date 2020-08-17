package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;

import uo.ri.business.dto.CourseDto;

public interface CourseCrudGateway {

	void registerNew(CourseDto course);
	void registerNew_Percentages(CourseDto course);
	void deleteCourse(Long id);
	void updateCourse(CourseDto course);
	List<CourseDto> findAllCourses();

	CourseDto findCourseById(Long cId);
	CourseDto findCourseByCode(String code);
	boolean existsCourse(Long id);
	boolean existsMechanicsInCourse(Long id);

	void setConnection(Connection c);
	boolean existsCourseWithName(String code);

}
