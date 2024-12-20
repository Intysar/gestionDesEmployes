package Model;
import DAO.HolidayDAOImpl;
import Model.Holiday.HolidayType;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HolidayModel {

    private HolidayDAOImpl dao;

    public HolidayModel(HolidayDAOImpl dao) {
        this.dao = dao;
    }

    public boolean ajouterHoliday(int id, Date startDate, Date endDate, HolidayType holidayType, int employeeId) {

        // Validate that start date is not after the end date
        if (!isValidDateRange(startDate, endDate)) {
            System.out.println("The start date must be before or equal to the end date.");
            return false;
        }

        // Calculate the number of holiday days
        long days = calculateHolidayDays(startDate, endDate);
        System.out.println(days + " day(s)");

        // Check if the employee has enough holiday balance (optional)
        // Uncomment this part if you need to check the balance before allowing the holiday
        /*
        if (days > employee.getSolde()) {
            System.out.println("Not enough holiday balance!");
            return false;
        }
        */

        // Create and save the holiday
        Holiday newHoliday = new Holiday(id, startDate, endDate, holidayType, employeeId);
        dao.ajouter(newHoliday);

        return true;
    }

    private boolean isValidDateRange(Date startDate, Date endDate) {
        return !startDate.after(endDate);  // Ensure startDate is before or equal to endDate
    }

    private long calculateHolidayDays(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
    
    public List<Holiday> afficherHolidays(){
    	
    	return dao.afficher();    	
    }
}
