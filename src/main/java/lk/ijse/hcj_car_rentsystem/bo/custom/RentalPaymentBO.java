package lk.ijse.hcj_car_rentsystem.bo.custom;

import lk.ijse.hcj_car_rentsystem.bo.SuperBO;
import lk.ijse.hcj_car_rentsystem.dto.RentalPaymentDTO;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.List;

public interface RentalPaymentBO extends SuperBO {
    public boolean savePayment(RentalPaymentDTO rentalPaymentDTO) throws SQLException;
    public boolean updatePayment(RentalPaymentDTO rentalPaymentDTO) throws SQLException;
    public boolean deletePayment(int id) throws SQLException;
    public RentalPaymentDTO searchPayment(int id) throws SQLException;
    public RentalPaymentDTO searchPaymentByBookingID (int cusId) throws SQLException;
    public List<RentalPaymentDTO> getPayments() throws SQLException;
    public void printPaymentInvoice(int paymentId) throws JRException, SQLException;
}
