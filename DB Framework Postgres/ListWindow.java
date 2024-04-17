import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.util.ArrayList;

/**
 * Window for listing persons.
 */
public class ListWindow implements ActionListener {
    private JList personJList;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton closeButton;
    private JScrollPane personListScrollPane;
    private JFrame frame;

    private MainWindow parentWindow;
    private DbInterface dbInterface;
    private ArrayList<Person> personList;
    
    public ListWindow(MainWindow aParentWindow, DbInterface aDbInterface) {
        parentWindow = aParentWindow;
        dbInterface = aDbInterface;
        
        // Create top-level container
        frame = new JFrame();
        frame.setSize(340, 250);
        frame.setLocation(400,150);
        frame.setTitle("Person List");
        frame.setResizable(true);
        
        // Create content panel without a layout manager
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        frame.setContentPane(mainPanel);

        // Create list
        buildPersonJList();

        // Create scroll pane for list
        personListScrollPane = new JScrollPane(personJList);
        
        // Create buttons and pane
        addButton = new JButton("Add");
        addButton.addActionListener(this);

        editButton = new JButton("Edit");
        editButton.addActionListener(this);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);

        personListScrollPane.setSize(315, 100);
        addButton.setSize(100, 30);
        editButton.setSize(100, 30);
        deleteButton.setSize(100, 30);
        closeButton.setSize(100, 30);

        personListScrollPane.setLocation(5, 10);
        addButton.setLocation(5, 120);
        editButton.setLocation(110, 120);
        deleteButton.setLocation(215, 120);
        closeButton.setLocation(110, 160);
        
        mainPanel.add(personListScrollPane);
        mainPanel.add(addButton);
        mainPanel.add(editButton);
        mainPanel.add(deleteButton);
        mainPanel.add(closeButton);
        
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == addButton) {
            // Open another window
            new EditWindow(this, dbInterface, null);
            addButton.setEnabled(false);
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            closeButton.setEnabled(false);
            personJList.setEnabled(false);
        }
        if (event.getSource() == editButton) {
            // Open another window
            new EditWindow(this, dbInterface,
                    personList.get(personJList.getSelectedIndex()));
            addButton.setEnabled(false);
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            closeButton.setEnabled(false);
            personJList.setEnabled(false);
        }
        if (event.getSource() == deleteButton) {
            // Get the selected person
            Person selectedPerson = personList.get(personJList.getSelectedIndex());
            
            // Utilize dialog box to confirm delete
            // Returns 0 if yes was clicked
            if (JOptionPane.showConfirmDialog(frame,
                    "Do you want to delete " + selectedPerson.getLastName() + 
                    ", " + selectedPerson.getFirstName() + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION) == 0) {
                if (dbInterface.deletePerson(selectedPerson)) {
                    JOptionPane.showMessageDialog(frame, "Person deleted.");

                    // Create new list for scrollpane
                    buildPersonJList();
        
                    // Update scrollpane with new list
                    personListScrollPane.setViewportView(personJList);
                } else {
                    JOptionPane.showMessageDialog(frame, "Error occurred.");
                }
            }
        }
        if (event.getSource() == closeButton) {
            parentWindow.closePopUp();
            frame.dispose();
        }
    }
    
    public void closePopUp() {
        // Create new list for scrollpane
        buildPersonJList();
        
        // Update scrollpane with new list
        personListScrollPane.setViewportView(personJList);
        
        // Re-enable window
        addButton.setEnabled(true);
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        closeButton.setEnabled(true);
        personJList.setEnabled(true);
    }
    
    private void buildPersonJList() {
        personList = dbInterface.readAllPersons();
        String[] persons = new String[personList.size()];
        for (int i=0; i<personList.size(); i++) {
            Person currPerson = personList.get(i);
            persons[i] = currPerson.getLastName() + ", " + 
                    currPerson.getFirstName() + 
                    " (" + currPerson.getAge() + ")" + ", " +
                        currPerson.getTitle();
        }
        personJList = new JList(persons);
        personJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personJList.setLayoutOrientation(JList.VERTICAL);
        personJList.setSelectedIndex(0);
    }
}