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
public class Wordel implements ActionListener, Runnable, KeyListener
{
    // instance variables - replace the example below with your own
    JFrame frame;
    JPanel main;
    JTextField[][] box = new JTextField[7][6];
    
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        if(key == e.CHAR_UNDEFINED) {
            
        } else if(key == e.VK_BACK_SPACE) {
            
        } else if(key == e.VK_EQUALS) {
            
        }
    }
    
    public void keyReleased(KeyEvent e) {
        
    }
    
    public void keyTyped(KeyEvent e) {

    }
    
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
        frame.addKeyListener(this);
        
        // Create content panel without a layout manager
        main = new JPanel();
        main.setLayout(null);
        frame.setContentPane(main);
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                box[i][j] = new JTextField();
                box[i][j].setSize(40,40);
                box[i][j].setLocation(50 + (i*50) ,50 + (j*50) );
                main.add(box[i][j]);
            }
        }
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event)
    {
        
    }
}
