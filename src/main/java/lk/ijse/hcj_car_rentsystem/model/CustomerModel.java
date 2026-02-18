package lk.ijse.hcj_car_rentsystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.hcj_car_rentsystem.dto.CustomerDTO;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;

public class CustomerModel {
    
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException  {
        
        boolean result = 
                CrudUtil.execute(
                        "INSERT INTO customers (user_id, name, address, contact, nic_passport, license_no) VALUES (?,?,?,?,?,?)",
                        customerDTO.getUserId(), 
                        customerDTO.getName(), 
                        customerDTO.getAddress(), 
                        customerDTO.getContact(),
                        customerDTO.getNicPassport(),
                        customerDTO.getLicenseNo()
                );
        
        return result;
    }
    
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        
        boolean result = 
                CrudUtil.execute(
                        "UPDATE customers SET name=?, address=?, contact=?, nic_passport=?, license_no=? WHERE customer_id=?", 
                        customerDTO.getName(), 
                        customerDTO.getAddress(), 
                        customerDTO.getContact(),
                        customerDTO.getNicPassport(),
                        customerDTO.getLicenseNo(),
                        customerDTO.getCustomerId()
                );
        
        return result;   
    }
    
    public boolean deleteCustomer(String id) throws SQLException {
        boolean result = 
                CrudUtil.execute(
                        "DELETE FROM customers WHERE customer_id=?", 
                        id
                );
        
        return result;  
    }
    
    public CustomerDTO searchCustomer(String id) throws SQLException {
        
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
            
            return new CustomerDTO(cusId, userId, cusName, cusAddress, cusContact, cusNic, cusLicenseNo);
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
    
    public List<CustomerDTO> getCustomers() throws SQLException {
        ResultSet rs = 
                CrudUtil.execute(
                        "SELECT * FROM customers ORDER BY customer_id DESC" 
                );
        
        List<CustomerDTO> customerList = new ArrayList<>();
        
        while(rs.next()) {
            CustomerDTO cusDTO = new CustomerDTO(
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
