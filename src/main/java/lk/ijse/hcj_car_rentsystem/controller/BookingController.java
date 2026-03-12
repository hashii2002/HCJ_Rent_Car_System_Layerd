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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.hcj_car_rentsystem.bo.BOFactory;
import lk.ijse.hcj_car_rentsystem.bo.custom.BookingBO;
import lk.ijse.hcj_car_rentsystem.bo.custom.DriverBO;
import lk.ijse.hcj_car_rentsystem.bo.custom.VehicleBO;
import lk.ijse.hcj_car_rentsystem.dao.custom.BookingDAO;
import lk.ijse.hcj_car_rentsystem.dao.custom.DriverDAO;
import lk.ijse.hcj_car_rentsystem.dao.custom.VehicleDAO;
import lk.ijse.hcj_car_rentsystem.dao.custom.impl.BookingDAOImpl;
import lk.ijse.hcj_car_rentsystem.dao.custom.impl.DriverDAOImpl;
import lk.ijse.hcj_car_rentsystem.dao.custom.impl.VehicleDAOImpl;
import lk.ijse.hcj_car_rentsystem.dto.BookingDTO;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class BookingController implements Initializable {

    @FXML
    private TextField bookingIdField;
    @FXML
    private TextField customerIdField;
    @FXML
    private DatePicker bookingDatePicker;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> statusCombo;
    @FXML
    private TextField advanceField;
    @FXML
    private TextArea notesField;
    @FXML
    private ComboBox<String> vehicleIDCombo;
    @FXML
    private ComboBox<String> driverIDCombo;
    @FXML
    private ComboBox<String> driverAssignCombo;
    @FXML
    private TextField searchField;
    @FXML
    private Label lblConfirmedCount;
    @FXML
    private Label lblCompletedCount;
    @FXML
    private Label lblCancelledCount;

 
    @FXML
    private TableView<BookingDTO> tblBooking;
    @FXML
    private TableColumn<BookingDTO, Integer> colBookingId;
    @FXML
    private TableColumn<BookingDTO, Integer> colCustomerId;
    @FXML
    private TableColumn <BookingDTO, String>colBookingDate;
    @FXML
    private TableColumn<BookingDTO, String> colStartDate;
    @FXML
    private TableColumn<BookingDTO, String> colEndDate;
    @FXML
    private TableColumn<BookingDTO, String> colStatus;
    @FXML
    private TableColumn<BookingDTO, Double> colAdvance;
    @FXML
    private TableColumn<BookingDTO, String> colNotes;
    @FXML
    private TableColumn<BookingDTO, Integer> colVehicleID;
    @FXML
    private TableColumn<BookingDTO, Integer> colDriverID;
    @FXML
    private TableColumn<BookingDTO, String> colDriverAssign;
    
    private final String BOOKING_ID_REGEX = "^[0-9]+$";
    private final String CUSTOMER_ID_REGEX = "^[0-9]+$";
    private final String BOOKING_STATUS_REGEX = "^[A-Za-z ]+$";
    private final String DRIVER_ASSIGN_REGEX = "^[A-Za-z ]+$";
    private final String BOOKING_ADVANCE_REGEX = "^[0-9]{1,8}(\\.[0-9]{1,2})?$";
    private final String BOOKING_NOTES_REGEX = "^[A-Za-z0-9 .,()-]*$";
    
    private final BookingBO bookingBO = (BookingBO) BOFactory.getInstance().getBO(BOFactory.BOType.BOOKING);
    private final VehicleBO vehicleBO = (VehicleBO) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);
    private final DriverBO driverBO = (DriverBO) BOFactory.getInstance().getBO(BOFactory.BOType.DRIVER);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Booking FXML is loaded!");
        
        // Set ComboBox Status Options
        statusCombo.setItems(FXCollections.observableArrayList(
            "Confirmed",
            "Cancelled",
            "Completed"
        ));
        
        // Set ComboBox Driver Assign Options
        driverAssignCombo.setItems(FXCollections.observableArrayList(
            "Yes",
            "No"
        ));
        
        // Map table columns 
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        colAdvance.setCellValueFactory(new PropertyValueFactory<>("advanceAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("bookingStatus"));
        colVehicleID.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colDriverID.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colDriverAssign.setCellValueFactory(new PropertyValueFactory<>("driverAssign"));
        
        loadBookingTable();
        loadBookingCounts(); 
        loadAvailableVehicles();
        loadAvailableDrivers();
            
        // Add double click event on table row
        tblBooking.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                BookingDTO selectedCustomer = (BookingDTO)tblBooking.getSelectionModel().getSelectedItem();
                if (selectedCustomer != null) {
                    populateFields(selectedCustomer);
                }
            }
        });
    }
    
    // Getting the available vehicles list using a combo box
    private void loadAvailableVehicles() {
        try {
            List<String> vehicleIdList = vehicleBO.getAvailableVehicleIds();
            ObservableList<String> obList = FXCollections.observableArrayList(vehicleIdList);
            vehicleIDCombo.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load available vehicles").show();
        }
    }
    
    // Getting the available drivers list using a combo box
    private void loadAvailableDrivers() {
        try {
            List<String> driverIdList = driverBO.getAvailableDriverIds();
            ObservableList<String> obList = FXCollections.observableArrayList(driverIdList);
            driverIDCombo.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load available drivers").show();
        }
    }
    
    private void populateFields(BookingDTO bookingDTO) {
        customerIdField.setText(String.valueOf(bookingDTO.getCustomerId()));
        bookingIdField.setText(String.valueOf(bookingDTO.getBookingId()));
        bookingDatePicker.setValue(LocalDate.parse(bookingDTO.getBookingDate()));
        startDatePicker.setValue(LocalDate.parse(bookingDTO.getStartDate()));
        endDatePicker.setValue(LocalDate.parse(bookingDTO.getEndDate()));
        notesField.setText(bookingDTO.getNotes());
        advanceField.setText(String.valueOf(bookingDTO.getAdvanceAmount()));
        statusCombo.setValue(bookingDTO.getBookingStatus());
        driverAssignCombo.setValue(bookingDTO.getDriverAssign());
    } 
    
    @FXML
    private void handleSaveBooking() {
        
        String customerId = customerIdField.getText().trim();
        String vehicleId = vehicleIDCombo.getValue();
        String driverId = driverIDCombo.getValue();    
        LocalDate bookingDate = bookingDatePicker.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String status = statusCombo.getValue();
        String advance= advanceField.getText().trim();
        String notes = notesField.getText().trim();
        String driverAssign = driverAssignCombo.getValue();
        
        
        // Apply Validation  
        if(!notes.matches(BOOKING_NOTES_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Notes!").show();
        } else if (status == null || !status.matches(BOOKING_STATUS_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Booking Status!").show();
        } else if(!advance.matches(BOOKING_ADVANCE_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Advance!").show();
        } else if (vehicleId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select an available Vehicle!").show();
        } else if (driverId == null) {
             new Alert(Alert.AlertType.ERROR, "Please select an available Driver!").show();
        } else if (driverAssign == null || !driverAssign.matches(DRIVER_ASSIGN_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Driver Assign!").show();
        } else if (driverAssign.equalsIgnoreCase("Yes") && driverId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select an available driver!").show();
        } else if (driverAssign.equalsIgnoreCase("No") && driverId != null) {
            new Alert(Alert.AlertType.ERROR, "Driver selection not allowed when Driver Assign is No!").show();
        } else {
            try {
                
                BookingDTO bookingDTO = new BookingDTO(Integer.parseInt(customerId),
                        Integer.parseInt(vehicleId),
                        Integer.parseInt(driverId),
                        bookingDate.toString(),
                        startDate.toString(),
                        endDate.toString(),
                        status, 
                        Double.parseDouble(advance),
                        notes, 
                        driverAssign);
                boolean result = bookingBO.saveBooking(bookingDTO);

                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Booking Completed successfully!").show();
                    cleanFields();
                    loadBookingTable();
                    loadBookingCounts(); 
                
                } else {
                    new Alert(Alert.AlertType.ERROR, "Booking failed! Database error!").show();
                }
            } catch(Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Booking faild!").show();
            }
            
        }
    }
    
    @FXML
    private void handleSearchFromSearchBar(KeyEvent event) {

        try {
            if (event.getCode() == KeyCode.ENTER) {

                String keyword = searchField.getText().trim();

                if (keyword.isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Enter Booking ID or Customer ID").show();
                    return;
                }

                BookingDTO bookingDTO = null;

                // Search by Customer ID
                if (keyword.matches(BOOKING_ID_REGEX)) {
                    bookingDTO = bookingBO.searchBooking(String.valueOf(Integer.parseInt(keyword)));
                }
                //Search by Booking ID
                else if (keyword.matches(CUSTOMER_ID_REGEX)) {
                    bookingDTO = bookingBO.searchBookingByCustomerID(Integer.parseInt(keyword));
                }
                else {
                    new Alert(Alert.AlertType.ERROR, "Invalid search keyword").show();
                    return;
                }

                if (bookingDTO != null){
                    customerIdField.setText(String.valueOf(bookingDTO.getCustomerId()));
                    bookingIdField.setText(String.valueOf(bookingDTO.getBookingId()));
                    bookingDatePicker.setValue(LocalDate.parse(bookingDTO.getBookingDate()));
                    startDatePicker.setValue(LocalDate.parse(bookingDTO.getStartDate()));
                    endDatePicker.setValue(LocalDate.parse(bookingDTO.getEndDate()));
                    notesField.setText(bookingDTO.getNotes());
                    advanceField.setText(String.valueOf(bookingDTO.getAdvanceAmount()));
                    statusCombo.setValue(bookingDTO.getBookingStatus());
                    driverAssignCombo.setValue(bookingDTO.getDriverAssign());

                } else {
                    new Alert(Alert.AlertType.ERROR, "Booking not found").show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }
    
    
    @FXML
    private void handleBookingUpdate() {
    
        try {
        
            String bookingId = bookingIdField.getText().trim();
            String customerId = customerIdField.getText().trim();
            String vehicleId = vehicleIDCombo.getValue();
            String driverId = driverIDCombo.getValue();
            LocalDate bookingDate = bookingDatePicker.getValue();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            String advance = advanceField.getText().trim();
            String notes = notesField.getText().trim();
            String status = statusCombo.getValue();
            String driverAssign = driverAssignCombo.getValue();
            
            // Apply Validation

            if(!bookingId.matches(BOOKING_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Booking id!").show();
            } else if(!customerId.matches(CUSTOMER_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Customer id!").show();
            } else if(!notes.matches(BOOKING_NOTES_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Notes!").show();
            } else if (status == null || !status.matches(BOOKING_STATUS_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Booking Status!").show();
            } else if(!advance.matches(BOOKING_ADVANCE_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Advance!").show();
            } else if (driverAssign == null || !driverAssign.matches(DRIVER_ASSIGN_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Driver Assign!").show();
            } else if (driverAssign.equalsIgnoreCase("Yes") && driverId == null) {
                new Alert(Alert.AlertType.ERROR, "Please select an available driver!").show();
            } else if (driverAssign.equalsIgnoreCase("No") && driverId != null) {
                new Alert(Alert.AlertType.ERROR, "Driver selection not allowed when Driver Assign is No!").show();
            } else if (bookingDate == null || startDate == null || endDate == null){
                new Alert(Alert.AlertType.ERROR, "Please select all dates!").show();
            } else {
                
                Integer driverIdInt = null;
                if(driverId != null && !driverId.isEmpty()) {
                    driverIdInt = Integer.parseInt(driverId);
                }

                BookingDTO bookingDTO = new BookingDTO(Integer.parseInt(bookingId) ,
                        Integer.parseInt(customerId),
                        Integer.parseInt(vehicleId), 
                        //Integer.parseInt(driverId),
                        driverIdInt != null ? driverIdInt : 0,
                        bookingDate.toString(),
                        startDate.toString(),
                        endDate.toString(),
                        status,
                        Double.parseDouble(advance),
                        notes, 
                        driverAssign);
                boolean result = bookingBO.updateBooking(bookingDTO);
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Booking updated successfully!").show();
                    cleanFields();
                    loadBookingTable();
                    loadBookingCounts(); 
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
    private void handleBookingDelete() {
        BookingDTO selectedBooking = tblBooking.getSelectionModel().getSelectedItem();

        if (selectedBooking == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a booking from the table first!").show();
            return;
        }

        try {
            boolean result = bookingBO.deleteBooking(
                selectedBooking.getBookingId(),
                selectedBooking.getVehicleId(),
                selectedBooking.getDriverId()
            );

            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Booking deleted and resources released!").show();
                cleanFields();
                loadBookingTable();
                loadBookingCounts();
                loadAvailableVehicles(); 
                loadAvailableDrivers();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private void handleReset() {
        cleanFields();
    }
    
    private void cleanFields() {
        bookingIdField.setText("");
        customerIdField.setText("");
        bookingDatePicker.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        advanceField.setText("");
        notesField.setText("");
        statusCombo.setValue(null);
        driverAssignCombo.setValue(null);
        vehicleIDCombo.setValue(null);
        driverIDCombo.setValue(null);
    }
     
    private void loadBookingTable() {
    
        try {
        
            List<BookingDTO> bookingList = bookingBO.getBookings();
            
            ObservableList<BookingDTO> obList = FXCollections.observableArrayList();
            
            for (BookingDTO bookingDTO : bookingList) {
                obList.add(bookingDTO);
            }
            
            tblBooking.setItems(obList);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void loadBookingCounts() {

        int confirmedCount = 0;
        int completedCount = 0;
        int cancelledCount = 0;

        try {
            List<BookingDTO> bookingList = bookingBO.getBookings();

            for (BookingDTO booking : bookingList) {

                if (booking.getBookingStatus().equalsIgnoreCase("Confirmed")) {
                    confirmedCount++;
                } 
                else if (booking.getBookingStatus().equalsIgnoreCase("Completed")) {
                    completedCount++;
                } 
                else if (booking.getBookingStatus().equalsIgnoreCase("Cancelled")) {
                    cancelledCount++;
                }
            }

            lblConfirmedCount.setText(String.valueOf(confirmedCount));
            lblCompletedCount.setText(String.valueOf(completedCount));
            lblCancelledCount.setText(String.valueOf(cancelledCount));

        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    private void handleDriverAssignSelection() {
        String assignValue = driverAssignCombo.getValue();

        if (assignValue != null && assignValue.equalsIgnoreCase("Yes")) {
            driverIDCombo.setDisable(false); // enable driver selection
        } else {
            driverIDCombo.setDisable(true); // disable driver selection
            driverIDCombo.setValue(null); // clear selection
        }
    }
    
     @FXML
    void handlePrint(ActionEvent event) {
        
        try {
            bookingBO.printBookingReports();
        } catch(Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }

    }

}
