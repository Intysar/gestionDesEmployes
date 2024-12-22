package Controller;

import java.util.Date;
import java.util.List;

import Model.HolidayModel;
import View.HolidayView;
import Model.Employee;
import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayController {
	
	private HolidayView view;
	private HolidayModel model;
	
	public HolidayController(HolidayView view, HolidayModel model){
		this.model=model;
		this.view=view;
		
		view.ajouterButton.addActionListener(e -> ajouterHoliday());
		view.afficherButton.addActionListener(e -> afficherHolidays());
	}
	
	public void ajouterHoliday() {
		int id=view.getEmployeeId();
		Date startDate=view.getStartDate(), endDate=view.getEndDate();
		HolidayType holidayType=view.getHolidayType();
		
		boolean ajoutResult=model.ajouterHoliday(id, startDate, endDate, holidayType, id);
		
		if (ajoutResult) {
			view.afficherMessageSucces("Ajout reussi :)");
		}else {
			view.afficherMessageErreur("Ajout failed :(");
		}
	}
	
	public void afficherHolidays(){
		
		List<Holiday> holidays=model.afficherHolidays();
		view.afficherHolidays(holidays);
	}
	
	

}
