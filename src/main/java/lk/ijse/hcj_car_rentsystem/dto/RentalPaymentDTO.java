package lk.ijse.hcj_car_rentsystem.dto;

public class RentalPaymentDTO {
    
    private int paymentId;
    private int bookingId;
    private String startKm;
    private String endKm;
    private String totalKm;
    private double totalAmount;
    private double dailyRent;
    private double amountChargedPerKm;
    private String paymentMethod;
    private String paidTime;
    private String paidDate;
    private double advanceAmount;

    public RentalPaymentDTO() {
    }

    public RentalPaymentDTO(int bookingId, String startKm, String endKm, String totalKm, double totalAmount, double dailyRent, double amountChargedPerKm, String paymentMethod, String paidTime, String paidDate, double advanceAmount) {
        this.bookingId = bookingId;
        this.startKm = startKm;
        this.endKm = endKm;
        this.totalKm = totalKm;
        this.totalAmount = totalAmount;
        this.dailyRent = dailyRent;
        this.amountChargedPerKm = amountChargedPerKm;
        this.paymentMethod = paymentMethod;
        this.paidTime = paidTime;
        this.paidDate = paidDate;
        this.advanceAmount = advanceAmount;
    }

    public RentalPaymentDTO(int paymentId, int bookingId, String startKm, String endKm, String totalKm, double totalAmount, double dailyRent, double amountChargedPerKm, String paymentMethod, String paidTime, String paidDate, double advanceAmount) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.startKm = startKm;
        this.endKm = endKm;
        this.totalKm = totalKm;
        this.totalAmount = totalAmount;
        this.dailyRent = dailyRent;
        this.amountChargedPerKm = amountChargedPerKm;
        this.paymentMethod = paymentMethod;
        this.paidTime = paidTime;
        this.paidDate = paidDate;
        this.advanceAmount = advanceAmount;
    }
    

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getStartKm() {
        return startKm;
    }

    public void setStartKm(String startKm) {
        this.startKm = startKm;
    }

    public String getEndKm() {
        return endKm;
    }

    public void setEndKm(String endKm) {
        this.endKm = endKm;
    }

    public String getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(String totalKm) {
        this.totalKm = totalKm;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getDailyRent() {
        return dailyRent;
    }

    public void setDailyRent(double dailyRent) {
        this.dailyRent = dailyRent;
    }

    public double getAmountChargedPerKm() {
        return amountChargedPerKm;
    }

    public void setAmountChargedPerKm(double amountChargedPerKm) {
        this.amountChargedPerKm = amountChargedPerKm;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(String paidTime) {
        this.paidTime = paidTime;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public double getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(double advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    @Override
    public String toString() {
        return "RentalPaymentDTO{" + "paymentId=" + paymentId + ", bookingId=" + bookingId + ", startKm=" + startKm + ", endKm=" + endKm + ", totalKm=" + totalKm + ", totalAmount=" + totalAmount + ", dailyRent=" + dailyRent + ", amountChargedPerKm=" + amountChargedPerKm + ", paymentMethod=" + paymentMethod + ", paidTime=" + paidTime + ", paidDate=" + paidDate + ", advanceAmount=" + advanceAmount + '}';
    }
    
}
