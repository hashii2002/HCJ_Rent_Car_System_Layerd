package lk.ijse.hcj_car_rentsystem.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.hcj_car_rentsystem.dao.custom.DriverDAO;
import lk.ijse.hcj_car_rentsystem.dao.custom.impl.DriverDAOImpl;
import lk.ijse.hcj_car_rentsystem.dto.DriverDTO;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DriverController implements Initializable {

    @FXML
    private TextField driverIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField nicField;
    @FXML
    private TextField licenseNoField;
    @FXML
    private TextArea addressField;
    @FXML
    private ComboBox<String> statusCombo;
    @FXML
    private TextArea notesField;
    @FXML
    private TextField searchField;
    @FXML
    private Label lblTotalDrivers;

    @FXML
    private Label lblAvailableDrivers;

    @FXML
    private Label lblAssignedDrivers;

    @FXML
    private Label lblOnLeaveDrivers;

    
    @FXML
    private TableView tblDriver;
    @FXML
    private TableColumn colDriverId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colNic;
    @FXML
    private TableColumn colLicenseNo;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colStatus;
    @FXML
    private TableColumn colNotes;
    
    private final String DRIVER_ID_REGEX = "^[0-9]+$";
    private final String DRIVER_NAME_REGEX = "^[A-Za-z ]{3,}$";
    private final String DRIVER_CONTACT_REGEX = "^[0-9]{10}$";
    private final String DRIVER_NIC_REGEX = "^(\\d{9}[VvXx]|\\d{12})$";
    private final String DRIVER_LICENSENO_REGEX = "^[A-Z][0-9]{7}$";
    private final String DRIVER_ADDRESS_REGEX = "^[A-Za-z0-9 ,./-]{5,}$";
    private final String DRIVER_STATUS_REGEX = "^[A-Za-z ]+$";
    private final String DRIVER_NOTES_REGEX = "^[A-Za-z0-9 .,()-]*$";
    
    private final DriverDAO driverDao = new DriverDAOImpl();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Driver FXML is loaded!");
        
        // Set ComboBox Status Options
        statusCombo.setItems(FXCollections.observableArrayList(
            "Available",
            "Assigned",
            "On Leave"
        ));
        
        // Map Columns
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colLicenseNo.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        loadDriverTable();
        loadDriverCounts();
        
        // Add double click event on table row
        tblDriver.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                DriverDTO selectedDriver = (DriverDTO)tblDriver.getSelectionModel().getSelectedItem();
                if (selectedDriver != null) {
                    populateFields(selectedDriver);
                }
            }
        });
    } 
    
    private void populateFields(DriverDTO driverDTO) {
        driverIdField.setText(String.valueOf(driverDTO.getDriverId()));
        nameField.setText(driverDTO.getName());
        contactField.setText(driverDTO.getContact());
        nicField.setText(driverDTO.getNic());
        licenseNoField.setText(driverDTO.getLicenseNo());
        addressField.setText(driverDTO.getAddress());
        notesField.setText(driverDTO.getNotes());
        statusCombo.setValue(driverDTO.getStatus());
    } 
    
    @FXML
    private void handleSaveDriver() {
       
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String nic = nicField.getText().trim();
        String licenseNo = licenseNoField.getText().trim();
        String address = addressField.getText().trim();
        String status = statusCombo.getValue();
        String notes = notesField.getText().trim();
        
        // Apply Validation  
        
        if(!name.matches(DRIVER_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Driver Name!").show();
        } else if(!contact.matches(DRIVER_CONTACT_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Driver Contact Number!").show();
        } else if(!nic.matches(DRIVER_NIC_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Driver NIC!").show();
        } else if(!licenseNo.matches(DRIVER_LICENSENO_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid License No!").show();
        } else if(!address.matches(DRIVER_ADDRESS_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Address!").show();
        } else if (status == null || !status.matches(DRIVER_STATUS_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Driver Status!").show();
        } else if(!notes.matches(DRIVER_NOTES_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Notes!").show();
        } else {
            try {
                
                DriverDTO driverDTO = new DriverDTO(name, contact, nic, licenseNo, address, status, notes);
                boolean result = driverDao.saveDriver(driverDTO);

                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Driver saved successfully!").show();
                    cleanFields();
                    loadDriverTable();
                    loadDriverCounts();

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
                    new Alert(Alert.AlertType.ERROR, "Enter Driver ID or Name").show();
                    return;
                }

                DriverDTO driverDTO = null;

                // Search by Customer ID
                if (keyword.matches(DRIVER_ID_REGEX)) {
                    int driverId = Integer.parseInt(keyword);
                    driverDTO = driverDao.searchDriver(driverId);
                }
                
                //Search by Customer Name
                else if (keyword.matches(DRIVER_NAME_REGEX)) {
                    driverDTO = driverDao.searchDriverByName(keyword);
                }
                else {
                    new Alert(Alert.AlertType.ERROR, "Invalid search keyword").show();
                    return;
                }

                if (driverDTO != null){
                    driverIdField.setText(String.valueOf(driverDTO.getDriverId()));
                    nameField.setText(driverDTO.getName());
                    contactField.setText(driverDTO.getContact());
                    nicField.setText(driverDTO.getNic());
                    licenseNoField.setText(driverDTO.getLicenseNo());
                    addressField.setText(driverDTO.getAddress());
                    notesField.setText(driverDTO.getNotes());
                    statusCombo.setValue(driverDTO.getStatus());
                } else {
                    new Alert(Alert.AlertType.ERROR, "Driver not found").show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    
    
    @FXML
    private void handleDriverUpdate() {
    
        try {
        
            String driverId = driverIdField.getText().trim();
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();
            String nic = nicField.getText().trim();
            String licenseNo = licenseNoField.getText().trim();
            String address = addressField.getText().trim();
            String status = statusCombo.getValue();
            String notes = notesField.getText().trim();
            
            // Apply Validation

            if(!driverId.matches(DRIVER_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Driver id!").show();
            } else if(!name.matches(DRIVER_NAME_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Driver Name!").show();
            } else if(!contact.matches(DRIVER_CONTACT_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Driver Contact Number!").show();
            } else if(!nic.matches(DRIVER_NIC_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Driver NIC!").show();
            } else if(!licenseNo.matches(DRIVER_LICENSENO_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid License No!").show();
            } else if(!address.matches(DRIVER_ADDRESS_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Address!").show();
            } else if (status == null || !status.matches(DRIVER_STATUS_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Driver Status!").show();
            } else if(!notes.matches(DRIVER_NOTES_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Notes!").show();
            } else {
            
                DriverDTO driverDTO = new DriverDTO(Integer.parseInt(driverId) , name, contact, nic, licenseNo, address, status, notes);
                boolean result = driverDao.updateDriver(driverDTO);
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Driver updated successfully!").show();
                    cleanFields();
                    loadDriverTable();
                    loadDriverCounts();

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
    private void handleDriverDelete() {
    
        try {
        
            String id = driverIdField.getText();

            if(!id.matches(DRIVER_ID_REGEX)) {
                 new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
            } else {
            
                boolean result = driverDao.deleteDriver(Integer.parseInt(id));
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Driver deleted successfully!").show();
                    cleanFields();
                    loadDriverTable();
                    loadDriverCounts();

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
        driverIdField.setText("");
        nameField.setText("");
        contactField.setText("");
        nicField.setText("");
        licenseNoField.setText("");
        addressField.setText("");
        statusCombo.setValue(null);
        notesField.setText("");
    }
    
    private void loadDriverTable() {
    
        try {
        
            List<DriverDTO> driverList = driverDao.getDrivers();
            
            ObservableList<DriverDTO> obList = FXCollections.observableArrayList();
            
            for (DriverDTO driverDTO : driverList) {
                obList.add(driverDTO);
            }
            
            tblDriver.setItems(obList);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void loadDriverCounts() {

        try {
            List<DriverDTO> driverList = driverDao.getDrivers();

            int total = driverList.size();
            int available = 0;
            int assigned = 0;
            int onLeave = 0;

            for (DriverDTO d : driverList) {
                if (d.getStatus().equalsIgnoreCase("Available")) {
                    available++;
                } else if (d.getStatus().equalsIgnoreCase("Assigned")) {
                    assigned++;
                } else if (d.getStatus().equalsIgnoreCase("On Leave")) {
                    onLeave++;
                }
            }

            lblTotalDrivers.setText(String.valueOf(total));
            lblAvailableDrivers.setText(String.valueOf(available));
            lblAssignedDrivers.setText(String.valueOf(assigned));
            lblOnLeaveDrivers.setText(String.valueOf(onLeave));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
}

