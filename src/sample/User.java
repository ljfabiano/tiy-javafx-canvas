package sample;

/**
 * Created by jfabiano on 9/9/2016.
 */
public class User {
    int id;
    String username;
    String fullname;//make sure to pass the user to each controller that needs it

    public User()
    {

    }

    public User(String username, String fullname)
    {
        //this.id = id;
        this.username = username;
        this.fullname = fullname;
    }

    public User(int id, String username, String fullname)
    {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
