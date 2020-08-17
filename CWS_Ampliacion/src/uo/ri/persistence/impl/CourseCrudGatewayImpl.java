package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.CourseCrudGateway;

/**
 * Table Data Gateway para las operaciones de añadir, modificar, borrar y listar mecánicos.
 * Se le llama desde los transaction scripts a través de una factoría.
 * @author UO258774
 *
 */
public class CourseCrudGatewayImpl implements CourseCrudGateway {

	private Connection c;

	@Override
	public void registerNew(CourseDto course) {
		PreparedStatement pst_course=null;
		String SQL_COURSE=Conf.getInstance().getProperty("SQL_REGISTER_NEW_COURSE");

		try {
			pst_course=c.prepareStatement(SQL_COURSE);

			//Course
			pst_course.setString(1, course.code.toUpperCase());
			pst_course.setString(2, course.name);
			pst_course.setString(3, course.description);
			pst_course.setString(4, course.startDate.get(Calendar.YEAR)+"/"+course.startDate.get(Calendar.MONTH)+"/"+course.startDate.get(Calendar.DATE));
			pst_course.setString(5, course.endDate.get(Calendar.YEAR)+"/"+course.endDate.get(Calendar.MONTH)+"/"+course.endDate.get(Calendar.DATE));
			pst_course.setInt(6, course.hours);
			pst_course.executeUpdate();

		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(pst_course);
		}
	}

	@Override
	public void deleteCourse(Long id) {
		PreparedStatement pst1=null;
		PreparedStatement pst2=null;
		String SQL1=Conf.getInstance().getProperty("SQL_REMOVE_COURSE_FROM_DEDICATIONS");
		String SQL2=Conf.getInstance().getProperty("SQL_REMOVE_COURSE");

		try {
			pst1=c.prepareStatement(SQL1);
			pst1.setLong(1, id);
			pst1.executeUpdate();

			pst2=c.prepareStatement(SQL2);
			pst2.setLong(1, id);
			pst2.executeUpdate();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(pst1);
			Jdbc.close(pst2);
		}

	}

