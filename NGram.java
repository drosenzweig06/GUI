import java.io.File;

/**
 * Write a description of class NGram here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NGram
{
    // instance variables - replace the example below with your own
    //history is the key to the hash table
    private int x;

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
            StringBuilder string = new StringBuilder(100);
            string.append(string);
        }
        catch(Exception e) {
            System.out.println("Cannot find file");
        }
    }
}
