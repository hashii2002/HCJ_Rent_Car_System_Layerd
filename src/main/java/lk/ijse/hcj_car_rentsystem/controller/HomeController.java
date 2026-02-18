package lk.ijse.hcj_car_rentsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import lk.ijse.hcj_car_rentsystem.App;

public class HomeController implements Initializable {
    
    @FXML
    private Pane mainContent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Parent dashboard = App.loadFXML("Dashboard");
            mainContent.getChildren().setAll(dashboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    @FXML
    private void clickCustomerNav() throws IOException {
        Parent newFXML = App.loadFXML("Customer");
        mainContent.getChildren().setAll(newFXML);
    }
    
    @FXML
    private void clickBookingNav()throws IOException {
        Parent newFXML = App.loadFXML("Booking");
        mainContent.getChildren().setAll(newFXML);  
    }
    
    @FXML
    private void clickVehicleNav()throws IOException {
        Parent newFXML = App.loadFXML("Vehicle");
        mainContent.getChildren().setAll(newFXML);
    }
    
    @FXML
    private void clickUserNav()throws IOException {
        Parent newFXML = App.loadFXML("User");
        mainContent.getChildren().setAll(newFXML);
    }
    
    @FXML
    private void clickPaymentNav()throws IOException {
        Parent newFXML = App.loadFXML("RentalPayment");
        mainContent.getChildren().setAll(newFXML);
    }
    
    @FXML
    private void clickDriverNav()throws IOException {
        Parent newFXML = App.loadFXML("Driver");
        mainContent.getChildren().setAll(newFXML);
    }
    
    @FXML
    private void clickReportNav()throws IOException {
        Parent newFXML = App.loadFXML("Report");
        mainContent.getChildren().setAll(newFXML);
    }
    
    @FXML
    private void clickHomeNav()throws IOException {
        Parent newFXML = App.loadFXML("Dashboard");
        mainContent.getChildren().setAll(newFXML);
    }
    
    @FXML
    private void clickLoginNav() {
        try {
            App.setRoot("Login"); 
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load Login Page").show();
        }
    }
}
