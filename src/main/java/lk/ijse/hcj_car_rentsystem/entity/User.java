package lk.ijse.hcj_car_rentsystem.entity;

public class User {

    private int userId;
    private String name;
    private String user_name;
    private String password;
    private String email;
    private String address;
    private String role;
    private String nic;
    private String contact;

    public User(){

    }
    public User(String name, String userName, String password, String email, String address, String role, String nic, String contact) {
        this.name = name;
        this.user_name = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.nic = nic;
        this.contact = contact;
    }

    public User(int userId, String name, String user_name, String password, String email, String address, String role, String nic, String contact) {
        this.userId = userId;
        this.name = name;
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.nic = nic;
        this.contact = contact;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserName() {
        return user_name;
    }
    public void setUserName(String user_name) {
        this.user_name = user_name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getNic() {
        return nic;
    }
    public void setNic(String nic) {
        this.nic = nic;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
}
