package uo.ri.business.dto;

public class EnrollmentDto {

	public Long id;

	public String mechanicId;
	public String courseId;
	public int attendance;  	// percentage 0..100
	public boolean passed;

	// Just for listing purposes
	public MechanicDto mechanic;
	public CourseDto course;

}
