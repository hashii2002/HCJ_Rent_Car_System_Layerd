package lk.ijse.hcj_car_rentsystem.dto;

public class DriverDTO {
    
    private int driverId;
    private String name;
    private String contact;
    private String nic;
    private String licenseNo;
    private String address;
    private String status;
    private String notes;

    public DriverDTO() {
    }

    public DriverDTO(String name, String contact, String nic, String licenseNo, String address, String status, String notes) {
        this.name = name;
        this.contact = contact;
        this.nic = nic;
        this.licenseNo = licenseNo;
        this.address = address;
        this.status = status;
        this.notes = notes;
    }

    public DriverDTO(int driverId, String name, String contact, String nic, String licenseNo, String address, String status, String notes) {
        this.driverId = driverId;
        this.name = name;
        this.contact = contact;
        this.nic = nic;
        this.licenseNo = licenseNo;
        this.address = address;
        this.status = status;
        this.notes = notes;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "DriverDTO{" + "driverId=" + driverId + ", name=" + name + ", contact=" + contact + ", nic=" + nic + ", licenseNo=" + licenseNo + ", address=" + address + ", status=" + status + ", notes=" + notes + '}';
    }
    
    
}