	@Override
	public void updateCourse(CourseDto course) {
		PreparedStatement pst=null;
		String SQL=Conf.getInstance().getProperty("SQL_UPDATE_COURSE");

		try {
			pst=c.prepareStatement(SQL);
			pst.setString(1, course.code);
			pst.setString(2, course.name);
			pst.setString(3, course.description);
			pst.setString(4, course.startDate.get(Calendar.YEAR)+"/"+course.startDate.get(Calendar.MONTH)+"/"+course.startDate.get(Calendar.DATE));
			pst.setString(5, course.endDate.get(Calendar.YEAR)+"/"+course.endDate.get(Calendar.MONTH)+"/"+course.endDate.get(Calendar.DATE));
			pst.setInt(6, course.hours);
			pst.setLong(7, course.id);
			pst.executeUpdate();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public List<CourseDto> findAllCourses() {
		List<CourseDto> courses=new ArrayList<CourseDto>();
		PreparedStatement pst=null;
		ResultSet rs=null;
		String SQL=Conf.getInstance().getProperty("SQL_FIND_ALL_COURSES");

		try {
			pst=c.prepareStatement(SQL);
			rs=pst.executeQuery();
			while(rs.next()) {
				CourseDto course=new CourseDto();
				course.id=rs.getLong("id");
				course.code=rs.getString("code");
				course.description=rs.getString("description");
				course.hours=rs.getInt("hours");
				course.name=rs.getString("name");

				//endDate
				Calendar endDate=Calendar.getInstance();
				endDate.setTime(rs.getDate("enddate"));
				course.endDate=endDate;

				//startDate
				Calendar startDate=Calendar.getInstance();
				startDate.setTime(rs.getDate("startdate"));
				course.startDate=startDate;


				courses.add(course);
			}
			return courses;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public void setConnection(Connection c) {
		this.c=c;		
	}

	@Override
	public CourseDto findCourseById(Long cId) {
		CourseDto course=null;
		String SQL=Conf.getInstance().getProperty("SQL_FIND_COURSE_BY_ID");

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(SQL);
			pst.setLong(1, cId);
			rs=pst.executeQuery();

			if(rs.next()==false)
				return course;//course=null si se cumple el if

			course=new CourseDto();
			course.id=cId;
			course.code=rs.getString("code");
			course.name=rs.getString("name");
			course.description=rs.getString("description");
			course.startDate=Calendar.getInstance();

			//endDate
			Calendar endDate=Calendar.getInstance();
			endDate.setTime(rs.getDate("enddate"));
			course.endDate=endDate;

			//startDate
			Calendar startDate=Calendar.getInstance();
			startDate.setTime(rs.getDate("startdate"));
			course.startDate=startDate;

			/*
			Date startDate=rs.getDate("startdate");
			course.startDate.set(startDate.getYear()+1900, startDate.getMonth(), startDate.getDate());

			Date endDate=rs.getDate("enddate");
			course.startDate.set(endDate.getYear()+1900, endDate.getMonth(), endDate.getDate());
			 */

			return course;

		}catch(Exception e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public boolean existsCourse(Long id) {
		String SQL=Conf.getInstance().getProperty("SQL_FIND_COURSE_BY_ID");

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(SQL);
			pst.setLong(1, id);
			rs=pst.executeQuery();

			if(rs.next()==false)
				return false;
			else
				return true;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(rs,pst);
		}
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public CourseDto findCourseByCode(String code) {
		CourseDto course=null;
		String SQL=Conf.getInstance().getProperty("SQL_FIND_COURSE_BY_CODE");

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(SQL);
			code=code.toUpperCase();//Que lo compruebe en mayúsculas, igual que se metión en mayúsculas
			pst.setString(1, code);
			rs=pst.executeQuery();

			if(rs.next()==false)
				return course;//course=null si se cumple el if

			course=new CourseDto();
			course.id=rs.getLong("id");
			course.code=code;
			course.name=rs.getString("name");
			course.description=rs.getString("description");
			course.startDate=Calendar.getInstance();

			Date startDate=rs.getDate("startdate");
			course.startDate.set(startDate.getYear()+1900, startDate.getMonth(), startDate.getDate());

			Date endDate=rs.getDate("enddate");
			course.startDate.set(endDate.getYear()+1900, endDate.getMonth(), endDate.getDate());


		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(rs,pst);
		}
		return course;
	}

	@Override
	public boolean existsCourseWithName(String name) {
		boolean exists=false;

		String SQL=Conf.getInstance().getProperty("SQL_FIND_COURSE_BY_NAME");

		PreparedStatement pst = null;
		ResultSet rs = null;

		PreparedStatement pst2 = null;
		ResultSet rs2 = null;

		PreparedStatement pst3 = null;
		ResultSet rs3 = null;

		try {
			pst = c.prepareStatement(SQL);
			pst.setString(1, name);
			rs=pst.executeQuery();
			if(rs.next())
				exists= true;

			//Se comprueba también en mayúsculas
			pst2 = c.prepareStatement(SQL);
			pst2.setString(1, name.toUpperCase());
			rs2=pst2.executeQuery();
			if(rs2.next())
				exists= true;

			//Y en minúsculas
			pst3 = c.prepareStatement(SQL);
			pst3.setString(1, name.toLowerCase());
			rs3=pst3.executeQuery();
			if(rs3.next())
				exists= true;


			return exists;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(rs,pst);
			Jdbc.close(rs2,pst2);
			Jdbc.close(rs3,pst3);
		}
	}

	@Override
	public void registerNew_Percentages(CourseDto course) {
		PreparedStatement pst_percentages=null;
		String SQL_PERCENTAGES=Conf.getInstance().getProperty("SQL_REGISTER_PERCENTAGES_FOR_NEW_COURSE");

		try {
			pst_percentages=c.prepareStatement(SQL_PERCENTAGES);

			Map<Long, Integer> e = course.percentages;
			for(int i=1;i<=6;i++)
				if(e.containsKey((long)i) && e.get((long)i)>0) { //No registramos cuando las horas son 0
					pst_percentages.setInt(1, e.get((long)i));
					pst_percentages.setLong(2, findCourseByCode(course.code).id);
					pst_percentages.setInt(3, i);
					pst_percentages.executeUpdate();
				}

		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(pst_percentages);
		}

	}

	@Override
	public boolean existsMechanicsInCourse(Long id) {
		PreparedStatement pst=null;
		ResultSet rs=null;
		String SQL=Conf.getInstance().getProperty("SQL_EXISTS_MECHANICS_IN_COURSE");
		try {
			pst=c.prepareStatement(SQL);
			pst.setLong(1, id);
			rs=pst.executeQuery();
			if(rs.next())
				return true;
			else
				return false;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			Jdbc.close(pst);
		}
	}
}
