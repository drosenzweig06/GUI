import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Window for editing a person.
 */
public class EditWindow implements ActionListener {
    private ListWindow parentWindow;
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel ageLabel;
    private JLabel titleLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField titleField;
    private JTextField ageField;
    private JButton saveButton;
    private JButton cancelButton;
    private DbInterface dbInterface;
    private Person person;
    
    public EditWindow(ListWindow aParentWindow, DbInterface aDbInterface,
            Person aPerson) {
        // Keep track of previous window
        parentWindow = aParentWindow;
        dbInterface = aDbInterface;
        person = aPerson;
        
        // Create top-level container
        frame = new JFrame();
        frame.setSize(300, 230);
        frame.setLocation(300,300);
        frame.setTitle("Edit Person");
        frame.setResizable(false);
        
        // Create content panel without a layout manager
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        frame.setContentPane(mainPanel);
        
        // Setup buttons
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        // Setup text fields and labels
        Font font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        
        firstNameField = new JTextField();
        firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(font1);
        firstNameLabel.setHorizontalAlignment(JLabel.RIGHT);

        lastNameField = new JTextField();
        lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(font1);
        lastNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        ageField = new JTextField();
        ageLabel = new JLabel("Age:");
        ageLabel.setFont(font1);
        ageLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        titleField = new JTextField();
        titleLabel = new JLabel("Title:");
        titleLabel.setFont(font1);
        titleLabel.setHorizontalAlignment(JLabel.RIGHT);
        
        firstNameLabel.setLocation(10, 10);
        lastNameLabel.setLocation(10, 40);
        ageLabel.setLocation(10, 70);
        titleLabel.setLocation(10, 100);
        firstNameField.setLocation(120, 15);
        lastNameField.setLocation(120, 45);
        ageField.setLocation(120, 75);
        titleField.setLocation(120, 105);
        saveButton.setLocation(5, 150);
        cancelButton.setLocation(110, 150);
        
        firstNameLabel.setSize(100, 30);
        lastNameLabel.setSize(100, 30);
        ageLabel.setSize(100, 30);
        titleLabel.setSize(100, 30);
        titleField.setSize(150, 20);
        firstNameField.setSize(150, 20);
        lastNameField.setSize(150, 20);
        ageField.setSize(150, 20);
        saveButton.setSize(100, 30);
        cancelButton.setSize(100, 30);
        
        mainPanel.add(firstNameLabel);
        mainPanel.add(lastNameLabel);
        mainPanel.add(ageLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(firstNameField);
        mainPanel.add(lastNameField);
        mainPanel.add(titleField);
        mainPanel.add(ageField);
        mainPanel.add(saveButton);
        mainPanel.add(cancelButton);

        if (person != null) {
            populateScreenFromPerson();
        }
        
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == saveButton) {
            if (person != null) {
                populatePersonFromScreen();
                if (dbInterface.updatePerson(person)) {
                    JOptionPane.showMessageDialog(frame, "Person updated.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Error occurred.");
                }
            } else {
                person = new Person();
                populatePersonFromScreen();
                if (dbInterface.addPerson(person)) {
                    JOptionPane.showMessageDialog(frame, "Person added.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Error occurred.");
                }
            }
            parentWindow.closePopUp();
            frame.dispose();
        }
        if (event.getSource() == cancelButton) {
            parentWindow.closePopUp();
            frame.dispose();
        }
    }
    
    private void populateScreenFromPerson() {
        lastNameField.setText(person.getLastName());
        firstNameField.setText(person.getFirstName());
        ageField.setText(Integer.toString(person.getAge()));
        titleField.setText(person.getTitle());
    }

    private void populatePersonFromScreen() {
        person.setLastName(lastNameField.getText());
        person.setFirstName(firstNameField.getText());
        person.setTitle(titleField.getText());
        if (!ageField.getText().equals("")) {
            person.setAge(Integer.parseInt(ageField.getText()));
        }
    }
}