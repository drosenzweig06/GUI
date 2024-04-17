/**
 * Class representing a person.
 */
public class Person {
    private int id;
    private String lastName;
    private String firstName;
    private int age;
    private String title;
    
    public Person() {
        id = 0;
        lastName = "";
        firstName = "";
        age = 0;
        title = "";
    }
    
    public Person(int aId, String aLastName, String aFirstName, int aAge,
    String title){
        id = aId;
        lastName = aLastName;
        firstName = aFirstName;
        age = aAge;
        this.title = title;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int aId) {
        id = aId;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String aLastName) {
        lastName = aLastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String aFirstName) {
        firstName = aFirstName;
    }
    
    public int getAge() {
        return age;
    }

    public void setAge(int aAge) {
        age = aAge;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String aTitle) {
        title = aTitle;
    }
}
