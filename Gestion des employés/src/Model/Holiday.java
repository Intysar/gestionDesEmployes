package Model;

import java.util.Date;

public class Holiday {
	
	private int id;
	private Date startDate, endDate;
	private HolidayType holidayType;
	private int employeeId;
	
	Holiday(int id, Date startDate, Date endDate, HolidayType holidayType, int employeeId){
		this.id=id;
		this.startDate=startDate;
		this.endDate=endDate;
		this.holidayType=holidayType;
		this.employeeId=employeeId;
	}
	
	public int getId() {return id;}
	public Date getStartDate() {return startDate;}
	public Date getEndDate() {return endDate;}
	public HolidayType getHolidayType() {return holidayType;}
	public int getEmployeeId() {return employeeId;}
	
	
	public enum HolidayType {
		CONGE_PAYEE, 
		CONGE_NON_PAYEE,
		CONGE_MALADIE
	}

}
