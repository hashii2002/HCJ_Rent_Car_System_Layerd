package lk.ijse.hcj_car_rentsystem.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import lk.ijse.hcj_car_rentsystem.dto.CustomerDTO;
import lk.ijse.hcj_car_rentsystem.model.CustomerModel;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CustomerController implements Initializable {

    @FXML
    private TextField customerIdField;
    @FXML
    private TextField userIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField contactField;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField nicField;
    @FXML
    private TextField licenseNoField;
    @FXML
    private TextField searchField;
    
    @FXML
    private TableView tblCustomer;
    @FXML
    private TableColumn colCustomerId;
    @FXML
    private TableColumn colUserId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colNIC;
    @FXML
    private TableColumn colLicenseNo;
    @FXML
    private Label lblTotalCustomers;

    
    private final String CUSTOMER_ID_REGEX = "^[0-9]+$";
    private final String USER_ID_REGEX = "^[0-9]+$";
    private final String CUSTOMER_NAME_REGEX = "^[A-Za-z]+( [A-Za-z]+)*$";
    private final String CUSTOMER_ADDRESS_REGEX = "^[A-Za-z0-9 ,./-]{5,}$";
    private final String CUSTOMER_CONTACT_REGEX = "^[0-9]{10}$";
    private final String CUSTOMER_NIC_REGEX = "^(\\d{9}[VvXx]|\\d{12})$";
    private final String CUSTOMER_LICENSENO_REGEX = "^[A-Z][0-9]{7}$";
    
    private final CustomerModel customerModel = new CustomerModel();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Customer FXML is loaded!");
        
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nicPassport"));
        colLicenseNo.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));

        loadCustomerTable();
        
        // Add double click event on table row
        tblCustomer.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                CustomerDTO selectedCustomer = (CustomerDTO)tblCustomer.getSelectionModel().getSelectedItem();
                if (selectedCustomer != null) {
                    populateFields(selectedCustomer);
                }
            }
        });
    } 
    
    private void populateFields(CustomerDTO customerDTO) {
        customerIdField.setText(String.valueOf(customerDTO.getCustomerId()));
        userIdField.setText(String.valueOf(customerDTO.getUserId()));
        nameField.setText(customerDTO.getName());
        addressField.setText(customerDTO.getAddress());
        contactField.setText(customerDTO.getContact());
        nicField.setText(customerDTO.getNicPassport());
        licenseNoField.setText(customerDTO.getLicenseNo());
    }
    
    @FXML
    private void handleSaveCustomer() {
        
        String userId = userIdField.getText().trim();
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String contact = contactField.getText().trim();
        String nic = nicField.getText().trim();
        String licenseNo = licenseNoField.getText().trim();
        
        // Apply Validation  
        
        if(!name.matches(CUSTOMER_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
        } else if(!userId.matches(USER_ID_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid User ID").show();
        } else if(!address.matches(CUSTOMER_ADDRESS_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
        } else if(!contact.matches(CUSTOMER_CONTACT_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer contact number").show();
        } else if(!nic.matches(CUSTOMER_NIC_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer NIC number").show();
        } else if(!licenseNo.matches(CUSTOMER_LICENSENO_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer license number").show();
        } else {
            try {
                
                CustomerDTO cusDTO = new CustomerDTO(Integer.parseInt(userId), name, address, contact, nic , licenseNo);
                boolean result = customerModel.saveCustomer(cusDTO);

                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
                    cleanFields();
                    loadCustomerTable();
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
                    new Alert(Alert.AlertType.ERROR, "Enter Customer ID or Name").show();
                    return;
                }

                CustomerDTO customerDTO = null;

                // Search by Customer ID
                if (keyword.matches(CUSTOMER_ID_REGEX)) {
                    customerDTO = customerModel.searchCustomer(keyword);
                }
                //Search by Customer Name
                else if (keyword.matches(CUSTOMER_NAME_REGEX)) {
                    customerDTO = customerModel.searchCustomerByName(keyword);
                }
                else {
                    new Alert(Alert.AlertType.ERROR, "Invalid search keyword").show();
                    return;
                }

                if (customerDTO != null){
                    customerIdField.setText(String.valueOf(customerDTO.getCustomerId()));
                    userIdField.setText(String.valueOf(customerDTO.getUserId()));
                    nameField.setText(customerDTO.getName());
                    addressField.setText(customerDTO.getAddress());
                    contactField.setText(customerDTO.getContact());
                    nicField.setText(customerDTO.getNicPassport());
                    licenseNoField.setText(customerDTO.getLicenseNo());

                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer not found").show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    
    @FXML
    private void handleCustomerUpdate() {
    
        try {
        
            String cusId = customerIdField.getText().trim();
            String userId = userIdField.getText().trim();
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String contact = contactField.getText().trim();
            String nic = nicField.getText().trim();
            String licenseNo = licenseNoField.getText().trim();
            
            // Apply Validation

            if(!cusId.matches(CUSTOMER_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid customer id").show();
            } else if(!userId.matches(USER_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user id").show();
            } else if(!name.matches(CUSTOMER_NAME_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
            } else if(!address.matches(CUSTOMER_ADDRESS_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            } else if(!contact.matches(CUSTOMER_CONTACT_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid customer contact").show();
            } else if(!nic.matches(CUSTOMER_NIC_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid customer NIC").show();
            } else if(!licenseNo.matches(CUSTOMER_LICENSENO_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid customer license number").show();
            } else {
            
                CustomerDTO customerDTO = new CustomerDTO(Integer.parseInt(cusId) , Integer.parseInt(userId) , name, address,contact, nic, licenseNo);
                boolean result = customerModel.updateCustomer(customerDTO);
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
                    cleanFields();
                    loadCustomerTable();
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
    private void handleCustomerDelete() {
    
        try {
        
            String id = customerIdField.getText();

            if(!id.matches(CUSTOMER_ID_REGEX)) {
                 new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
            } else {
            
                boolean result = customerModel.deleteCustomer(id);
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully!").show();
                    cleanFields();
                    loadCustomerTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }
                
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "You cannot delete this customer. Bookings exist").show();
        }
        
    }
    
    
    @FXML
    private void handleReset() {
        cleanFields();
    }
    
    private void cleanFields() {
        customerIdField.setText("");
        userIdField.setText("");
        nameField.setText("");
        addressField.setText("");
        contactField.setText("");
        nicField.setText("");
        licenseNoField.setText("");
    }
    
    private void loadCustomerTable() {
    
        try {
        
            List<CustomerDTO> customerList = customerModel.getCustomers();
            
            ObservableList<CustomerDTO> obList = FXCollections.observableArrayList();
            
            for (CustomerDTO customerDTO : customerList) {
                obList.add(customerDTO);
            }
            
            tblCustomer.setItems(obList);
            
            lblTotalCustomers.setText(String.valueOf(customerList.size()));
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
