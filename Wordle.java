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
    static char[] input;
    static char[] answer;
    static String answer1;
    
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        if (key != e.CHAR_UNDEFINED && Character.isLetter(key)) {
            placeCharacter(key);
        } else if (e.getKeyCode() == e.VK_BACK_SPACE &&
                    countY <= 5 && countY >= 1) {
            countY--;
            box[countY][countX].setText("");
        } else if (e.getKeyCode() == e.VK_ENTER && countY == 5) {
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
    
    public char[] enteredWord() {
        for(int i = 0; i < 5; i++) {
            input[i] = box[i][countX].getText().charAt(1);
        }
        System.out.println(input);
        return input;
    }
    
    public void placeCharacter(char k) {
        if(countY < 5) {
            box[countY][countX].setText(k + "");
            countY++;
        }
    }
    
    public static int[] checkCharacters(char[] input, char[] answer) {
        int[] colors = new int[5];
        for(int i = 0; i < 5; i++) {
            if(input[i] == answer[i]) {
                colors[i] = 2;
            }
        }
        for(int j = 0; j < 5; j++) {
            for(int k = 0; k < 5; k++) {
                if(input[j] == answer[k] && colors[j] != 2) {
                    colors[j] = 1;
                }
            }
        }
        System.out.println(input);
        System.out.println(answer);
        return colors;
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
                box[i][j].setHorizontalAlignment(JTextField.CENTER);
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
        answer1 = potentialWords.get(i);
        for(int j = 0; j < 5; j++) {
            answer[j] = answer1.charAt(j);
        }
        return answer1;
    }
    
    public static void start() {
       SwingUtilities.invokeLater(new Wordle());
    }
}
