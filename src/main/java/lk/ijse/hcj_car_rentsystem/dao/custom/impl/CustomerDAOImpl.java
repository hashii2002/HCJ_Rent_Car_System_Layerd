package lk.ijse.hcj_car_rentsystem.dao.custom.impl;

import lk.ijse.hcj_car_rentsystem.dao.custom.CustomerDAO;
import lk.ijse.hcj_car_rentsystem.dto.CustomerDTO;
import lk.ijse.hcj_car_rentsystem.entity.Customer;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl  implements CustomerDAO {
    public boolean save(Customer customer) throws SQLException {

        boolean result =
                CrudUtil.execute(
                        "INSERT INTO customers (user_id, name, address, contact, nic_passport, license_no) VALUES (?,?,?,?,?,?)",
                        customer.getUserId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getContact(),
                        customer.getNicPassport(),
                        customer.getLicenseNo()
                );

        return result;
    }

    public boolean update(Customer customer) throws SQLException {

        boolean result =
                CrudUtil.execute(
                        "UPDATE customers SET name=?, address=?, contact=?, nic_passport=?, license_no=? WHERE customer_id=?",
                        customer.getName(),
                        customer.getAddress(),
                        customer.getContact(),
                        customer.getNicPassport(),
                        customer.getLicenseNo(),
                        customer.getCustomerId()
                );

        return result;
    }

    public boolean delete(String id) throws SQLException {
        boolean result =
                CrudUtil.execute(
                        "DELETE FROM customers WHERE customer_id=?",
                        id
                );

        return result;
    }

    @Override
    public Customer search(Customer id) throws SQLException {
        return null;
    }

    public Customer search(String id) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM customers WHERE customer_id=?",
                        id
                );

        if(rs.next()) {
            int cusId = rs.getInt("customer_id");
            int userId = rs.getInt("user_id");
            String cusName = rs.getString("name");
            String cusAddress = rs.getString("address");
            String cusContact = rs.getString("contact");
            String cusNic = rs.getString("nic_passport");
            String cusLicenseNo = rs.getString("license_no");

            return new Customer(cusId, userId, cusName, cusAddress, cusContact, cusNic, cusLicenseNo);
        }

        return null;
    }

    public CustomerDTO searchCustomerByName(String name) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM customers WHERE name=?",
                        name
                );

        if(rs.next()) {
            int cusId = rs.getInt("customer_id");
            int userId = rs.getInt("user_id");
            String cusName = rs.getString("name");
            String cusAddress = rs.getString("address");
            String cusContact = rs.getString("contact");
            String cusNic = rs.getString("nic_passport");
            String cusLicenseNo = rs.getString("license_no");

            return new CustomerDTO(cusId, userId, cusName, cusAddress, cusContact, cusNic, cusLicenseNo);
        }

        return null;
    }

    public List<Customer> get() throws SQLException {
        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM customers ORDER BY customer_id DESC"
                );

        List<Customer> customerList = new ArrayList<>();

        while(rs.next()) {
            Customer cusDTO = new Customer(
                    rs.getInt("customer_id"),
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("contact"),
                    rs.getString("nic_passport"),
                    rs.getString("license_no")

            );

            customerList.add(cusDTO);
        }

        return customerList;
    }
}
