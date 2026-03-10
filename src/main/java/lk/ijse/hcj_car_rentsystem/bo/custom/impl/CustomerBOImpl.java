package lk.ijse.hcj_car_rentsystem.bo.custom.impl;

import lk.ijse.hcj_car_rentsystem.bo.custom.CustomerBO;
import lk.ijse.hcj_car_rentsystem.dao.DAOFactory;
import lk.ijse.hcj_car_rentsystem.dao.custom.CustomerDAO;
import lk.ijse.hcj_car_rentsystem.dto.CustomerDTO;
import lk.ijse.hcj_car_rentsystem.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.save(new Customer
                (customerDTO.getUserId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getContact(),
                customerDTO.getNicPassport(),
                customerDTO.getLicenseNo()));
    }
    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(new Customer(
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getContact(),
                customerDTO.getNicPassport(),
                customerDTO.getLicenseNo(),
                customerDTO.getCustomerId()
        ));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException{
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException{
        Customer customer = customerDAO.search(id);

        if (customer != null) {
            return new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact(),
                    customer.getNicPassport(),
                    customer.getLicenseNo()
            );
        }

        return null;
    }

    @Override
    public CustomerDTO searchCustomerByName(String name) throws SQLException{
        CustomerDTO customer = customerDAO.searchCustomerByName(name);

        if (customer != null) {
            return new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact(),
                    customer.getNicPassport(),
                    customer.getLicenseNo()
            );
        }
        return null;
    }

    @Override
    public List<CustomerDTO> getCustomers() throws SQLException {

        List<Customer> customers = customerDAO.get();
        List<CustomerDTO> dtoList = new ArrayList<>();

        for (Customer c : customers) {
            dtoList.add(new CustomerDTO(
                    c.getCustomerId(),
                    c.getUserId(),
                    c.getName(),
                    c.getAddress(),
                    c.getContact(),
                    c.getNicPassport(),
                    c.getLicenseNo()
            ));
        }

        return dtoList;
    }
}
