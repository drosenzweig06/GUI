import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Write a description of class GUIProject here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ConversionCalculator implements ActionListener, Runnable
{
    JFrame frame;
    JPanel main;
    JLabel label1, label2;
    JTextField From, To;
    String[] units;
    JButton convert;
    

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
        label1.setSize(100,30);
        label2.setSize(100,30);
        From.setSize(150,20);
        To.setSize(150,20);
        label1.setLocation(10,50);
        label2.setLocation(175,50);
        From.setLocation(10,70);
        To.setLocation(175,70);
        
        main.add(label1);
        main.add(label2);
        main.add(From);
        main.add(To);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event)
    {
        
    }
    
    public static void start() {
       SwingUtilities.invokeLater(new ConversionCalculator());
    }
}
