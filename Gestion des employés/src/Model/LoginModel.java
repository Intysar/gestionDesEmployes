package Model;

import DAO.LoginDAO;

public class LoginModel {

	private LoginDAO dao;
	
	public LoginModel(LoginDAO dao) {
		this.dao=dao;
	}
	
	public boolean login(String email, String password) {
		
		if(!email.contains("@") || email==null || password==null) {
			System.out.println("Erreur : email invalide!");
			return false;
		}
		
		dao.login(email, password);
		
		return true;
		
	}
}
