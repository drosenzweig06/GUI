import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 * Write a description of class Wordel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Wordel implements ActionListener, Runnable
{
    // instance variables - replace the example below with your own
    JFrame frame;
    JPanel main;
    JTextField box;
    /**
     * Constructor for objects of class Worlde
     */
    public void run()
    {
        frame = new JFrame();
        frame.setSize(600,800);
        frame.setLocation(100,100);
        frame.setTitle("Window Title");
        frame.setResizable(false);
        main = new JPanel(new BorderLayout(20, 20));
        frame.setContentPane(main);
        
        // Create content panel without a layout manager
        main = new JPanel();
        main.setLayout(null);
        frame.setContentPane(main);
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                box = new JTextField();
                box.setSize(40,40);
                box.setLocation(50 * j,50);
                main.add(box);
            }
        }
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event)
    {
        
    }
}
