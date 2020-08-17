package uo.ri.business.serviceLayer.impl;

import java.util.List;

import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.business.serviceLayer.CourseCrudService;
import uo.ri.business.transactionScripts.administrator.DeleteCourse;
import uo.ri.business.transactionScripts.administrator.FindCourseById;
import uo.ri.business.transactionScripts.administrator.ListCourses;
import uo.ri.business.transactionScripts.administrator.RegisterNewCourse;
import uo.ri.business.transactionScripts.administrator.UpdateCourse;
import uo.ri.common.BusinessException;

public class CourseCrudServiceImpl implements CourseCrudService {

	@Override
	public void registerNew(CourseDto dto) throws BusinessException {
		RegisterNewCourse rnc=new RegisterNewCourse(dto);
		rnc.execute();
	}

	@Override
	public void updateCourse(CourseDto dto) throws BusinessException {
		UpdateCourse uc=new UpdateCourse(dto);
		uc.execute();
	}

	@Override
	public void deleteCourse(Long id) throws BusinessException {
		DeleteCourse dc=new DeleteCourse(id);
		dc.execute();
	}

	@Override
	public List<CourseDto> findAllCourses() throws BusinessException {
		ListCourses lc=new ListCourses();
		return lc.execute();
	}

	@Override
	public List<VehicleTypeDto> findAllVehicleTypes() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CourseDto findCourseById(Long cId) throws BusinessException {
		FindCourseById fcbi=new FindCourseById(cId);
		return fcbi.execute();
	}

}
