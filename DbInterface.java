import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Provides interface between application and database.
 */
public class DbInterface {
    private Connection dbConn = null;
    
    // A constructor that attaches to a PostgreSQL database.
    public DbInterface(String databaseName) {
        String serverUrl = "y1wordle24.c7wwuywsqkcc.us-east-2.rds.amazonaws.com:5432/";
        String user = "coder";
        String password = "coderPW";

        try {
            // Connect to database
            String dbUrl = "jdbc:postgresql://" + serverUrl + databaseName;
            dbConn = DriverManager.getConnection(dbUrl, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (dbConn == null) {
            System.out.println(databaseName + " not connected.");                
        } else {
            System.out.println(databaseName + " connected.");                
        }      
    }

    // Indicate whether database connection is established.
    public boolean isDbConnected() {
        return dbConn != null;
    }
    
    public String getWord() {
        String word = "";
        try {
            Statement s = dbConn.createStatement();
            String sql = 
                    "SELECT current_game_word();";
            System.out.println(sql);
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
               word = rs.getString(1); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return word;
    }
    
    public boolean checkGuess(String guess) {
        boolean isValid = false;
        try {
            Statement s = dbConn.createStatement();
            String sql = 
                    "Select * FROM guess_word WHERE word = '" + guess + "'";
            System.out.println(sql);
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()) {
                isValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
    
    public boolean addHistory(String user, String gameWord, boolean win, int guesses) {
        try {
            Statement s = dbConn.createStatement();
            String sql = 
                    "INSERT INTO result (\"user\", date, game_word, win, guesses) " +
                    "VALUES ('" + user + "', CURRENT_DATE , '" + 
                    gameWord + "'," + win + "," + guesses + ");";
            System.out.println(sql);
            s.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
/*
    // Read all person records from database and return as a list.
    public ArrayList<Wordle> readAllPersons() {
        ArrayList<Wordle> wordleList = new ArrayList<Wordle>();
        
        try {
            Statement s = dbConn.createStatement();
            String sql = 
                    "SELECT current_game_word();";
            System.out.println(sql);
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1));
                Wordle newWordle = new Wordle();
                wordleList.add(newWordle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return wordleList;
    }

    // Add person to database.
    public boolean addPerson(Person newPerson) {
        try {
            Statement s = dbConn.createStatement();
            String sql = 
                    "INSERT INTO person (last_name, first_name, age, title) " +
                    "VALUES ('" + newPerson.getLastName() + "', '" + 
                    newPerson.getFirstName() + "', " + 
                    newPerson.getAge() + "," + "'" + newPerson.getTitle() + "');";
            System.out.println(sql);
            s.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update person in database.
    public boolean updatePerson(Person person) {
        try {
            Statement s = dbConn.createStatement();
            String sql =
                    "UPDATE person " +
                    "SET last_name = '" + person.getLastName() +
                    "', first_name = '" + person.getFirstName() +
                    "', age = " + person.getAge() + 
                    ", title = '" + person.getTitle() +
                    "' WHERE id = " + person.getId() +
                    ";";
            System.out.println(sql);
            int result = s.executeUpdate(sql);
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove person from database.
    public boolean deletePerson(Person personToDelete) {
        try {
            Statement s = dbConn.createStatement();
            String sql = 
                    "DELETE FROM person " +
                    "WHERE id = " + personToDelete.getId() +
                    ";";
            System.out.println(sql);
            int result = s.executeUpdate(sql);
            return result == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    */
}