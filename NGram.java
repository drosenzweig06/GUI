import java.io.File;
import java.util.Scanner;
import java.util.*;

/**
 * Write a description of class NGram here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NGram
{
    private String[] words;
    private MyHashTable<String, ArrayList<String>> potentialWords = new MyHashTable<String, ArrayList<String>>();
    private int murphcon = 8;
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public static void readText()
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
            System.out.println(Arrays.toString(words));
        }
        catch(Exception e) {
            System.out.println("Cannot find file");
        }
        
    }
    
    public static void buildWords(String start) {
            for(int j = 0; j < words.length; j++) { 
                String history = start;
                for(int i = j; i < j + murphcon; i++) {
                    if(i < words.length - 1) {
                        history += words[i] + " ";
                        if(potentialWords.get(history) == null) {
                            ArrayList<String> values = new ArrayList<String>();
                            String value = words[i+1];
                            values.add(value);
                            potentialWords.put(history, values);
                        } else {
                            ArrayList<String> values = potentialWords.get(history);
                            String value = words[i+1];
                            values.add(value);
                            potentialWords.put(history, values);
                        }
                    }
                }
            }
    }
    
    public static void run() {
        
    }
}
