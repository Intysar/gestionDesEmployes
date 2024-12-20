package DAO;

import java.util.List;

public interface GenericDAOI<T> {
	
	public void ajouter(T obj);
	public List<T> afficher();
	//public void modifier(int id, T obj);
	//public void supprimer(int id):
	
}
