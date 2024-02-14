import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Scanner;
import java.io.File;
import java.util.Random;

/**
 * Write a description of class Wordel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Wordle implements ActionListener, Runnable, KeyListener
{
    // instance variables - replace the example below with your own
    JFrame frame;
    JPanel main;
    JTextField[][] box = new JTextField[7][6];
    int countX = 0;
    int countY = 0;
    private MyHashTable potentialWords = new MyHashTable();
    private ArrayList<String> words = new ArrayList<String>();
    
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        if (key != e.CHAR_UNDEFINED && Character.isLetter(key)) {
            placeCharacter(key);
        } else if (e.getKeyCode() == e.VK_BACK_SPACE &&
                    countX <= 5 && countX >= 1) {
            countX--;
            box[countX][countY].setText("");
        } else if (e.getKeyCode() == e.VK_ENTER && countX == 5) {
            // if(checkValidWord() == true) {
                // countX = 0;
                // countY++; d
            // }
            countX = 0;
            countY++;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        
    }
    
    public void keyTyped(KeyEvent e) {

    }
    
    public void placeCharacter(char k) {
        if(countX < 5) {
            box[countX][countY].setText(k + "");
            countX++;
        }
    }
    
    public void checkCharacters() {
        
    }
    
    public void checkValidWord() {
        String word = "";
        for(int i = 0; i < words.size() - 1; i++) {
            potentialWords.put(potentialWords.get(i),i);
        }
        
        for(int j = 0; j < 5; j++) {
            word += box[j][countY].getText();
        }
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
        
        //Create arraylist
        try {
            File file = new File("wordle-guess-words.txt");
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                String word = scan.nextLine();
                words.add(word);
            }
            scan.close();
        } catch(FileNotFoundException e) {
            System.out.println("Error");
        }
        
        // Create content panel without a layout manager
        main = new JPanel();
        main.setLayout(null);
        frame.setContentPane(main);
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                box[i][j] = new JTextField();
                box[i][j].setEditable(false);
                box[i][j].setSize(40,40);
                box[i][j].setLocation(50 + (i*50) ,50 + (j*50));
                box[i][j].addKeyListener(this);
                main.add(box[i][j]);
            }
        }
        frame.addKeyListener(this);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event)
    {
        
    }

    public static String pickAnswer() {
        ArrayList<String> potentialWords = new ArrayList<String>();
        Random rand = new Random();
        try {
            File words = new File("wordle-game-words.txt");
            Scanner scan = new Scanner(words);
            while(scan.hasNextLine()) {
                String word = scan.nextLine();
                potentialWords.add(word);
            }
            scan.close();
        } catch(FileNotFoundException e) {
            System.out.println("Error");
        }
        int i = rand.nextInt(potentialWords.size());
        String answer = potentialWords.get(i);
        System.out.println(answer);
        return answer;
    }
    
    public static void start() {
       SwingUtilities.invokeLater(new Wordle());
    }
}
