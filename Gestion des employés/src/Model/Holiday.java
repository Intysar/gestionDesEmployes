package Model;

import java.util.Date;

public class Holiday {
	
	private int id;
	private Date startDate, endDate;
	private HolidayType holidayType;
	private int employeeId;
	private String employeeNom;
	private String selectedItem;
	
	public Holiday(int id, Date startDate, Date endDate, HolidayType holidayType, int employeeId){
		this.id=id;
		this.startDate=startDate;
		this.endDate=endDate;
		this.holidayType=holidayType;
		this.employeeId=employeeId;
	}
	
	public Holiday (int id, Date startDate, Date endDate, HolidayType holidayType, int employeeId, String employeeNom){
		this.id=id;
		this.startDate=startDate;
		this.endDate=endDate;
		this.holidayType=holidayType;
		this.employeeId=employeeId;
		this.employeeNom=employeeNom;
	}
	
    public Holiday(String employeeNom, Date startDate, Date endDate, HolidayType holidayType) {
        this.employeeNom = employeeNom;
        this.startDate = startDate;
        this.endDate = endDate;
        this.holidayType = holidayType;
    }
	
    public Holiday(int id, Date startDate, Date endDate, HolidayType holidayType, String employeeNom) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.holidayType = holidayType;
        this.employeeNom = employeeNom;
    }


	public int getId() {return id;}
	public Date getStartDate() {return startDate;}
	public Date getEndDate() {return endDate;}
	public HolidayType getHolidayType() {return holidayType;}
	public int getEmployeeId() {return employeeId;}
	public String getEmployeeNom() {return employeeNom;}
	public String getSelectedItem() {
		return selectedItem;
	}//i'll remove this later
	
	
	public enum HolidayType {
		CONGE_PAYEE, 
		CONGE_NON_PAYEE,
		CONGE_MALADIE
	}

}
