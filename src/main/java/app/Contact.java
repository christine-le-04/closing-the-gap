package app;

public class Contact {

    private String name;
    
    private String student_id;

    public Contact(String name, String student_id) {
        this.name = name;
        this.student_id = student_id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getID() {
        return student_id;
    }
    
}
