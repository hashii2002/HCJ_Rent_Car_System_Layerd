package lk.ijse.hcj_car_rentsystem.bo.custom;

import lk.ijse.hcj_car_rentsystem.bo.SuperBO;
import lk.ijse.hcj_car_rentsystem.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    public boolean saveUser(UserDTO userDTO) throws SQLException;
    public boolean updateUser(UserDTO userDTO) throws SQLException;
    public boolean deleteUser(int id) throws SQLException;
    public UserDTO searchUser(int id) throws SQLException;
    public UserDTO searchUserByName(String name) throws SQLException;
    public List<UserDTO> getUsers() throws SQLException;


}
