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
import lk.ijse.hcj_car_rentsystem.bo.BOFactory;
import lk.ijse.hcj_car_rentsystem.bo.custom.VehicleBO;
import lk.ijse.hcj_car_rentsystem.dao.custom.VehicleDAO;
import lk.ijse.hcj_car_rentsystem.dao.custom.impl.VehicleDAOImpl;
import lk.ijse.hcj_car_rentsystem.dto.VehicleDTO;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class VehicleController implements Initializable {

    @FXML
    private TextField vehicleIdField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField brandField;
    @FXML
    private TextField priceField;
    @FXML
    private TextArea notesField;
    @FXML
    private ComboBox<String> statusCombo;
    @FXML
    private TextField searchField;
    @FXML
    private Label lblTotalVehicles;
    @FXML
    private Label lblAvailableVehicles;
    @FXML
    private Label lblRentedVehicles;
    @FXML
    private Label lblMaintenanceVehicles;

    
    @FXML
    private TableView tblVehicle;
    @FXML
    private TableColumn colVehicleId;
    @FXML
    private TableColumn colModel;
    @FXML
    private TableColumn colBrand;
    @FXML
    private TableColumn colPrice;
    @FXML
    private TableColumn colNotes;
    @FXML
    private TableColumn colStatus;
    
    private final String VEHICLE_ID_REGEX = "^[0-9]+$";
    private final String VEHICLE_MODEL_REGEX = "^[A-Za-z0-9 ]+$";
    private final String VEHICLE_BRAND_REGEX = "^[A-Za-z][A-Za-z ]{1,49}$";
    private final String VEHICLE_PRICE_REGEX = "^[0-9]{1,8}(\\.[0-9]{1,2})?$";
    private final String VEHICLE_NOTES_REGEX = "^[A-Za-z0-9 .,()-]*$";
    private final String VEHICLE_STATUS_REGEX = "^[A-Za-z ]+$";
    
    private final VehicleBO vehicleBO = (VehicleBO) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Vehicle FXML is loaded!");
        
        // Set ComboBox Status Options
        statusCombo.setItems(FXCollections.observableArrayList(
            "Available",
            "Rented",
            "Maintenance"
        ));
        
        // Map table columns 
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("pricePerDay"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadVehicleTable();
        loadVehicleCounts();
        
        // Add double click event on table row
        tblVehicle.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                VehicleDTO selectedVehicle = (VehicleDTO)tblVehicle.getSelectionModel().getSelectedItem();
                if (selectedVehicle != null) {
                    populateFields(selectedVehicle);
                }
            }
        });
    } 
    
    private void populateFields(VehicleDTO vehicleDTO) {
        
        vehicleIdField.setText(String.valueOf(vehicleDTO.getVehicleId()));
        modelField.setText(vehicleDTO.getModel());
        brandField.setText(vehicleDTO.getBrand());
        priceField.setText(String.valueOf(vehicleDTO.getPricePerDay()));
        notesField.setText(vehicleDTO.getNotes());
        statusCombo.setValue(vehicleDTO.getStatus());
    } 
    
    @FXML
    private void handleSaveVehicle() {
       
        String model = modelField.getText().trim();
        String brand = brandField.getText().trim();
        String price_per_day = priceField.getText().trim();
        String notes = notesField.getText().trim();
        String status = statusCombo.getValue();
        
        // Apply Validation  
        
        if(!model.matches(VEHICLE_MODEL_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Model!").show();
        } else if(!brand.matches(VEHICLE_BRAND_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Brand!").show();
        } else if(!price_per_day.matches(VEHICLE_PRICE_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Price!").show();
        } else if(!notes.matches(VEHICLE_NOTES_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Notes!").show();
        } else if (status == null || !status.matches(VEHICLE_STATUS_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Status!").show();
        } else {
            try {
                
                VehicleDTO vehicleDTO = new VehicleDTO(model , brand, Double.parseDouble(price_per_day), status, notes);
                boolean result = vehicleBO.saveVehicle(vehicleDTO);

                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle saved successfully!").show();
                    cleanFields();
                    loadVehicleTable();
                    loadVehicleCounts();

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
                    new Alert(Alert.AlertType.ERROR, "Enter Vehicle ID").show();
                    return;
                }

                VehicleDTO vehicleDTO = null;

                // Search by Vehicle ID
                if (keyword.matches(VEHICLE_ID_REGEX)) {
                    int vehicleId = Integer.parseInt(keyword);
                    vehicleDTO = vehicleBO.searchVehicle(vehicleId);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid search keyword").show();
                    return;
                }

                if (vehicleDTO != null){
        
                    vehicleIdField.setText(String.valueOf(vehicleDTO.getVehicleId()));
                    modelField.setText(vehicleDTO.getModel());
                    brandField.setText(vehicleDTO.getBrand());
                    priceField.setText(String.valueOf(vehicleDTO.getPricePerDay()));
                    notesField.setText(vehicleDTO.getNotes());
                    statusCombo.setValue(vehicleDTO.getStatus());
                } else {
                    new Alert(Alert.AlertType.ERROR, "User not found").show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }
    
    @FXML
    private void handleVehicleUpdate() {
    
        try {
        
            String vehicleId = vehicleIdField.getText().trim();
            String model = modelField.getText().trim();
            String brand = brandField.getText().trim();
            String price_per_day = priceField.getText().trim();
            String notes = notesField.getText().trim();
            String status = statusCombo.getValue();
            
            // Apply Validation

            if(!vehicleId.matches(VEHICLE_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Vehicle id!").show();
            } else if(!model.matches(VEHICLE_MODEL_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Model").show();
            } else if(!brand.matches(VEHICLE_BRAND_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Brand!").show();
            } else if(!price_per_day.matches(VEHICLE_PRICE_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Price!").show();
            } else if(!notes.matches(VEHICLE_NOTES_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Notes!").show();
            } else if (status == null || !status.matches(VEHICLE_STATUS_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid Vehicle Status!").show();
            } else {
            
                VehicleDTO vehicleDTO = new VehicleDTO(Integer.parseInt(vehicleId) , model , brand, Double.parseDouble(price_per_day), status, notes);
                boolean result = vehicleBO.updateVehicle(vehicleDTO);
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle updated successfully!").show();
                    cleanFields();
                    loadVehicleTable();
                    loadVehicleCounts();

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
    private void handleVehicleDelete() {
    
        try {
        
            String id = vehicleIdField.getText();

            if(!id.matches(VEHICLE_ID_REGEX)) {
                 new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
            } else {
            
                boolean result = vehicleBO.deleteVehicle(Integer.parseInt(id));
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle deleted successfully!").show();
                    cleanFields();
                    loadVehicleTable();
                    loadVehicleCounts();

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
        vehicleIdField.setText("");
        modelField.setText("");
        brandField.setText("");
        priceField.setText("");
        notesField.setText("");
        statusCombo.setValue(null);
    }
    
    private void loadVehicleTable() {
    
        try {
        
            List<VehicleDTO> vehicleList = vehicleBO.getVehicles();
            
            ObservableList<VehicleDTO> obList = FXCollections.observableArrayList();
            
            for (VehicleDTO vehicleDTO : vehicleList) {
                obList.add(vehicleDTO);
            }
            
            tblVehicle.setItems(obList);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void loadVehicleCounts() {

        try {
            List<VehicleDTO> vehicleList = vehicleBO.getVehicles();

            int total = vehicleList.size();
            int available = 0;
            int rented = 0;
            int maintenance = 0;

            for (VehicleDTO v : vehicleList) {
                if (v.getStatus().equalsIgnoreCase("Available")) {
                    available++;
                } else if (v.getStatus().equalsIgnoreCase("Rented")) {
                    rented++;
                } else if (v.getStatus().equalsIgnoreCase("Maintenance")) {
                    maintenance++;
                }
            }

            lblTotalVehicles.setText(String.valueOf(total));
            lblAvailableVehicles.setText(String.valueOf(available));
            lblRentedVehicles.setText(String.valueOf(rented));
            lblMaintenanceVehicles.setText(String.valueOf(maintenance));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

