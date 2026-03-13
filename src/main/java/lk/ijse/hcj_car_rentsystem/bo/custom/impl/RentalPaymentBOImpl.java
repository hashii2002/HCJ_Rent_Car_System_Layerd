package lk.ijse.hcj_car_rentsystem.bo.custom.impl;

import lk.ijse.hcj_car_rentsystem.bo.custom.RentalPaymentBO;
import lk.ijse.hcj_car_rentsystem.dao.DAOFactory;
import lk.ijse.hcj_car_rentsystem.dao.custom.RentalPaymentDAO;
import lk.ijse.hcj_car_rentsystem.dto.RentalPaymentDTO;
import lk.ijse.hcj_car_rentsystem.entity.RentalPayment;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalPaymentBOImpl implements RentalPaymentBO {
    RentalPaymentDAO rentalPaymentDAO = (RentalPaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RENTALPAYMENT);

    @Override
    public boolean savePayment(RentalPaymentDTO rentalPaymentDTO) throws SQLException {
        return rentalPaymentDAO.save(new RentalPayment(
                rentalPaymentDTO.getBookingId(),
                rentalPaymentDTO.getStartKm(),
                rentalPaymentDTO.getEndKm(),
                rentalPaymentDTO.getTotalKm(),
                rentalPaymentDTO.getTotalAmount(),
                rentalPaymentDTO.getDailyRent(),
                rentalPaymentDTO.getAmountChargedPerKm(),
                rentalPaymentDTO.getPaymentMethod(),
                rentalPaymentDTO.getPaidTime(),
                rentalPaymentDTO.getPaidDate(),
                rentalPaymentDTO.getAdvanceAmount()
        ));
    }

    @Override
    public boolean updatePayment(RentalPaymentDTO rentalPaymentDTO) throws SQLException {
        return rentalPaymentDAO.update(new RentalPayment(
                rentalPaymentDTO.getPaymentId(),
                rentalPaymentDTO.getBookingId(),
                rentalPaymentDTO.getStartKm(),
                rentalPaymentDTO.getEndKm(),
                rentalPaymentDTO.getTotalKm(),
                rentalPaymentDTO.getTotalAmount(),
                rentalPaymentDTO.getDailyRent(),
                rentalPaymentDTO.getAmountChargedPerKm(),
                rentalPaymentDTO.getPaymentMethod(),
                rentalPaymentDTO.getPaidTime(),
                rentalPaymentDTO.getPaidDate(),
                rentalPaymentDTO.getAdvanceAmount()
        ));
    }

    @Override
    public boolean deletePayment(int id) throws SQLException{
        return rentalPaymentDAO.delete(String.valueOf(id));
    }

    @Override
    public RentalPaymentDTO searchPayment(int id) throws SQLException{
        RentalPayment payment = rentalPaymentDAO.search(String.valueOf(id));

        if (payment != null) {
            return new RentalPaymentDTO(
                    payment.getPaymentId(),
                    payment.getBookingId(),
                    payment.getStartKm(),
                    payment.getEndKm(),
                    payment.getTotalKm(),
                    payment.getTotalAmount(),
                    payment.getDailyRent(),
                    payment.getAmountChargedPerKm(),
                    payment.getPaymentMethod(),
                    payment.getPaidTime(),
                    payment.getPaidDate(),
                    payment.getAdvanceAmount()
            );
        }
        return null;
    }

    @Override
    public RentalPaymentDTO searchPaymentByBookingID (int bookingId) throws SQLException{
        RentalPaymentDTO payment = rentalPaymentDAO.searchPaymentByBookingID(bookingId);

        if (payment != null) {
            return new RentalPaymentDTO(
                    payment.getPaymentId(),
                    payment.getBookingId(),
                    payment.getStartKm(),
                    payment.getEndKm(),
                    payment.getTotalKm(),
                    payment.getTotalAmount(),
                    payment.getDailyRent(),
                    payment.getAmountChargedPerKm(),
                    payment.getPaymentMethod(),
                    payment.getPaidTime(),
                    payment.getPaidDate(),
                    payment.getAdvanceAmount()
            );
        }
        return null;
    }

    @Override
    public List<RentalPaymentDTO> getPayments() throws SQLException{
        List<RentalPayment> payments = rentalPaymentDAO.get();
        List<RentalPaymentDTO> dtoList = new ArrayList<>();

        for (RentalPayment p : payments) {
            dtoList.add(new RentalPaymentDTO(
                    p.getPaymentId(),
                    p.getBookingId(),
                    p.getStartKm(),
                    p.getEndKm(),
                    p.getTotalKm(),
                    p.getTotalAmount(),
                    p.getDailyRent(),
                    p.getAmountChargedPerKm(),
                    p.getPaymentMethod(),
                    p.getPaidTime(),
                    p.getPaidDate(),
                    p.getAdvanceAmount()
            ));
        }
        return dtoList;
    }

    @Override
    public void printPaymentInvoice(int paymentId) throws JRException, SQLException {
        rentalPaymentDAO.printPaymentInvoice(paymentId);
    }
}
