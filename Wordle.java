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
    JTextField wordleLogo;
    int countX = 0;
    int countY = 0;
    private MyHashTable potentialWords = new MyHashTable();
    static char[] input = new char[5];
    static char[] answer = new char[5];
    static String answer1;
    
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        if (key != e.CHAR_UNDEFINED && Character.isLetter(key)) {
            placeCharacter(key);
        } else if (e.getKeyCode() == e.VK_BACK_SPACE &&
                    countY <= 5 && countY >= 1) {
            countY--;
            box[countY][countX].setText("");
        } else if (e.getKeyCode() == e.VK_ENTER && countY == 5 
        && isValidWord()) {
            enteredWord();
            colorThatBadBoy(checkCharacters(input, answer));
            countX++;
            countY = 0;
        }
    }                                               
    
    public void keyReleased(KeyEvent e) {
        
    }
    
    public void keyTyped(KeyEvent e) {

    }
    
    public void colorThatBadBoy(int[] colors) {
        for(int i = 0; i < 5; i++) {
            if(colors[i] == 1) {
                box[i][countX].setBackground(Color.yellow);
            } else if(colors[i] == 2) {
                box[i][countX].setBackground(Color.green);
            } else if(colors[i] == 0) {
                box[i][countX].setBackground(Color.gray);
            }
        }
    }
    
    public void enteredWord() {
        for(int i = 0; i < 5; i++) {
            input[i] = box[i][countX].getText().charAt(0);
        }
    }
    
    public void placeCharacter(char k) {
        if (countY < 5) {
            box[countY][countX].setText(k + "");
            countY++;
        }
    }
    
    public int[] checkCharacters(char[] input, char[] answer) {
        int[] colors = new int[5];
        char[] tempAnswer = Arrays.copyOf(answer, 5);
        for(int j = 0; j < 5; j++) {
            if (input[j] == tempAnswer[j]) {
                colors[j] = 2;
                tempAnswer[j] = '.';
            }
        }
        for(int k = 0; k < 5; k++) {
            for(int l = 0; l < 5; l++) {
                if (input[k] == tempAnswer[l] && colors[k] != 2) {
                    colors[k] = 1;
                    tempAnswer[l] = '.';
                } 
            }
        }
        System.out.println(Arrays.toString(colors));
        return colors;
    }
    
    public boolean isValidWord() {
        String inputWord = "";
        for(int i = 0; i < 5; i++) {
            inputWord += box[i][countX].getText();
        }
        if(potentialWords.get(inputWord) != null) {
            return true;
        } else if(potentialWords.get(inputWord) == null) {
            return false;
        } else {
            return true;
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
        frame.setTitle("Wordle");
        frame.setBackground(Color.white);
        frame.setResizable(false);
        main = new JPanel(new BorderLayout(20, 20));
        frame.setContentPane(main);
        
        //Create arraylist
        try {
            File file = new File("wordle-guess-words.txt");
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                String word = scan.nextLine();
                potentialWords.put(word,word);
            }
            scan.close();
        } catch(FileNotFoundException e) {
            System.out.println("Error");
        }
        
        // Create content panel without a layout manager
        Font font = new Font("SansSerif", Font.BOLD, 30);
        Font font2 = new Font("SansSerif", Font.BOLD, 15);
        main = new JPanel();
        main.setLayout(null);
        main.setBackground(Color.white);
        wordleLogo = new JTextField();
        wordleLogo.setSize(150, 50);
        wordleLogo.setLocation(210,15);
        wordleLogo.setHorizontalAlignment(JTextField.CENTER);
        wordleLogo.setText("Wordle");
        wordleLogo.setFont(font);
        wordleLogo.setBackground(Color.white);
        wordleLogo.setEditable(false);
        wordleLogo.addKeyListener(this);
        main.add(wordleLogo);
        frame.setContentPane(main);
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
                box[i][j] = new JTextField();
                box[i][j].setHorizontalAlignment(JTextField.CENTER);
                box[i][j].setEditable(false);
                box[i][j].setSize(50,50);
                box[i][j].setLocation(120 + (i*70) ,80 + (j*70));
                box[i][j].setBackground(Color.white);
                box[i][j].setFont(font2);
                box[i][j].addKeyListener(this);
                main.add(box[i][j]);
            }
        }
        pickAnswer();
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
