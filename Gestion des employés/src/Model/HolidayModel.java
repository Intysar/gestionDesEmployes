package Model;
import DAO.HolidayDAOImpl;
import Model.Holiday.HolidayType;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HolidayModel {

    private HolidayDAOImpl dao;

    public HolidayModel(HolidayDAOImpl dao) {
        this.dao = dao;
    }

    public boolean ajouterHoliday(int id, Date startDate, Date endDate, HolidayType holidayType, int employeeId, Employee employee) {

        if (startDate.after(endDate)) {
        	System.out.println("Date invalide!");
            return false;
        }

        Date currentDate = new Date();
        if (startDate.after(currentDate) || endDate.after(currentDate)) {
        	System.out.println("Date invalide!");
            return false;
        }

        long diffInMillis = endDate.getTime() - startDate.getTime(); 
        long days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS); 

        System.out.println(days+" jour(s)");

        if (days>employee.getSolde()) {
        	System.out.println("Date invalide!");
        	return false;
        }
        
        Holiday newHoliday=new Holiday(id, startDate, endDate, holidayType, employeeId);
        dao.ajouterHoliday(newHoliday);
        
        return true;
    }
}
