package Controller;

import Model.EmployeeModel;
import Model.Employee;
import Model.Employee.Role;
import Model.Employee.Poste;
import View.EmployeeView;

import java.util.List;

public class EmployeeController {
	
	private EmployeeView view;
	private EmployeeModel model;
	
	public EmployeeController(EmployeeView view, EmployeeModel model){
		this.model=model;
		this.view=view;
		
		this.view.ajouterButton.addActionListener(e -> ajouterEmployee());
		this.view.afficherButton.addActionListener(e -> afficherEmployees());
		this.view.modifierButton.addActionListener(e -> modifierEmployee());
		this.view.supprimerButton.addActionListener(e -> supprimerEmployee());
	}
	
	public void ajouterEmployee() {
		int solde=0;
		int id=0;
		String nom=view.getNom(), prenom=view.getPrenom(), email=view.getEmail(), telephone=view.getTelephone();
		double salaire=view.getSalaire();
		Role role=view.getRole();
		Poste poste=view.getPoste();
		
		boolean ajoutReussi=model.ajouterEmployee(id, nom, prenom, email, telephone, salaire, role, poste, solde);
		
		if(ajoutReussi) {
			view.afficherMessageSucces("Employee ajoute avec succes :)");
		}else {
			view.afficherMessageErreur("Echec de l'ajout :(");
		}
	}
	
	public void afficherEmployees() {
		
		List<Employee> employees=model.afficherEmployees();
		view.afficherEmployees(employees);
	}
	
	public void modifierEmployee() {
		
		int solde=0;
         
		int id=view.getId();
		
	    if (id<=0) {
	        view.afficherMessageErreur("Selectionnez un employee pour le modifier.");
	        return;
	    }
	    
		String nom=view.getNom(), prenom=view.getPrenom(), email=view.getEmail(), telephone=view.getTelephone();
		double salaire=view.getSalaire();
		Role role=view.getRole();
		Poste poste=view.getPoste();
		
		boolean modificationReussite=model.modifierEmployee(id, nom, prenom, email, telephone, salaire, role, poste, solde);
		
		if(modificationReussite) {
			view.afficherMessageSucces("L'employee a ete modifie :)");
		}else {
			view.afficherMessageErreur("L'employee n'a pas ete modifie :(");
		}
	}
	
	public void supprimerEmployee() {
		
		int id=view.getId();
		
		if(id<=0) {
			view.afficherMessageErreur("Selectionner un employee pour le supprimer.");
			return;
		}
		
		boolean suppressionReussi=model.supprimerEmployee(id);
		
		if(suppressionReussi) {
			view.afficherMessageSucces("L'employee a ete supprimer :)");
		}else {
			view.afficherMessageErreur("L'emloyee n'a pas ete supprime :(");
		}
	}
	
}
