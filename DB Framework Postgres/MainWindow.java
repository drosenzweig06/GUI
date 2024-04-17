import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import java.util.ArrayList;

/**
 * Main window for application.
 */
public class MainWindow implements ActionListener, Runnable {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel databaseNameLabel;
    private JButton connectButton;
    private JButton listButton;
    private DbInterface dbInterface = null;
    
    public void run() {
        // Create top-level container
        frame = new JFrame();
        frame.setSize(300, 200);
        frame.setLocation(100,100);
        frame.setTitle("Main Window");
        frame.setResizable(false);

        // Create content panel without a layout manager
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        frame.setContentPane(mainPanel);

        // Setup buttons
        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);

        listButton = new JButton("List");
        listButton.addActionListener(this);

        // Setup text fields and labels
        Font font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

        databaseNameLabel = new JLabel("Not connected to database");
        databaseNameLabel.setFont(font1);
        databaseNameLabel.setHorizontalAlignment(JLabel.LEFT);

        databaseNameLabel.setLocation(10, 10);
        connectButton.setLocation(100, 50);
        listButton.setLocation(100, 100);

        databaseNameLabel.setSize(250, 30);
        connectButton.setSize(100, 30);
        listButton.setSize(100, 30);

        mainPanel.add(databaseNameLabel);
        mainPanel.add(connectButton);
        mainPanel.add(listButton);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == connectButton) {
            String databaseName = "sample";

            dbInterface = new DbInterface(databaseName);
                
            if (dbInterface.isDbConnected()) {
                databaseNameLabel.setText("Connected to " + databaseName);                
            } else {
                databaseNameLabel.setText("Not connected to " + databaseName);
            }
        }

        if (event.getSource() == listButton) {
            if (dbInterface == null || !dbInterface.isDbConnected()) {
                JOptionPane.showMessageDialog(frame, "No connection.");
            } else {
                new ListWindow(this, dbInterface);
                connectButton.setEnabled(false);
                listButton.setEnabled(false);
            }
        }
    }

    public void closePopUp() {
        // Re-enable window
        connectButton.setEnabled(true);
        listButton.setEnabled(true);
    }

    public static void main() {
        SwingUtilities.invokeLater(new MainWindow());
    }
}
