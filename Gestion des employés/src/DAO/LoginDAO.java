package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {
	
	public boolean login(String email, String password) {
		
        String query = "SELECT COUNT(*) FROM employe WHERE email = ? AND password = ?";

        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
            
            	System.out.println("loged in");
                return true; 
            }else {
            	System.out.println("didn't log in");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; 
    }

}
