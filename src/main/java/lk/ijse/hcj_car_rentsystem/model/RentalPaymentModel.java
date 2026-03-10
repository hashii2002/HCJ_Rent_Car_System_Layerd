//package lk.ijse.hcj_car_rentsystem.model;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import lk.ijse.hcj_car_rentsystem.db.DBConnection;
//import lk.ijse.hcj_car_rentsystem.dto.RentalPaymentDTO;
//import lk.ijse.hcj_car_rentsystem.util.CrudUtil;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.view.JasperViewer;
//
//public class RentalPaymentModel {
//
//    public boolean savePayment(RentalPaymentDTO rentalPaymentDTO) throws SQLException  {
//
//        boolean result =
//                CrudUtil.execute(
//                        "INSERT INTO payments (booking_id, start_km , end_km, total_amount, daily_rent, amount_charged_per_1km, payment_method, paid_date, advance_amount) VALUES (?,?,?,?,?,?,?,?,?)",
//
//                        rentalPaymentDTO.getBookingId(),
//                        rentalPaymentDTO.getStartKm(),
//                        rentalPaymentDTO.getEndKm(),
//                        rentalPaymentDTO.getTotalAmount(),
//                        rentalPaymentDTO.getDailyRent(),
//                        rentalPaymentDTO.getAmountChargedPerKm(),
//                        rentalPaymentDTO.getPaymentMethod(),
//                        rentalPaymentDTO.getPaidDate(),
//                        rentalPaymentDTO.getAdvanceAmount()
//                );
//
//        return result;
//    }
//
//    public boolean updatePayment(RentalPaymentDTO rentalPaymentDTO) throws SQLException {
//
//        boolean result =
//                CrudUtil.execute(
//                        "UPDATE payments SET start_km=? , end_km=? , total_amount=? , daily_rent=?, amount_charged_per_1km=?, payment_method=? , paid_time=?, paid_date=?, advance_amount=? WHERE payment_id=?",
//
//                        rentalPaymentDTO.getStartKm(),
//                        rentalPaymentDTO.getEndKm(),
//                        rentalPaymentDTO.getTotalAmount(),
//                        rentalPaymentDTO.getDailyRent(),
//                        rentalPaymentDTO.getAmountChargedPerKm(),
//                        rentalPaymentDTO.getPaymentMethod(),
//                        rentalPaymentDTO.getPaidTime(),
//                        rentalPaymentDTO.getPaidDate(),
//                        rentalPaymentDTO.getAdvanceAmount(),
//                        rentalPaymentDTO.getPaymentId()
//
//
//                );
//
//        return result;
//    }
//
//    public boolean deletePayment(int id) throws SQLException {
//        boolean result =
//                CrudUtil.execute(
//                        "DELETE FROM payments WHERE payment_id=?",
//                        id
//                );
//
//        return result;
//    }
//
//    public RentalPaymentDTO searchPayment(int id) throws SQLException {
//
//        ResultSet rs =
//                CrudUtil.execute(
//                        "SELECT * FROM payments WHERE payment_id=?",
//                        id
//                );
//
//        if(rs.next()) {
//            int paymentId = rs.getInt("payment_id");
//            int bookingId = rs.getInt("booking_id");
//            String startKm = rs.getString("start_km");
//            String endKm = rs.getString("end_km");
//            String totalKm = rs.getString("total_km");
//            double totalAmount = rs.getDouble("total_amount");
//            double dailyRent = rs.getDouble("daily_rent");
//            double amountChargedPerKm = rs.getDouble("amount_charged_per_1km");
//            String paymentMethod = rs.getString("payment_method");
//            String paidTime = rs.getString("paid_time");
//            String paidDate = rs.getString("paid_date");
//            double advanceAmount = rs.getDouble("advance_amount");
//
//            return new RentalPaymentDTO (paymentId,bookingId,startKm ,endKm,totalKm, totalAmount, dailyRent, amountChargedPerKm, paymentMethod, paidTime, paidDate, advanceAmount );
//        }
//
//        return null;
//    }
//
//    public RentalPaymentDTO searchPaymentByBookingID (int cusId) throws SQLException {
//
//        ResultSet rs =
//                CrudUtil.execute(
//                        "SELECT * FROM payments WHERE booking_id=?",
//                        cusId
//                );
//
//        if(rs.next()) {
//            int paymentId = rs.getInt("payment_id");
//            int bookingId = rs.getInt("booking_id");
//            String startKm = rs.getString("start_km");
//            String endKm = rs.getString("end_km");
//            String totalKm = rs.getString("total_km");
//            double totalAmount = rs.getDouble("total_amount");
//            double dailyRent = rs.getDouble("daily_rent");
//            double amountChargedPerKm = rs.getDouble("amount_charged_per_1km");
//            String paymentMethod = rs.getString("payment_method");
//            String paidTime = rs.getString("paid_time");
//            String paidDate = rs.getString("paid_date");
//            double advanceAmount = rs.getDouble("advance_amount");
//
//            return new RentalPaymentDTO(paymentId,bookingId,startKm ,endKm,totalKm, totalAmount, dailyRent, amountChargedPerKm, paymentMethod, paidTime, paidDate, advanceAmount);
//        }
//
//        return null;
//    }
//
//    public List<RentalPaymentDTO> getPayments() throws SQLException {
//        ResultSet rs =
//                CrudUtil.execute(
//                        "SELECT * FROM payments ORDER BY payment_id DESC"
//                );
//
//        List<RentalPaymentDTO> paymentList = new ArrayList<>();
//
//        while(rs.next()) {
//            RentalPaymentDTO rentalPaymentDTO = new RentalPaymentDTO(
//                    rs.getInt("payment_id"),
//                    rs.getInt("booking_id"),
//                    rs.getString("start_km"),
//                    rs.getString("end_km"),
//                    rs.getString("total_km"),
//                    rs.getDouble("total_amount"),
//                    rs.getDouble("daily_rent"),
//                    rs.getDouble("amount_charged_per_1km"),
//                    rs.getString("payment_method"),
//                    rs.getString("paid_time"),
//                    rs.getString("paid_date"),
//                    rs.getDouble("advance_amount")
//
//            );
//
//            paymentList.add(rentalPaymentDTO);
//        }
//
//        return paymentList;
//    }
//
//     public void printPaymentInvoice(int paymentId) throws JRException, SQLException {
//
//        Connection conn = DBConnection.getInstance().getConnection();
//
//        // Step 01
//        InputStream reportObject = getClass().getResourceAsStream("/lk/ijse/hcj_car_rentsystem/repots/invoice.jrxml");
//
//        // Step 02
//        JasperReport jr = JasperCompileManager.compileReport(reportObject); // this method thorws JRException
//
//        // Step 03
//        Map<String, Object> params = new HashMap<>();
//        params.put("PAYMENT_ID", paymentId);
//
//        JasperPrint jp = JasperFillManager.fillReport(jr, params, conn); // fillReport(japerreport, params, connection_obj)
//
//        // Step 04
//        JasperViewer.viewReport(jp, false);
//
//    }
//
//}
//
