package lk.ijse.hcj_car_rentsystem.dto;

public class VehicleDTO {
    
    private int vehicleId;
    private String model;
    private String brand;
    private double pricePerDay;
    private String status;
    private String notes;

    public VehicleDTO() {
    }

    public VehicleDTO(String model, String brand, double pricePerDay, String status, String notes) {
        this.model = model;
        this.brand = brand;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.notes = notes;
    }

    public VehicleDTO(int vehicleId, String model, String brand, double pricePerDay, String status, String notes) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.brand = brand;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.notes = notes;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
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
        return "VehicleDTO{" + "vehicleId=" + vehicleId + ", model=" + model + ", brand=" + brand + ", pricePerDay=" + pricePerDay + ", status=" + status + ", notes=" + notes + '}';
    }
    
}
