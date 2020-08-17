package uo.ri.ui.manager.training.course;

import alb.util.menu.BaseMenu;
import uo.ri.ui.manager.training.course.actions.AddCourseAction;
import uo.ri.ui.manager.training.course.actions.ListCoursesAction;
import uo.ri.ui.manager.training.course.actions.RemoveCourseAction;
import uo.ri.ui.manager.training.course.actions.UpdateCourseAction;

public class CourseCrudMenu extends BaseMenu {

	public CourseCrudMenu() {
		menuOptions = new Object[][] { 
			{"Manager > Training management > Course CRUD", null},
			
			{ "Add", 			AddCourseAction.class }, 
			{ "Update", 		UpdateCourseAction.class }, 
			{ "Remove", 		RemoveCourseAction.class }, 
			{ "List all", 		ListCoursesAction.class },
		};
	}

}
