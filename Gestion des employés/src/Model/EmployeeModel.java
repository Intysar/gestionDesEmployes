package Model;

import DAO.EmployeeDAOImpl;
import Model.Employee.Role;
import Model.Employee.Poste;

import java.util.List;

public class EmployeeModel {
	
	private EmployeeDAOImpl dao;
	
	public EmployeeModel(EmployeeDAOImpl dao) {
		this.dao=dao;
	}
	
	public boolean ajouterEmployee(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste, int solde) {
		
		if(!email.contains("@")) {
			System.out.println("Erreur : email invalide!");
			return false;
		}
		
		if(!telephone.matches("\\d{10}")) {
			System.out.println("Erreur : numero de telephone invalide!");
			return false;
		}
		
		if(salaire<=0) {
			System.out.println("Erreur : salaire invalide!");
			return false;
		}
		
		Employee nouveauEmployee=new Employee(id, nom, prenom, email, telephone, salaire, role, poste, solde);
		
		dao.ajouterEmployee(nouveauEmployee);
		
		return true;
		
	}
	
	public List<Employee> afficherEmployees(){
		
		return dao.afficherEmployees();
		
	}
	
	public boolean modifierEmployee(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste, int solde) {
		
		if(!email.contains("@")) {
			System.out.println("Email invalide!!");
			return false;
		}
		
		if(!telephone.matches("\\d{10}")) {
			System.out.println("Telephone invalide!!");
			return false;
		}
		
		if (salaire<=0) {
			System.out.println("Salaire invalid!!");
			return false;
		}
		
		Employee modifiedEmployee=new Employee(id, nom, prenom, email, telephone, salaire, role, poste, solde);
		
		dao.modifierEmployee(id, modifiedEmployee);
		
		return true;
	}
	
	public boolean supprimerEmployee(int id) {
		
		if(id<=0) {
			System.out.println("Id invalide!!");
			return false;
		}
		
		dao.supprimerEmployee(id);
		
		return true;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
