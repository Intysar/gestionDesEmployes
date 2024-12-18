package DAO;

import Model.Employee;
import Model.Employee.Poste;
import Model.Employee.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAOI{
	
	@Override
	public void ajouterEmployee(Employee employee) {
		
		String query=" insert into employe (nom, prenom, email, telephone, salaire, roleId, posteId) values (?, ?, ?, ?, ?, (select id from role where nom=?), (select id from poste where nom=?))";
		
		try (PreparedStatement stmt=DBConnection.getConnection().prepareStatement(query)){
			
			stmt.setString(1, employee.getNom());
			stmt.setString(2, employee.getPrenom());
			stmt.setString(3, employee.getEmail());
			stmt.setString(4, employee.getTelephone());
			stmt.setDouble(5, employee.getSalaire());
			stmt.setString(6, employee.getRole().name());
			stmt.setString(7, employee.getPoste().name());
			
			int rowAffected=stmt.executeUpdate();
			
			if(rowAffected==0) {
				System.out.println("Echec de l'insertion!!");
			}else {
				System.out.println("Insertion reussi :)");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override 
	public List<Employee> afficherEmployees(){
		
		List<Employee> employees=new ArrayList<>();

		String query="SELECT e.id, e.nom, e.prenom, e.email, e.telephone, e.salaire,\r\n"
				+ "       r.nom AS roleNom, p.nom AS posteNom\r\n"
				+ "FROM employe e\r\n"
				+ "JOIN role r ON e.roleId = r.id\r\n"
				+ "JOIN poste p ON e.posteId = p.id;\r\n"
				+ "";
		
		try (PreparedStatement stmt=DBConnection.getConnection().prepareStatement(query)){
			
			ResultSet rs=stmt.executeQuery();
			
			while (rs.next()) {
				int id=rs.getInt("id");
				String nom=rs.getString("nom"), prenom=rs.getString("prenom"), email=rs.getString("email"), telephone=rs.getString("telephone"), role=rs.getString("roleNom"), poste=rs.getString("posteNom");
				double salaire=rs.getDouble("salaire");
				int solde=0;
				Employee newEmployee=new Employee(id, nom, prenom, email, telephone, salaire, Role.valueOf(role), Poste.valueOf(poste), solde);
				
				employees.add(newEmployee);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return employees;
	}
	
	public void modifierEmployee(int id, Employee modifiedEmployee) {
		                                                                                                                                                                                                                                                                                                                                                                                                                                             
		String query="update employe set nom=?, prenom=?, email=?, telephone=?, salaire=?, roleId=(select id from role where nom=?), posteId=(select id from poste where nom=?) where id=?";
		
		try(PreparedStatement stmt=DBConnection.getConnection().prepareStatement(query)){
			
			stmt.setString(1, modifiedEmployee.getNom());
			stmt.setString(2, modifiedEmployee.getPrenom());
			stmt.setString(3, modifiedEmployee.getEmail());
			stmt.setString(4, modifiedEmployee.getTelephone());
			stmt.setDouble(5, modifiedEmployee.getSalaire());
			stmt.setString(6 , modifiedEmployee.getRole().name());
			stmt.setString(7, modifiedEmployee.getPoste().name());
			stmt.setInt(8, id);
			
			int rowAffected=stmt.executeUpdate();
			
			if(rowAffected>0) {
				System.out.println("L'employee a ete modifie :)");
			}else{
				System.out.println("L'employee n'a pas ete modifie :(");
			}
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			
		}
	}
	
	public void supprimerEmployee(int id) {
		
		String query="delete from employe where id=?";
		
		try(PreparedStatement stmt=DBConnection.getConnection().prepareStatement(query)){
			
			stmt.setInt(1, id);
			
			int rowAffected=stmt.executeUpdate();
			
			if(rowAffected>0) {
				System.out.println("L'employee a ete supprime :)");
			}else {
				System.out.println("L'employee n'a pas ete supprime :(");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}	
	
}
