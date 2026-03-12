package lk.ijse.hcj_car_rentsystem.dao.custom;

import lk.ijse.hcj_car_rentsystem.dao.CrudDAO;
import lk.ijse.hcj_car_rentsystem.dto.UserDTO;
import lk.ijse.hcj_car_rentsystem.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    //public boolean saveUser(UserDTO userDTO) throws SQLException;
    //public boolean updateUser(UserDTO userDTO) throws SQLException;
    //public boolean deleteUser(int id) throws SQLException;
   // public UserDTO searchUser(int id) throws SQLException;
    public UserDTO searchUserByName(String name) throws SQLException;

    User search(User id) throws SQLException;
    // public List<UserDTO> getUsers() throws SQLException;

}
