package fpoly.group6_pro1122.kidsshop.Model;

public class User {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String  image;
    private int role;

    public User() {
    }

    public User(String username, String password, String fullname, String email, String image, int role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.image = image;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
