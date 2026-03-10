package lk.ijse.hcj_car_rentsystem.bo.custom;

import lk.ijse.hcj_car_rentsystem.bo.SuperBO;
import lk.ijse.hcj_car_rentsystem.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
    public boolean deleteCustomer(String id) throws SQLException;
    public CustomerDTO searchCustomer(String id) throws SQLException;
    public CustomerDTO searchCustomerByName(String name) throws SQLException;
    public List<CustomerDTO> getCustomers() throws SQLException;


}
