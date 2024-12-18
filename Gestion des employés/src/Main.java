import View.*;
import Model.*;
import Controller.*;
import DAO.*;

public class Main {
	
	public static void main(String[] args) {
		
		EmployeeDAOImpl dao=new EmployeeDAOImpl();
		EmployeeModel model=new EmployeeModel(dao);
		EmployeeView view=new EmployeeView();
		new EmployeeController(view, model);
		
	}
	
}
