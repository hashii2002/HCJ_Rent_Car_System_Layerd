package lk.ijse.hcj_car_rentsystem.dao.custom;

import lk.ijse.hcj_car_rentsystem.dao.CrudDAO;
import lk.ijse.hcj_car_rentsystem.dto.RentalPaymentDTO;
import lk.ijse.hcj_car_rentsystem.entity.RentalPayment;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;

public interface RentalPaymentDAO extends CrudDAO<RentalPayment> {

   // public boolean savePayment(RentalPaymentDTO rentalPaymentDTO) throws SQLException;
    //public boolean updatePayment(RentalPaymentDTO rentalPaymentDTO) throws SQLException;
    //public boolean deletePayment(int id) throws SQLException;
    //public RentalPaymentDTO searchPayment(int id) throws SQLException;
    public RentalPaymentDTO searchPaymentByBookingID (int cusId) throws SQLException;
    //public List<RentalPaymentDTO> getPayments() throws SQLException;
    public void printPaymentInvoice(int paymentId) throws JRException, SQLException;

    RentalPayment search(RentalPayment id) throws SQLException;
}
