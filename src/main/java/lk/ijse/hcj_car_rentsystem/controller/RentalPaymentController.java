package lk.ijse.hcj_car_rentsystem.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.hcj_car_rentsystem.dto.RentalPaymentDTO;
import lk.ijse.hcj_car_rentsystem.model.RentalPaymentModel;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class RentalPaymentController implements Initializable {

    @FXML
    private TextField paymentIdField;
    @FXML
    private TextField bookingIdField;
    @FXML
    private TextField startKmField;
    @FXML
    private TextField endKmField;
    @FXML
    private TextField totalKmField;
    @FXML
    private Label totalAmountField;
    @FXML
    private TextField dailyRentField;
    @FXML
    private TextField amountChargedPerKmField;
    @FXML
    private ComboBox <String>paymentMethodCombo;
    @FXML
    private TextField paidTimeField;
    @FXML
    private DatePicker paidDatePicker;
    @FXML
    private TextField advanceAmountField;
    @FXML
    private TextField searchField;

    
    @FXML
    private TableView tblPaymentDetails;
    
    @FXML
    private TableColumn colBookingId;
    @FXML
    private TableColumn colPaymentId;
    @FXML
    private TableColumn colTotalKm;
    @FXML
    private TableColumn colTotalAmount;
    @FXML
    private TableColumn colDailyRent;
    @FXML
    private TableColumn colPaymentMethod;
    @FXML
    private TableColumn colPaidTime;
    @FXML
    private TableColumn colPaidDate;
    
    private final String BOOKING_ID_REGEX = "^[0-9]+$";
    private final String PAYMENT_ID_REGEX = "^[0-9]+$";
    private final String START_KM_REGEX = "^[0-9]{1,7}$";
    private final String END_KM_REGEX = "^[0-9]{1,7}$";
    private final String TOTAL_KM_REGEX = "^[0-9]{1,7}$";
    private final String TOTAL_AMOUNT_REGEX = "^[0-9]{1,8}(\\.[0-9]{1,2})?$";
    private final String DAILY_RENT_REGEX = "^[0-9]{1,8}(\\.[0-9]{1,2})?$";
    private final String ADVANCE_AMOUNT_REGEX = "^[0-9]{1,8}(\\.[0-9]{1,2})?$";
    private final String CHARGED_PERKM_REGEX = "^[0-9]{1,8}(\\.[0-9]{1,2})?$";
    
    private final RentalPaymentModel rentalPaymentModel = new RentalPaymentModel();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Payment FXML is loaded!");
        
        // Set ComboBox Status Options
        paymentMethodCombo.setItems(FXCollections.observableArrayList(
            "cash",
            "online_transfer"
        ));
        
        // Mapping Table columns 
        
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colTotalKm.setCellValueFactory(new PropertyValueFactory<>("totalKm"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colDailyRent.setCellValueFactory(new PropertyValueFactory<>("dailyRent"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colPaidTime.setCellValueFactory(new PropertyValueFactory<>("paidTime"));
        colPaidDate.setCellValueFactory(new PropertyValueFactory<>("paidDate"));
        
        startKmField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotals());
        endKmField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotals());
        dailyRentField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotals());
        amountChargedPerKmField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotals());
        advanceAmountField.textProperty().addListener((observable, oldValue, newValue) -> calculateTotals());

        loadPaymentDetailsTable();
        
        tblPaymentDetails.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                RentalPaymentDTO selectedPayment = (RentalPaymentDTO)tblPaymentDetails.getSelectionModel().getSelectedItem();
                if (selectedPayment != null) {
                    populateFields(selectedPayment);
                }
            }
        });
    } 
    
    private void populateFields(RentalPaymentDTO rentalPaymentDTO) {
        try {
            paymentIdField.setText(String.valueOf(rentalPaymentDTO.getPaymentId()));
            bookingIdField.setText(String.valueOf(rentalPaymentDTO.getBookingId()));
            startKmField.setText(rentalPaymentDTO.getStartKm());
            endKmField.setText(rentalPaymentDTO.getEndKm());
            totalKmField.setText(rentalPaymentDTO.getTotalKm());
            totalAmountField.setText(String.valueOf(rentalPaymentDTO.getTotalAmount()));

            // Null check for advanceAmountField
            if (advanceAmountField != null) {
                advanceAmountField.setText(String.valueOf(rentalPaymentDTO.getAdvanceAmount()));
            }

            dailyRentField.setText(String.valueOf(rentalPaymentDTO.getDailyRent()));
            amountChargedPerKmField.setText(String.valueOf(rentalPaymentDTO.getAmountChargedPerKm()));
            paymentMethodCombo.setValue(rentalPaymentDTO.getPaymentMethod());
            paidTimeField.setText(rentalPaymentDTO.getPaidTime());

            // Safe Date Parsing
            if (rentalPaymentDTO.getPaidDate() != null && rentalPaymentDTO.getPaidDate().length() >= 10) {
                String dateOnly = rentalPaymentDTO.getPaidDate().substring(0, 10); 
                paidDatePicker.setValue(LocalDate.parse(dateOnly));
            }
        } catch (Exception e) {
            System.out.println("Error in populateFields: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleSavePayment() {
        
        paidTimeField.setText(java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a")));
        
        String bookingId = bookingIdField.getText().trim();
        String paymentId = paymentIdField.getText().trim();
        String startKm = startKmField.getText().trim();
        String endKm = endKmField.getText().trim();
        String totalKm = totalKmField.getText().trim();
        String totalAmount = totalAmountField.getText().trim();
        String advanceAmount = advanceAmountField.getText().trim();
        String dailyRent = dailyRentField.getText().trim();
        String amountChargedPerKm = amountChargedPerKmField.getText().trim();
        String paymentMethod = paymentMethodCombo.getValue();
        String paidTime = paidTimeField.getText().trim();
        LocalDate paidDate = paidDatePicker.getValue();
        
        // Apply Validation  
        
        if(!startKm.matches(START_KM_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Start Km!").show();
        } else if(!endKm.matches(END_KM_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid end Km!").show();
        } else if(!totalKm.matches(TOTAL_KM_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Total Km!").show();
        } else if(!totalAmount.matches(TOTAL_AMOUNT_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Total Amount!").show();
        } else if(!dailyRent.matches(DAILY_RENT_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Daily Rent!").show();
        } else if(!advanceAmount.matches(ADVANCE_AMOUNT_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid advance amount!").show();
        } else if(!amountChargedPerKm.matches(CHARGED_PERKM_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Amount Charged per Km!").show();
        } else {
            try {

                RentalPaymentDTO rentalPaymentDTO = new RentalPaymentDTO(0 ,Integer.parseInt(bookingId),startKm,endKm, totalKm, Double.parseDouble(totalAmount), Double.parseDouble(dailyRent), Double.parseDouble(amountChargedPerKm), paymentMethod, paidTime,paidDate.toString(), Double.parseDouble(advanceAmount));
                boolean result = rentalPaymentModel.savePayment(rentalPaymentDTO);

                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully!").show();
                    cleanFields();
                    loadPaymentDetailsTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
            } catch(Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
            
        }
    }
    
    
    @FXML
    private void handleSearchFromSearchBar(KeyEvent event) {

        try {
            if (event.getCode() == KeyCode.ENTER) {

                String keyword = searchField.getText().trim();

                if (keyword.isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Enter Payment ID or Customer ID").show();
                    return;
                }

                RentalPaymentDTO rentalPaymentDTO = null;

                // Search by Payment ID
                if (keyword.matches(PAYMENT_ID_REGEX)) {
                    rentalPaymentDTO = rentalPaymentModel.searchPayment(Integer.parseInt(keyword));
                }
                //Search by Booking ID
                else if (keyword.matches(BOOKING_ID_REGEX)) {
                    rentalPaymentDTO = rentalPaymentModel.searchPaymentByBookingID(Integer.parseInt(keyword));
                }
                else {
                    new Alert(Alert.AlertType.ERROR, "Invalid search keyword").show();
                    return;
                }

                if (rentalPaymentDTO != null){
                    paymentIdField.setText(String.valueOf(rentalPaymentDTO.getBookingId()));
                    bookingIdField.setText(String.valueOf(rentalPaymentDTO.getBookingId()));
                    startKmField.setText(rentalPaymentDTO.getStartKm());
                    endKmField.setText(rentalPaymentDTO.getEndKm());
                    totalKmField.setText(rentalPaymentDTO.getTotalKm());
                    totalAmountField.setText(String.valueOf(rentalPaymentDTO.getTotalAmount()));
                    advanceAmountField.setText(String.valueOf(rentalPaymentDTO.getAdvanceAmount()));
                    dailyRentField.setText(String.valueOf(rentalPaymentDTO.getDailyRent()));
                    amountChargedPerKmField.setText(String.valueOf(rentalPaymentDTO.getAmountChargedPerKm()));
                    paymentMethodCombo.setValue(rentalPaymentDTO.getPaymentMethod());
                    paidTimeField.setText(rentalPaymentDTO.getPaidTime());
                    paidDatePicker.setValue(LocalDate.parse(rentalPaymentDTO.getPaidDate()));
                } else {
                    new Alert(Alert.AlertType.ERROR, "Payment not found").show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }
    
    
    
    @FXML
    private void handlePaymentUpdate() {
    
        try {
        
            String bookingId = bookingIdField.getText().trim();
            String paymentId = paymentIdField.getText().trim();
            String startKm = startKmField.getText().trim();
            String endKm = endKmField.getText().trim();
            String totalKm = totalKmField.getText().trim();
            String totalAmount = totalAmountField.getText().trim();
            String advanceAmount = advanceAmountField.getText().trim();
            String dailyRent = dailyRentField.getText().trim();
            String amountChargedPerKm = amountChargedPerKmField.getText().trim();
            String paymentMethod = paymentMethodCombo.getValue();
            String paidTime = paidTimeField.getText().trim();
            LocalDate paidDate = paidDatePicker.getValue();
            
            // Apply Validation

            if(!bookingId.matches(BOOKING_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Booking id!").show();
            } else if(!paymentId.matches(PAYMENT_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Payment id!").show();
            } else if(!startKm.matches(START_KM_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Start Km!").show();
            } else if(!endKm.matches(END_KM_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid end Km!").show();
            } else if(!totalKm.matches(TOTAL_KM_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Total Km!").show();
            } else if(!totalAmount.matches(TOTAL_AMOUNT_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Total Amount!").show();
            } else if(!dailyRent.matches(DAILY_RENT_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Daily Rent!").show();
            } else if(!amountChargedPerKm.matches(CHARGED_PERKM_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Amount Charged per Km!").show();
            } else if(!advanceAmount.matches(ADVANCE_AMOUNT_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid advance amount!").show();
            } else {
            
                RentalPaymentDTO rentalPaymentDTO = new RentalPaymentDTO(Integer.parseInt(paymentId) ,Integer.parseInt(bookingId), startKm,endKm, totalKm, Double.parseDouble(totalAmount), Double.parseDouble(dailyRent), Double.parseDouble(amountChargedPerKm), paymentMethod, paidTime,paidDate.toString(), Double.parseDouble(advanceAmount));
                boolean result = rentalPaymentModel.updatePayment(rentalPaymentDTO);
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment updated successfully!").show();
                    cleanFields();
                    loadPaymentDetailsTable();
                } else {
                    
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
                
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
        
    }
    
    
    @FXML
    private void handlePaymentDelete() {
    
        try {
        
            String id = paymentIdField.getText();

            if(!id.matches(BOOKING_ID_REGEX)) {
                 new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
            } else {
            
                boolean result = rentalPaymentModel.deletePayment(Integer.parseInt(id));
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully!").show();
                    cleanFields();
                    loadPaymentDetailsTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
                
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
        
    }
    
    
    @FXML
    private void handleReset() {
        cleanFields();
    }
    
    private void cleanFields() {
        bookingIdField.setText("");
        paymentIdField.setText("");
        startKmField.setText("");
        endKmField.setText("");
        totalKmField.setText("");
        totalAmountField.setText("");
        advanceAmountField.setText("");
        dailyRentField.setText("");
        amountChargedPerKmField.setText("");
        paymentMethodCombo.setValue(null);
        paidTimeField.setText("");
        paidDatePicker.setValue(null);
    }
    
    
    private void loadPaymentDetailsTable() {
    
        try {
        
            List<RentalPaymentDTO> rentalPaymentList = rentalPaymentModel.getPayments();
            
            ObservableList<RentalPaymentDTO> obList = FXCollections.observableArrayList();
            
            for (RentalPaymentDTO rentalPaymentDTO : rentalPaymentList) {
                obList.add(rentalPaymentDTO);
            }
            
            tblPaymentDetails.setItems(obList);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    // Calculate total km and total amount automatically
    private void calculateTotals() {
        try {
            
            // Checking if values ​​are entered
            if (startKmField.getText().trim().isEmpty() || 
                endKmField.getText().trim().isEmpty() || 
                dailyRentField.getText().trim().isEmpty() || 
                amountChargedPerKmField.getText().trim().isEmpty() || 
                advanceAmountField.getText().trim().isEmpty()) {

                totalKmField.setText("0");
                totalAmountField.setText("0.00");
                return; 
            }

            int start = Integer.parseInt(startKmField.getText().trim());
            int end = Integer.parseInt(endKmField.getText().trim());
            int totalKm = end - start;
            totalKmField.setText(String.valueOf(totalKm));

            double dailyRent = Double.parseDouble(dailyRentField.getText().trim());
            double perKm = Double.parseDouble(amountChargedPerKmField.getText().trim());
            double advance = Double.parseDouble(advanceAmountField.getText().trim());

            double extraKmCharge = Math.max(0, totalKm - 100) * perKm;
            double totalAmount = dailyRent + extraKmCharge - advance;

            totalAmountField.setText(String.format("%.2f", totalAmount));

        } catch (NumberFormatException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }
    
    @FXML
    void printInvoice(ActionEvent event) {
        
        // Getting the Payment ID from the field
        String paymentIdText = paymentIdField.getText().trim();

        // Checking if the ID is empty
        if (paymentIdText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select or search a payment first!").show();
            return;
        }

        try {
            int paymentId = Integer.parseInt(paymentIdText);

            // Calling to print the invoice through the model
            rentalPaymentModel.printPaymentInvoice(paymentId);

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Payment ID format!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while generating invoice: " + e.getMessage()).show();
        }

    }
}

