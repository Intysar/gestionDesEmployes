
import Controller.LoginController;
import DAO.LoginDAO;
import Model.LoginModel;
import View.Login;

public class MainLogin {
    public static void main(String[] args) {
    	
    	LoginDAO dao = new LoginDAO();
        Login view = new Login();
        LoginModel model = new LoginModel(dao);
        new LoginController(view, model);
    }
}
