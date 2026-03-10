package lk.ijse.hcj_car_rentsystem.entity;

public class Customer {

    private int customerId;
    private int userId;
    private String name;
    private String address;
    private String contact;
    private String nicPassport;
    private String licenseNo;

    public Customer() {

    }
    public Customer(int customerId, int userId, String name, String address, String contact, String nicPassport, String licenseNo) {
        this.customerId = customerId;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nicPassport = nicPassport;
        this.licenseNo = licenseNo;
    }

    public Customer(String name, String address, String contact, String nicPassport, String licenseNo) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nicPassport = nicPassport;
        this.licenseNo = licenseNo;
    }

    public Customer(int userId, String name, String address, String contact, String nicPassport, String licenseNo) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nicPassport = nicPassport;
        this.licenseNo = licenseNo;
    }

    public Customer(String name, String address, String contact, String nicPassport, String licenseNo, int customerId) {
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getNicPassport() {
        return nicPassport;
    }
    public void setNicPassport(String nicPassport) {
        this.nicPassport = nicPassport;
    }
    public String getLicenseNo() {
        return licenseNo;
    }
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }
}
