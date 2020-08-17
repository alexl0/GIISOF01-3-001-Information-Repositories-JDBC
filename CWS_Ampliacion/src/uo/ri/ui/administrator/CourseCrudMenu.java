package uo.ri.ui.administrator;

import alb.util.menu.BaseMenu;
import uo.ri.ui.administrator.action.AddCourseAction;
import uo.ri.ui.administrator.action.DeleteCourseAction;
import uo.ri.ui.administrator.action.ListCoursesAction;
import uo.ri.ui.administrator.action.UpdateCourseAction;

public class CourseCrudMenu extends BaseMenu{

	public CourseCrudMenu() {
		menuOptions = new Object[][] { 
			{"Manager > Training management > Course CRUD", null},

			{ "Add", 			AddCourseAction.class }, 
			{ "Update", 		UpdateCourseAction.class }, 
			{ "Remove", 		DeleteCourseAction.class }, 
			{ "List all", 		ListCoursesAction.class },
		};
	}
}
