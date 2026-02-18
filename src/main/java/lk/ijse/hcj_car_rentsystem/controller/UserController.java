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
import lk.ijse.hcj_car_rentsystem.model.UserModel;
import lk.ijse.hcj_car_rentsystem.dto.UserDTO;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class UserController implements Initializable {

    @FXML
    private TextField userIdField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField nicField;
    @FXML
    private TextField roleField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField searchField;
    @FXML
    private Label lblTotalUsers;
    
    @FXML
    private TableView tblUser;
    @FXML
    private TableColumn colUserId;
    @FXML
    private TableColumn colFullName;
    @FXML
    private TableColumn colUserName;
    @FXML
    private TableColumn colPassword;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colNic;
    @FXML
    private TableColumn colRole;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colEmail;
    
    private final String USER_ID_REGEX = "^[0-9]+$";
    private final String USER_FULLNAME_REGEX = "^[A-Za-z]+( [A-Za-z]+)*$";
    private final String USER_NAME_REGEX = "^[A-Za-z][A-Za-z0-9]{4,19}$";
    private final String USER_PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{3,}$";
    private final String USER_ADDRESS_REGEX = "^[A-Za-z0-9 ,./-]{5,}$";
    private final String USER_CONTACT_REGEX = "^[0-9]{10}$";
    private final String USER_NIC_REGEX = "^(\\d{9}[VvXx]|\\d{12})$";
    private final String USER_ROLE_REGEX = "^[A-Za-z]+$";
    private final String USER_EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    
    private final UserModel userModel = new UserModel();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("User FXML is loaded!");
        
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadUserTable();
        
        // Add double click event on table row
        tblUser.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                UserDTO selectedCustomer = (UserDTO)tblUser.getSelectionModel().getSelectedItem();
                if (selectedCustomer != null) {
                    populateFields(selectedCustomer);
                }
            }
        });
    } 
    
    private void populateFields(UserDTO userDTO) {
        
        userIdField.setText(String.valueOf(userDTO.getUserId()));
        fullNameField.setText(userDTO.getName());
        userNameField.setText(userDTO.getUserName());
        passwordField.setText(userDTO.getPassword());
        addressField.setText(userDTO.getAddress());
        contactField.setText(userDTO.getContact());
        nicField.setText(userDTO.getNic());
        roleField.setText(userDTO.getRole());
        emailField.setText(userDTO.getEmail());
    } 
    
    @FXML
    private void handleSaveUser() {
       
        String name = fullNameField.getText().trim();
        String userName = userNameField.getText().trim();
        String password = passwordField.getText().trim();
        String address = addressField.getText().trim();
        String contact = contactField.getText().trim();
        String nic = nicField.getText().trim();
        String salary = emailField.getText().trim();
        String role = roleField.getText().trim();
        String email = emailField.getText().trim();
        
        // Apply Validation  
        
        if(!name.matches(USER_FULLNAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Full Name!").show();
        } else if(!userName.matches(USER_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid user name!").show();
        } else if(!password.matches(USER_PASSWORD_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid user password!").show();
        } else if(!address.matches(USER_ADDRESS_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid user address!").show();
        } else if(!contact.matches(USER_CONTACT_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid user contact number!").show();
        } else if(!nic.matches(USER_NIC_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid user NIC number!").show();
        } else if(!salary.matches(USER_EMAIL_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid user Email!").show();
        } else if(!role.matches(USER_ROLE_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid user role!").show();
        } else {
            try {
                
                UserDTO userDTO = new UserDTO(name,userName,password, email, address,role, nic, contact );
                boolean result = userModel.saveUser(userDTO);

                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "user saved successfully!").show();
                    cleanFields();
                    loadUserTable();
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
                    new Alert(Alert.AlertType.ERROR, "Enter User ID or Name").show();
                    return;
                }

                UserDTO userDTO = null;

                // Search by User ID
                if (keyword.matches(USER_ID_REGEX)) {
                    int userId = Integer.parseInt(keyword);
                    userDTO = userModel.searchUser(userId);
                }
                //Search by User Name
                else if (keyword.matches(USER_FULLNAME_REGEX)) {
                    userDTO = userModel.searchUserByName(keyword);
                }
                else {
                    new Alert(Alert.AlertType.ERROR, "Invalid search keyword").show();
                    return;
                }

                if (userDTO != null){
        
                    userIdField.setText(String.valueOf(userDTO.getUserId()));
                    fullNameField.setText(userDTO.getName());
                    userNameField.setText(userDTO.getUserName());
                    passwordField.setText(userDTO.getName());
                    emailField.setText(userDTO.getEmail());
                    addressField.setText(userDTO.getAddress());
                    contactField.setText(userDTO.getContact());
                    nicField.setText(userDTO.getNic());
                    roleField.setText(userDTO.getRole());

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
    private void handleUserUpdate() {
    
        try {
            
            String userId = userIdField.getText().trim();
            String name = fullNameField.getText().trim();
            String userName = userNameField.getText().trim();
            String password = passwordField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String contact = contactField.getText().trim();
            String nic = nicField.getText().trim();
            String role = roleField.getText().trim();
            
            // Apply Validation

            if(!userId.matches(USER_ID_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user id!").show();
            } else if(!name.matches(USER_FULLNAME_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user full name!").show();
            } else if(!userName.matches(USER_NAME_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user name!").show();
            } else if(!password.matches(USER_PASSWORD_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user password!").show();
            } else if(!address.matches(USER_ADDRESS_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user address!").show();
            } else if(!contact.matches(USER_CONTACT_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user contact!").show();
            } else if(!nic.matches(USER_NIC_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user NIC!").show();
            } else if(!email.matches(USER_EMAIL_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user Email!").show();
            } else if(!role.matches(USER_ROLE_REGEX)) {
                new Alert(Alert.AlertType.ERROR, "Invalid user role!").show();
            } else {
            
                UserDTO userDTO = new UserDTO(Integer.parseInt(userId) ,name,userName,password ,email, address, role, nic , contact);
                boolean result = userModel.updateUser(userDTO);
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
                    cleanFields();
                    loadUserTable();
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
    private void handleUserDelete() {
    
        try {
        
            String id = userIdField.getText();

            if(!id.matches(USER_ID_REGEX)) {
                 new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
            } else {
            
                boolean result = userModel.deleteUser(Integer.parseInt(id));
                
                if(result) {
                    new Alert(Alert.AlertType.INFORMATION, "User deleted successfully!").show();
                    cleanFields();
                    loadUserTable();
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
        userIdField.setText("");
        fullNameField.setText("");
        userNameField.setText("");
        passwordField.setText("");
        addressField.setText("");
        contactField.setText("");
        nicField.setText("");
        emailField.setText("");
        roleField.setText("");
    }
    
    private void loadUserTable() {
    
        try {
        
            List<UserDTO> userList = userModel.getUsers();
            
            ObservableList<UserDTO> obList = FXCollections.observableArrayList();
            
            for (UserDTO userDTO : userList) {
                obList.add(userDTO);
            }
            tblUser.setItems(obList);
            
            lblTotalUsers.setText(String.valueOf(userList.size()));
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}

