package DAO;

import java.sql.*;
import java.util.Date.*;

import Model.Holiday;

public class HolidayDAOImpl implements GenericDAOI<Holiday>{
	
	@Override
	public void ajouterHoliday(Holiday holiday) {
		
		String query="insert into holiday (startDate, endDate, holidayTypeId, employeeId) values (?, ?, ?, ?)";
		
		try(PreparedStatement stmt=DBConnection.getConnection().prepareStatement(query)){
			
			stmt.setDate(1, (Date) holiday.getStartDate());
			stmt.setDate(2, (Date) holiday.getEndDate());
			stmt.setString(3, holiday.getHolidayType().name());
			stmt.setInt(4, holiday.getEmployeeId());
			
			int rowAffected=stmt.executeUpdate();
			
			if(rowAffected==0) {
				System.out.println("Echec de l'ionsertion!!");
			}else {
				System.out.println("Insertion reusi :)");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
