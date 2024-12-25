package Model;

public class Employee {
	
	private int id;
	private String nom, prenom, email, telephone;
	private double salaire;
	private Role role;
	private Poste poste;
	private int solde;
	
	public Employee(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste, int solde) {
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.email=email;
		this.telephone=telephone;
		this.role=role;
		this.poste=poste;
		this.solde=solde;
	}
	
	public int getId() {
		return id;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public String getEmail() {
		return email;
	}
	public String getTelephone() {
		return telephone;
	}
	public double getSalaire() {
		return salaire;
	}
	public Role getRole() {
		return role;
	}
	public Poste getPoste() {
		return poste;
	}
	public int getSolde() {
		return solde;
	}
	
	public enum Role{
		ADMIN,
		EMPLOYEE,
	}
	
	public enum Poste{
		INGENIEUR,
		TEAM_LEADER,
		PILOTE,
	}

	public void setId(int int1) {
		id=int1;
	}

	public void setNom(String string) {
		nom=string;
	}

	public void setPrenom(String string) {
		prenom=string;
	}
}
