import java.io.File;
import java.util.Scanner;

/**
 * Write a description of class NGram here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NGram
{
    private String[] words;
    private MyHashTable potentialWords = new MyHashTable();
    private int murphcon = 8;
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void readText()
    {
        try{
            File test = new File("Ngrams.txt");
            Scanner scan = new Scanner(test);
            StringBuilder string = new StringBuilder(1000);
            while(scan.hasNextLine()) {
                string.append(scan.nextLine() + " ");
            }
            scan.close();
            words = string.toString().split("\s+");
            buildWords();
        }
        catch(Exception e) {
            System.out.println("Cannot find file");
        }
    }
    
    public void buildWords() {
            for(int j = 0; j < words.length; j++) { 
                String history = "";
                for(int i = j; i < j + murphcon; i++) {
                    history += words[i] + " ";
                    String value = words[i+1];
                    //System.out.println(history + "    " + value);
                    potentialWords.put(history, value);
                }
            }
    }
}
