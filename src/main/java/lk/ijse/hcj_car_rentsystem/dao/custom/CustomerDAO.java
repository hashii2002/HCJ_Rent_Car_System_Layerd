package lk.ijse.hcj_car_rentsystem.dao.custom;

import lk.ijse.hcj_car_rentsystem.dao.CrudDAO;
import lk.ijse.hcj_car_rentsystem.dto.CustomerDTO;
import lk.ijse.hcj_car_rentsystem.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
   // public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;
    //public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
   // public boolean deleteCustomer(String id) throws SQLException;
   // public CustomerDTO searchCustomer(String id) throws SQLException;
    public CustomerDTO searchCustomerByName(String name) throws SQLException;
    //public List<CustomerDTO> getCustomers() throws SQLException;
}
