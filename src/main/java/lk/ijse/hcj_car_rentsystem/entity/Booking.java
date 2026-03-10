package lk.ijse.hcj_car_rentsystem.entity;

public class Booking {

    private int bookingId;
    private int customerId;
    private int vehicleId;
    private int driverId;
    private String bookingDate;
    private String startDate;
    private String endDate;
    private String bookingStatus;
    private double advanceAmount;
    private String notes;
    private String driverAssign;

    public Booking() {
    }

    public Booking(int customerId, int vehicleId, int driverId, String bookingDate, String startDate, String endDate, String bookingStatus, double advanceAmount, String notes, String driverAssign) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingStatus = bookingStatus;
        this.advanceAmount = advanceAmount;
        this.notes = notes;
        this.driverAssign = driverAssign;
    }

    public Booking(int bookingId, int customerId, int vehicleId, int driverId, String bookingDate, String startDate, String endDate, String bookingStatus, double advanceAmount, String notes, String driverAssign) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingStatus = bookingStatus;
        this.advanceAmount = advanceAmount;
        this.notes = notes;
        this.driverAssign = driverAssign;
    }

    public Booking(int customerId, String bookingDate, String startDate, String endDate, String bookingStatus, double advanceAmount, String notes, String driverAssign) {
    }

    public Booking(int customerId, String bookingDate, String startDate, String endDate, String bookingStatus, double advanceAmount, String notes, String driverAssign, int bookingId) {
    }

    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    public int getDriverId() {
        return driverId;
    }
    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    public String getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public double getAdvanceAmount() {
        return advanceAmount;
    }
    public void setAdvanceAmount(double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getDriverAssign() {
        return driverAssign;
    }
    public void setDriverAssign(String driverAssign) {
        this.driverAssign = driverAssign;
    }
}
