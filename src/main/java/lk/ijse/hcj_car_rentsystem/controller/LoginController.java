package lk.ijse.hcj_car_rentsystem.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.hcj_car_rentsystem.App;
import lk.ijse.hcj_car_rentsystem.db.DBConnection;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void login() throws IOException {

        String userName = usernameField.getText();
        String password = passwordField.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields").show();
            return;
        }

        String sql = "SELECT * FROM users WHERE user_name=? AND password=?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, userName);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful");
                App.setRoot("Home");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed. Please Try Again!");
                alert.setHeaderText("Invalid username or password");
                alert.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database connection error").show();
        }
    }

    @FXML
    private void forgotPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgot Password");
        alert.setHeaderText("Password Recovery");
        alert.setContentText(
                "Please contact the system administrator\n"
              + "or use your registered email to reset the password."
        );
        alert.show();
    }
}
