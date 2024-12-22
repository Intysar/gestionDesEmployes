package View;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.Employee;
import Model.Holiday;
import Model.Holiday.HolidayType;

public class HolidayView extends JFrame{

	JPanel jp1=new JPanel(), jp2=new JPanel(), jp3=new JPanel(), jp4=new JPanel();
	
	private JLabel jlNom=new JLabel("Id de l'employe : "), jlType=new JLabel("Type : "), jlDateDebut=new JLabel("Date de debut : "), jlDateFin=new JLabel("Date de fin : ");
	//private JComboBox employees=new JComboBox<>();
	private JTextField jtfEmployeeId=new JTextField();
    private JComboBox<String> employeeComboBox = new JComboBox<>(); // ComboBox for employee names

	private JComboBox<HolidayType> holidayType=new JComboBox<>(HolidayType.values());
	private Calendar calendar=Calendar.getInstance();
	
	private JSpinner startDateSpinner=new JSpinner(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH));
	private JSpinner.DateEditor startDateEditor=new JSpinner.DateEditor(startDateSpinner, "dd/mm/yyyy");//startDateSpinner is going to be added
	//dateSpinner.setEditor(editor);//should be inside an instructor or methode
	
	private JSpinner endDateSpinner=new JSpinner(new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH));
	private JSpinner.DateEditor endDateEditor=new JSpinner.DateEditor(endDateSpinner, "dd/mm/yyyy");
	
	private DefaultTableModel tableModel=new DefaultTableModel(new Object[] [] {}, new String[] {"Id", "Employe", "Date de bebut", "Date de fin", "Type"});
	private JTable jt=new JTable(tableModel);
	
	public JButton ajouterButton=new JButton("Ajouter"), modifierButton=new JButton("Modifier"), supprimerButton=new JButton("Supprimer"), afficherButton=new JButton("Afficher");
	
	public HolidayView(){
		
		setTitle("Gestion des conges");
		setLocationRelativeTo(null);
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(jp1);
		jp1.setLayout(new BorderLayout());
		jp1.add(jp2, BorderLayout.NORTH);
		jp2.setLayout(new GridLayout(4, 2));
		jp2.add(jlNom);
		jp2.add(jtfEmployeeId);
		jp2.add(jlType);
		jp2.add(holidayType);
		startDateSpinner.setEditor(startDateEditor);
		jp2.add(jlDateDebut);
		endDateSpinner.setEditor(endDateEditor);
		jp2.add(startDateSpinner);
		jp2.add(jlDateFin);
		jp2.add(endDateSpinner);
		
		
		
		jp1.add(jp3, BorderLayout.CENTER);
		jp3.setLayout(new BorderLayout());
		JScrollPane jsp=new JScrollPane(jt);
		jp3.add(jsp, BorderLayout.CENTER);
		
		jp1.add(jp4, BorderLayout.SOUTH);
		jp4.setLayout(new FlowLayout());
		jp4.add(ajouterButton);
		jp4.add(modifierButton);
		jp4.add(supprimerButton);
		jp4.add(afficherButton);
		
		//setVisible(true);
		
		
		
	}
	

    public void setEmployeeList(List<Employee> employees) {
        employeeComboBox.removeAllItems();
        for (Employee emp : employees) {
            employeeComboBox.addItem(emp.getId() + " - " + emp.getNom() + " " + emp.getPrenom());
        }
    }
	
	public int getEmployeeId() {
	    String text = jtfEmployeeId.getText();
	    if (text.isEmpty()) {
	        throw new NumberFormatException("Employee ID cannot be empty.");
	    }
	    try {
	        return Integer.parseInt(text);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Please enter a valid Employee ID.", "Invalid input", JOptionPane.ERROR_MESSAGE);
	        return -1; 
	    }
	}
	
	public void afficherHolidays(List<Holiday> holidays) {
		tableModel.setRowCount(0);
		for (Holiday elm : holidays) {
		    tableModel.addRow(new Object[] {
		        elm.getId(),
		        elm.getEmployeeNom(),
		        elm.getStartDate(),
		        elm.getEndDate(),
		        elm.getHolidayType().name()
		    });
		}
	}

	public HolidayType getHolidayType() {
		return (HolidayType) holidayType.getSelectedItem();
	}
	public Date getStartDate() {
	    return (Date) startDateSpinner.getValue();
	}

	public Date getEndDate() {
	    return (Date) endDateSpinner.getValue();
	}
	
    public void afficherMessageErreur(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void afficherMessageSucces(String message) {
        JOptionPane.showMessageDialog(this, message, "Succes", JOptionPane.INFORMATION_MESSAGE);
    }
	
}
