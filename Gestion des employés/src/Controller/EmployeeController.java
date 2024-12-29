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

    public EmployeeController(EmployeeView view, EmployeeModel model) {
        this.model = model;
        this.view = view;
        afficherEmployees();

       
        this.view.ajouterButton.addActionListener(e -> {
            ajouterEmployee();
            clearFields();
            afficherEmployees();
        });

        this.view.modifierButton.addActionListener(e -> {
            modifierEmployee();
            clearFields();
            afficherEmployees();
        });

        this.view.supprimerButton.addActionListener(e -> {
            supprimerEmployee();
            afficherEmployees();
        });

        this.view.afficherButton.addActionListener(e -> afficherEmployees());

        this.view.importButton.addActionListener(e -> importerEmployees());
        this.view.exportButton.addActionListener(e -> exporterEmployees());
    }

    public void ajouterEmployee() {
        int solde = 0;
        int id = 0;
        String nom = view.getNom(), prenom = view.getPrenom(), email = view.getEmail(), telephone = view.getTelephone();
        double salaire = view.getSalaire();
        Role role = view.getRole();
        Poste poste = view.getPoste();

        boolean ajoutReussi = model.ajouterEmployee(id, nom, prenom, email, telephone, salaire, role, poste, solde);

        if (ajoutReussi) {
            view.afficherMessageSucces("Employee ajouté avec succès :)");
        } else {
            view.afficherMessageErreur("Échec de l'ajout :(");
        }
    }

    public void afficherEmployees() {
        List<Employee> employees = model.afficherEmployees();
        view.afficherEmployees(employees);
    }

    public void modifierEmployee() {
        int solde = 0;

        int id = view.getId();

        if (id <= 0) {
            view.afficherMessageErreur("Sélectionnez un employé pour le modifier.");
            return;
        }

        String nom = view.getNom(), prenom = view.getPrenom(), email = view.getEmail(), telephone = view.getTelephone();
        double salaire = view.getSalaire();
        Role role = view.getRole();
        Poste poste = view.getPoste();

        boolean modificationReussite = model.modifierEmployee(id, nom, prenom, email, telephone, salaire, role, poste, solde);

        if (modificationReussite) {
            view.afficherMessageSucces("L'employé a été modifié :)");
        } else {
            view.afficherMessageErreur("L'employé n'a pas été modifié :(");
        }
    }

    public void supprimerEmployee() {
        int id = view.getId();

        if (id <= 0) {
            view.afficherMessageErreur("Sélectionnez un employé pour le supprimer.");
            return;
        }

        boolean suppressionReussi = model.supprimerEmployee(id);

        if (suppressionReussi) {
            view.afficherMessageSucces("L'employé a été supprimé :)");
        } else {
            view.afficherMessageErreur("L'employé n'a pas été supprimé :(");
        }
    }

    public void importerEmployees() {
        String filePath = view.showFileChooser("Importer des employés");
        if (filePath != null) {
            boolean importationReussie = model.importerEmployees(filePath);
            if (importationReussie) {
                view.afficherMessageSucces("Les employés ont été importés avec succès :)");
                afficherEmployees();
            } else {
                view.afficherMessageErreur("Échec de l'importation des employés :(");
            }
        }
    }

    public void exporterEmployees() {
    	int rowCount = view.jt.getRowCount();
        boolean exportSuccess = true;

        for (int i = 0; i < rowCount; i++) {
            int id = Integer.parseInt(view.jt.getValueAt(i, 0).toString());
            String nom = view.jt.getValueAt(i, 1).toString();
            String prenom = view.jt.getValueAt(i, 2).toString();
            String email = view.jt.getValueAt(i, 3).toString();
            String telephone = view.jt.getValueAt(i, 4).toString();
            double salaire = Double.parseDouble(view.jt.getValueAt(i, 5).toString());
            Role role = Role.valueOf(view.getRole().toString()); 
            Poste poste = Poste.valueOf(view.getPoste().toString()); 
            int solde = 0; 

            boolean ajoutReussi = model.ajouterEmployee(id, nom, prenom, email, telephone, salaire, role, poste, solde);
            if (!ajoutReussi) {
                exportSuccess = false;
            }
        }

        if (exportSuccess) {
            view.afficherMessageSucces("Exportation des données vers la base de données réussie !");
        } else {
            view.afficherMessageErreur("Une ou plusieurs erreurs se sont produites lors de l'exportation.");
        }
    }

    private void clearFields() {
        view.jtfNom.setText(null);
        view.jtfPrenom.setText(null);
        view.jtfEmail.setText(null);
        view.jtfTelephone.setText(null);
        view.jtfSalaire.setText(null);
    }
}
