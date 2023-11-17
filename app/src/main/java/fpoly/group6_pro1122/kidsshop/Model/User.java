package fpoly.group6_pro1122.kidsshop.Model;

public class User {
    private String password;
    private String fullname;
    private String email;
    private String  image;
    private String phone;
    private int role;

    public User() {
    }

    public User(String password, String fullname, String email, String image,String
                phone, int role) {

        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.image = image;
        this.phone = phone;
        this.role = role;
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
