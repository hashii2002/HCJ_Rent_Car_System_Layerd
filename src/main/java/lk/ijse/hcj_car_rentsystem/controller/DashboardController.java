package lk.ijse.hcj_car_rentsystem.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import lk.ijse.hcj_car_rentsystem.db.DBConnection;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;

public class DashboardController implements Initializable {

    @FXML
    private Label lblTotalUsers;

    @FXML
    private Label lblTotalVehicles;

    @FXML
    private Label lblTotalCustomers;

    @FXML
    private Label lblTotalDrivers;

    @FXML
    private Label lblTotalBookings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTotalUsers();
        loadTotalVehicles();
        loadTotalCustomers();
        loadTotalDrivers();
        loadTotalBookings();
        loadVehicleComparisonChart();
    }

    private void loadTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblTotalUsers.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTotalVehicles() {
        String sql = "SELECT COUNT(*) FROM vehicles";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblTotalVehicles.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTotalCustomers() {
        String sql = "SELECT COUNT(*) FROM customers";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblTotalCustomers.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTotalDrivers() {
        String sql = "SELECT COUNT(*) FROM drivers";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblTotalDrivers.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTotalBookings() {
        String sql = "SELECT COUNT(*) FROM bookings";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblTotalBookings.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private BarChart<String, Integer> vehicleComparisonChart; 

    public void loadVehicleComparisonChart() {
        try {

            ResultSet rs = CrudUtil.execute(
                "SELECT v.model, COUNT(vad.vehicle_id) AS rent_count " +
                "FROM vehicles v " +
                "LEFT JOIN vehicle_assign_details vad ON v.vehicle_id = vad.vehicle_id " +
                "GROUP BY v.model"
            );

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName("Number of Times Rented");

            while (rs.next()) {
                String model = rs.getString("model");
                int count = rs.getInt("rent_count");

                series.getData().add(new XYChart.Data<>(model, count));
            }

            vehicleComparisonChart.getData().clear();
            vehicleComparisonChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

