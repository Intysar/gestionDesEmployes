package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Employee;
import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayDAOImpl implements GenericDAOI<Holiday> {

	
	public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            String query = "SELECT id, nom, prenom FROM employe";
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee(0, query, query, query, query, 0, null, null, 0);
                emp.setId(rs.getInt("id"));
                emp.setNom(rs.getString("nom"));
                emp.setPrenom(rs.getString("prenom"));
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return employees;
    }
	
    @Override
    public void ajouter(Holiday holiday) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            
            String queryDays = "select datediff(?, ?) as days_diff";
            stmt = connection.prepareStatement(queryDays);
            stmt.setDate(1, new java.sql.Date(holiday.getEndDate().getTime()));
            stmt.setDate(2, new java.sql.Date(holiday.getStartDate().getTime()));
            rs = stmt.executeQuery();

            int daysDiff = 0;
            if (rs.next()) {
                daysDiff = rs.getInt("days_diff");
            }

            String querySolde = "select solde from employe where id=?";
            stmt = connection.prepareStatement(querySolde);
            stmt.setInt(1, holiday.getEmployeeId());
            rs = stmt.executeQuery();

            int solde = 0;
            if (rs.next()) {
                solde = rs.getInt("solde");
            }

            if (daysDiff <= solde) {
                String query = "insert into holiday (startDate, endDate, holidayTypeId, employeId) values (?, ?, (select id from holidaytype where nom=?), ?)";
                
                stmt = connection.prepareStatement(query);
                
                java.sql.Date sqlStartDate = new java.sql.Date(holiday.getStartDate().getTime());
                java.sql.Date sqlEndDate = new java.sql.Date(holiday.getEndDate().getTime());

                stmt.setDate(1, sqlStartDate);
                stmt.setDate(2, sqlEndDate);
                stmt.setString(3, holiday.getHolidayType().name());
                stmt.setInt(4, holiday.getEmployeeId());

                int rowAffected = stmt.executeUpdate();
                
                if (rowAffected > 0) {
                    String updateSoldeQuery = "update employe set solde=solde-? where id=?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateSoldeQuery);
                    updateStmt.setInt(1, daysDiff);
                    updateStmt.setInt(2, holiday.getEmployeeId());
                    updateStmt.executeUpdate();
                    
                    System.out.println("Insertion reussi :)");
                } else {
                    System.out.println("Echec de l'insertion!!");
                }
            } else {
                System.out.println("Solde insuffisant :(");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (connection != null) connection.close(); } catch (SQLException e) {}
        }
    }
    
    public List<Holiday> afficher(){
    	
    	List<Holiday> holidays=new ArrayList<>();
    	
    	String sql="select \r\n"
    			+ "    e.id as employee_id, \r\n"
    			+ "    e.nom as employee_nom, \r\n"
    			+ "    e.prenom as employee_prenom, \r\n"
    			+ "    e.solde as employee_solde, \r\n"
    			+ "    h.id as holiday_id, \r\n"
    			+ "    h.startdate as start_date, \r\n"
    			+ "    h.enddate as end_date, \r\n"
    			+ "    h.holidaytypeid as holiday_type_id, \r\n"
    			+ "    ht.nom as holiday_type_nom\r\n"
    			+ "from employe e \r\n"
    			+ "join holiday h on e.id = h.employeid\r\n"
    			+ "join holidaytype ht on h.holidaytypeid = ht.id;\r\n"
    			+ "";
    	
    	try(PreparedStatement stmt=DBConnection.getConnection().prepareStatement(sql)){
    		
    		ResultSet rs=stmt.executeQuery();
    		
    		while (rs.next()) {
    		    int employeeId = rs.getInt("employee_id");
    		    int holidayId = rs.getInt("holiday_id");
    		    int holidayTypId = rs.getInt("holiday_type_id");
    		    int solde = rs.getInt("employee_solde");

    		    String nomEmployee = rs.getString("employee_nom") + " " + rs.getString("employee_prenom");
    		    String holidayTypeNom = rs.getString("holiday_type_nom");

    		    Date startDate = rs.getDate("start_date");
    		    Date endDate = rs.getDate("end_date");

    		    Holiday newHoliday = new Holiday(holidayId, startDate, endDate, HolidayType.valueOf(holidayTypeNom), employeeId, nomEmployee);
    		    holidays.add(newHoliday);
    		}

    		
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return holidays;
    }
}
