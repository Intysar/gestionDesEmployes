package Model;

import DAO.EmployeeDAOImpl;
import Model.Employee.Role;
import Model.Employee.Poste;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    private EmployeeDAOImpl dao;

    public EmployeeModel(EmployeeDAOImpl dao) {
        this.dao = dao;
    }

    public boolean ajouterEmployee(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste, int solde) {

        if (!email.contains("@")) {
            System.out.println("Erreur : email invalide!");
            return false;
        }

        if (!telephone.matches("\\d{10}")) {
            System.out.println("Erreur : numero de telephone invalide!");
            return false;
        }
        

        if (salaire <= 0) {
            System.out.println("Erreur : salaire invalide!");
            return false;
        }

        Employee nouveauEmployee = new Employee(id, nom, prenom, email, telephone, salaire, role, poste, solde);

        dao.ajouterEmployee(nouveauEmployee);

        return true;
    }

    public List<Employee> afficherEmployees() {
        return dao.afficherEmployees();
    }

    public boolean modifierEmployee(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste, int solde) {

        if (!email.contains("@")) {
            System.out.println("Email invalide!!");
            return false;
        }

        if (!telephone.matches("\\d{10}")) {
            System.out.println("Telephone invalide!!");
            return false;
        }

        if (salaire <= 0) {
            System.out.println("Salaire invalid!!");
            return false;
        }

        Employee modifiedEmployee = new Employee(id, nom, prenom, email, telephone, salaire, role, poste, solde);

        dao.modifierEmployee(id, modifiedEmployee);

        return true;
    }

    public boolean supprimerEmployee(int id) {

        if (id <= 0) {
            System.out.println("Id invalide!!");
            return false;
        }

        dao.supprimerEmployee(id);

        return true;
    }

    public boolean importerEmployees(String filePath) throws NumberFormatException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<Employee> importedEmployees = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 9) {
                    System.out.println("Invalid data format in file.");
                    return false;
                }

                int id = Integer.parseInt(fields[0].trim());
                String nom = fields[1].trim();
                String prenom = fields[2].trim();
                String email = fields[3].trim();
                String telephone = fields[4].trim();
                double salaire = Double.parseDouble(fields[5].trim());
                Role role = Role.valueOf(fields[6].trim());
                Poste poste = Poste.valueOf(fields[7].trim());
                int solde = Integer.parseInt(fields[8].trim());

                Employee employee = new Employee(id, nom, prenom, email, telephone, salaire, role, poste, solde);
                importedEmployees.add(employee);
            }

            for (Employee employee : importedEmployees) {
                dao.ajouterEmployee(employee);
            }

            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Erreur lors de l'importation des employés: " + e.getMessage());
            return false;
        }
    }

    public boolean exporterEmployees(String filePath) {
        List<Employee> employees = dao.afficherEmployees();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                String line = String.format("%d,%s,%s,%s,%s,%.2f,%s,%s,%d",
                        employee.getId(),
                        employee.getNom(),
                        employee.getPrenom(),
                        employee.getEmail(),
                        employee.getTelephone(),
                        employee.getSalaire(),
                        employee.getRole().name(),
                        employee.getPoste().name(),
                        employee.getSolde());
                writer.write(line);
                writer.newLine();
            }

            return true;
        } catch (IOException e) {
            System.out.println("Erreur lors de l'exportation des employés: " + e.getMessage());
            return false;
        }
    }
}
