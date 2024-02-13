import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JTextField;

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
        
        //Units/String array
        String[] units = {"Kilometer", "Meter", "Centimeter", "Millimeter", "Mile", "Yard", "Foot", "Inch", 
        "Light Year"};
        
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
        To.setEditable(false);
        
        
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
                double conversion = conversion(From.getText(),
                unitsList.getSelectedIndex(), unitsList2.getSelectedIndex());
                To.setText(conversion + ""); 
        }
    }
    
    public double conversion(String stringValue, int index, int index2) {
        double value = Double.parseDouble(stringValue);
        double[] values = {1000, 1, 0.01, 0.001, 1609.34, 0.9144, 0.3048, 0.0254, 
        9.461*Math.pow(10,15)};
        
        double toMeters = value * values[index];
        return Math.round(toMeters/values[index2])/100.0;
    }
    
    public static void start() {
       SwingUtilities.invokeLater(new ConversionCalculator());
    }
}
