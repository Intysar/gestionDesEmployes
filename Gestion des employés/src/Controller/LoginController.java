package Controller;

import Model.LoginModel;
import View.Login;

import javax.swing.JOptionPane;

public class LoginController {

    private Login view;
    private LoginModel model;

    public LoginController(Login view, LoginModel model) {
        this.model = model;
        this.view = view;

        
        this.view.submitButton.addActionListener(e -> {
            login();
        });
    }
    

    public void login() {
        String email = view.getEmail();
        String password = view.getPassword();

        boolean loginOrNot = model.login(email, password);

        if (loginOrNot) {
            JOptionPane.showMessageDialog(view, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            //new MainLogin();
        } else {
            JOptionPane.showMessageDialog(view, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
