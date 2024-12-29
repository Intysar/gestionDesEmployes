package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Employee;
import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayDAOImpl implements GenericDAOI<Holiday> {
	
    @Override
    public void ajouter(Holiday holiday) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            int employeeId = getEmployeeIdByName(holiday.getEmployeeNom());
            if (employeeId == -1) {
                System.out.println("Erreur : Employe introuvable.");
                return;
            }
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
            stmt.setInt(1, employeeId);
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
                stmt.setInt(4, employeeId);

                int rowAffected = stmt.executeUpdate();
                
                if (rowAffected > 0) {
                    String updateSoldeQuery = "update employe set solde=solde-? where id=?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateSoldeQuery);
                    updateStmt.setInt(1, daysDiff);
                    updateStmt.setInt(2, employeeId);
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

    		    Holiday newHoliday = new Holiday(holidayId, startDate, endDate, HolidayType.valueOf(holidayTypeNom), employeeId, nomEmployee, solde);
    		    holidays.add(newHoliday);
    		}

    		
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return holidays;
    }
    
    public boolean modifier(Holiday holiday) {
        String sql = "UPDATE holiday SET startDate = ?, endDate = ?, holidayTypeId = (select id from holidaytype where nom=?) WHERE id = ?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(holiday.getStartDate().getTime()));
            stmt.setDate(2, new java.sql.Date(holiday.getEndDate().getTime()));
            stmt.setString(3, holiday.getHolidayType().name());
            stmt.setInt(4, holiday.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    /*public void modifier(int holidayId, Holiday modifiedHoliday) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            
            int employeeId = getEmployeeIdByName(modifiedHoliday.getEmployeeNom());
            if (employeeId == -1) {
                System.out.println("Erreur : Employé introuvable.");
                return;
            }

            String queryDays = "select datediff(?, ?) as days_diff";
            stmt = connection.prepareStatement(queryDays);
            stmt.setDate(1, new java.sql.Date(modifiedHoliday.getEndDate().getTime()));
            stmt.setDate(2, new java.sql.Date(modifiedHoliday.getStartDate().getTime()));
            rs = stmt.executeQuery();

            int newDaysDiff = 0;
            if (rs.next()) {
                newDaysDiff = rs.getInt("days_diff");
            }

            String querySolde = "select solde from employe where id=?";
            stmt = connection.prepareStatement(querySolde);
            stmt.setInt(1, employeeId);
            rs = stmt.executeQuery();

            int solde = 0;
            if (rs.next()) {
                solde = rs.getInt("solde");
            }

            String queryOldDays = "select datediff(endDate, startDate) as old_days_diff from holiday where id=?";
            stmt = connection.prepareStatement(queryOldDays);
            stmt.setInt(1, holidayId);
            rs = stmt.executeQuery();

            int oldDaysDiff = 0;
            if (rs.next()) {
                oldDaysDiff = rs.getInt("old_days_diff");
            }

            int daysDiffAdjustment = newDaysDiff - oldDaysDiff;
            if (solde + oldDaysDiff >= newDaysDiff) {

            	String updateHolidayQuery = "update holiday set startDate=?, endDate=?, holidayTypeId=(select id from holidaytype where nom=?) where id=?";
                stmt = connection.prepareStatement(updateHolidayQuery);
                stmt.setDate(1, new java.sql.Date(modifiedHoliday.getStartDate().getTime()));
                stmt.setDate(2, new java.sql.Date(modifiedHoliday.getEndDate().getTime()));
                stmt.setString(3, modifiedHoliday.getHolidayType().name());
                stmt.setInt(4, holidayId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {

                	String updateSoldeQuery = "update employe set solde=solde-? where id=?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateSoldeQuery);
                    updateStmt.setInt(1, daysDiffAdjustment);
                    updateStmt.setInt(2, employeeId);
                    updateStmt.executeUpdate();

                    System.out.println("Modification réussie :)");
                } else {
                    System.out.println("Echec de la modification :(");
                }
            } else {
                System.out.println("Solde insuffisant pour effectuer la modification :(");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public List<String> chargerNomsEmployes() {
    	
        List<String> employeeNames = new ArrayList<>();
        
        String query = "SELECT CONCAT(nom, ' ', prenom) AS fullName FROM employe";

        try (Connection conn = DBConnection.getConnection();
        		
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	
                String fullName = rs.getString("fullName");
                employeeNames.add(fullName);
                
            }
            
        } catch (SQLException e) {
        	
            System.err.println("Erreur lors de la recuperation des noms des employes : " + e.getMessage());
            
        }

        return employeeNames;
    }
    
    public int getEmployeeIdByName(String employeeName) {
    	
    	String query="SELECT id FROM employe WHERE CONCAT(nom, ' ', prenom)=?";
    	
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query)) {
            stmt.setString(1, employeeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recuperation de l'ID employe : " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    
    public void supprimer(int id) {
    	
    	String query="delete from holiday where id=?";
    	
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Conge supprime avec succes.");
            } else {
                System.out.println("Aucun conge trouve avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du conge : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void exporterHolidays(String filePath) {
        List<Holiday> holidays = afficher(); 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Id,Nom Employe,Date Debut,Date Fin,Type,Solde\n");
            for (Holiday holiday : holidays) {
                writer.write(String.format("%d,%s,%s,%s,%s,%d\n",
                    holiday.getId(),
                    holiday.getEmployeeNom(),
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(holiday.getStartDate()),
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(holiday.getEndDate()),
                    holiday.getHolidayType(),
                    holiday.getSolde()
                ));
            }
            System.out.println("Exportation réussie !");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'exportation !");
        }
    }

    public void importerHolidays(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String employeeNom = parts[1];
                    Date startDate = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(parts[2]);
                    Date endDate = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(parts[3]);
                    Holiday.HolidayType holidayType = Holiday.HolidayType.valueOf(parts[4]);
                    ajouter(new Holiday(employeeNom, startDate, endDate, holidayType));
                }
            }
            System.out.println("Importation réussie !");
        } catch (IOException | java.text.ParseException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'importation !");
        }
    }
    
}
