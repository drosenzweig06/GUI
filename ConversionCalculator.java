import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 * Write a description of class GUIProject here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ConversionCalculator implements ActionListener, Runnable
{
    private JButton button;
    JFrame frame;
    JPanel main;
    JLabel label1, label2;
    JTextField From, To;
    String[] units;
    double[] values;
    JButton convert;
    JComboBox unitsList;
    JComboBox unitsList2;
    

    public void run() {
        frame = new JFrame();
        frame.setSize(300,200);
        frame.setLocation(100,100);
        frame.setTitle("Window Title");
        frame.setResizable(false);
        main = new JPanel(new BorderLayout(20, 20));
        frame.setContentPane(main);
        
        // Create content panel without a layout manager
        main = new JPanel();
        main.setLayout(null);
        frame.setContentPane(main);
        
        //Convert Button
        button = new JButton("Convert");
        button.setMnemonic(KeyEvent.VK_D);
        button.addActionListener(this);
        
        //Units of conversion
        units = new String[9];
        units[0] = "Kilometer";
        units[1] = "Meter";
        units[2] = "Centimeter";
        units[3] = "Millimeter";
        units[4] = "Mile";
        units[5] = "Yard";
        units[6] = "Foot";
        units[7] = "Inch";
        units[8] = "Light Year";
        
        //ComboBox for units
        unitsList = new JComboBox(units);
        unitsList2 = new JComboBox(units);
        int index = unitsList.getSelectedIndex();
        
        

        //Fonts
        Font font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        
        //Text From->To
        From = new JTextField();
        label1 = new JLabel("From:");
        label1.setFont(font1);
        label1.setVerticalAlignment(JLabel.TOP);
        
        To = new JTextField();
        label2 = new JLabel("To:");
        label2.setFont(font1);
        label2.setVerticalAlignment(JLabel.TOP);
        
        
        //Locations and Sizes
        
        button.setSize(80,20);
        unitsList.setSize(90,30);
        unitsList2.setSize(90,30);
        label1.setSize(100,30);
        label2.setSize(100,30);
        From.setSize(70,20);
        To.setSize(70,20);
        button.setLocation(87,70);
        unitsList.setLocation(10,100);
        unitsList2.setLocation(175,100);
        label1.setLocation(30,50);
        label2.setLocation(195,50);
        From.setLocation(10,70);
        To.setLocation(175,70);
        
        main.add(label1);
        main.add(label2);
        main.add(From);
        main.add(To);
        main.add(unitsList);
        main.add(unitsList2);
        main.add(button);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == button) {
            
        }
    }
    
    public void conversion(String first, String second) {
        double[] values = {1.0, 1000.0, 10000.0, 100000.0, .621371, 1093.61, 3280.84, 
            39370.1, 1.057};
    }
    
    public static void start() {
       SwingUtilities.invokeLater(new ConversionCalculator());
    }
}
