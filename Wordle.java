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
 * Wordle game class
 *
 * Daniel Rosenzweig
 * 2/26/24
 */
public class Wordle implements Runnable, KeyListener
{
    // instance variables
    JFrame frame;
    JPanel main;
    JTextField[][] box = new JTextField[7][6];
    JTextField[][] keyboard = new JTextField[2][13];
    JTextField wordleLogo;
    JTextField reveal;
    int countX = 0;
    int countY = 0;
    boolean win = false;
    private MyHashTable potentialWords = new MyHashTable();
    static char[] input = new char[5];
    static char[] answer = new char[5];
    static String answer1;
    private DbInterface dbInterface = null;
    private String user = "Daniel";

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
            String str = new String(input);
            if(str.equals(answer1)) {
                colorThatBadBoy(checkCharacters(input, answer));
                win();
                countY = 0;
                dbInterface.addHistory(user, answer1, win, countX);
            } else {
                colorThatBadBoy(checkCharacters(input, answer));
                lose();
                countX++;
                countY = 0;
            }
        }
    }                                               

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    //Displays text congratulating the user for winning the game and ends it
    public void win() {
        Font font3 = new Font("SansSerif", Font.BOLD, 20);
        reveal = new JTextField();
        reveal.setSize(500, 50);
        reveal.setLocation(50, 600);
        reveal.setHorizontalAlignment(JTextField.CENTER);
        reveal.setText("Congratulations bud. The answer was " + answer1);
        reveal.setFont(font3);
        reveal.setBackground(Color.white);
        reveal.setEditable(false);
        main.add(reveal);
        win = true;
    }

    //Displays text informing the user that they lost the game
    public void lose() {
        if(countX == 5 && isValidWord()) {
            Font font3 = new Font("SansSerif", Font.BOLD, 20);
            reveal = new JTextField();
            reveal.setSize(500, 50);
            reveal.setLocation(50, 600);
            reveal.setHorizontalAlignment(JTextField.CENTER);
            reveal.setText("Good try. The answer was " + answer1);
            reveal.setFont(font3);
            reveal.setBackground(Color.white);
            reveal.setEditable(false);
            main.add(reveal);
            dbInterface.addHistory(user, answer1, win, countX);
        }
    }

    //Uses a passed in integer array to determine what to highlight each textbox
    public void colorThatBadBoy(int[] colors) {
        for(int i = 0; i < 5; i++) {
            if(colors[i] == 1) {
                box[i][countX].setBackground(Color.yellow);
            } else if(colors[i] == 2) {
                box[i][countX].setBackground(Color.green);
            } else if(colors[i] == 0) {
                box[i][countX].setBackground(Color.lightGray);
            }
        }
    }
    
    public void colorThatBadBoypt2(int[] colors) {
        
    }

    //Processes and places the entered word into an array of chars
    public void enteredWord() {
        for(int i = 0; i < 5; i++) {
            input[i] = box[i][countX].getText().charAt(0);
        }
    }

    //Sets the text of the textbox to the character pressed
    public void placeCharacter(char k) {
        if (countY < 5) {
            box[countY][countX].setText(k + "");
            countY++;
        }
    }

    //Iterates through the arrays and determines a value for each character that
    //has a corresponding color; returns integer array
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
        return colors;
    }

    //Checks if the entered word is a valid word and is in the txt file
    public boolean isValidWord() {
        String inputWord = "";
        for(int i = 0; i < 5; i++) {
            inputWord += box[i][countX].getText();
        }
        boolean isValid = dbInterface.checkGuess(inputWord);
        return isValid;
    }

    //Creates a keyboard to be displayed
    public void createKeyboard() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Font font2 = new Font("SansSerif", Font.BOLD, 15);
        int count = 0;
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 13; j++) {
                keyboard[i][j] = new JTextField();
                keyboard[i][j].setHorizontalAlignment(JTextField.CENTER);
                keyboard[i][j].setEditable(false);
                keyboard[i][j].setSize(30,30);
                keyboard[i][j].setLocation(35 + (j*40) ,500 + (i*40));
                keyboard[i][j].setBackground(Color.white);
                keyboard[i][j].setFont(font2);
                keyboard[i][j].addKeyListener(this);
                keyboard[i][j].setText(letters.charAt(count) + "");
                count++;
                main.add(keyboard[i][j]);
            }
        }
    }

    //Creates layout for the Wordle game(frame, panels, textboxs, etc.)
    public void run()
    {
        frame = new JFrame();
        frame.setTitle("Wordle");
        frame.setBackground(Color.white);
        frame.setResizable(false);
        main = new JPanel(new BorderLayout(20, 20));
        frame.setContentPane(main);

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

        //Fonts
        Font font = new Font("SansSerif", Font.BOLD, 30);
        Font font2 = new Font("SansSerif", Font.BOLD, 15);

        main = new JPanel();
        main.setLayout(null);
        main.setBackground(Color.white);

        //Creates Wordle logo at the top
        wordleLogo = new JTextField();
        wordleLogo.setHorizontalAlignment(JTextField.CENTER);
        wordleLogo.setText("Wordle");
        wordleLogo.setFont(font);
        wordleLogo.setBackground(Color.white);
        wordleLogo.setEditable(false);
        wordleLogo.addKeyListener(this);
        main.add(wordleLogo);

        frame.setContentPane(main);

        //Creates the textboxes using a 2D array
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
        //Locations and Sizes
        wordleLogo.setSize(150, 50);
        wordleLogo.setLocation(210,15);
        frame.setSize(600,800);
        frame.setLocation(100,100);
        String databaseName = "wordle";
        dbInterface = new DbInterface(databaseName);
        createKeyboard();
        pickAnswer();
        frame.addKeyListener(this);
        frame.setVisible(true);
    }


    public String pickAnswer() {
        answer1 = dbInterface.getWord();
        for(int j = 0; j < 5; j++) {
            answer[j] = answer1.charAt(j);
        }
        return answer1;
    }

    public static void start() {
        SwingUtilities.invokeLater(new Wordle());
    }
}
