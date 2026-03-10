package lk.ijse.hcj_car_rentsystem.bo.custom.impl;

import lk.ijse.hcj_car_rentsystem.bo.custom.UserBO;
import lk.ijse.hcj_car_rentsystem.dao.DAOFactory;
import lk.ijse.hcj_car_rentsystem.dao.custom.UserDAO;
import lk.ijse.hcj_car_rentsystem.dto.UserDTO;
import lk.ijse.hcj_car_rentsystem.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public boolean saveUser(UserDTO userDTO) throws SQLException {
        return userDAO.save(new User(
                userDTO.getName(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getAddress(),
                userDTO.getRole(),
                userDTO.getNic(),
                userDTO.getContact()
        ));
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws SQLException {
        return userDAO.update(new User(
                userDTO.getUserId(),
                userDTO.getName(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getAddress(),
                userDTO.getRole(),
                userDTO.getNic(),
                userDTO.getContact()
        ));
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        return userDAO.delete(String.valueOf(id));
    }

    @Override
    public UserDTO searchUser(int id) throws SQLException {
        User user = userDAO.search(String.valueOf(id));

        if (user != null) {
            return new UserDTO(
                    user.getUserId(),
                    user.getName(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getRole(),
                    user.getNic(),
                    user.getContact()
            );
        }
        return null;
    }

    @Override
    public UserDTO searchUserByName(String name) throws SQLException {

        UserDTO user = userDAO.searchUserByName(name);

        if (user != null) {
            return new UserDTO(
                    user.getUserId(),
                    user.getName(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getRole(),
                    user.getNic(),
                    user.getContact()
            );
        }
        return null;
    }

    @Override
    public List<UserDTO> getUsers() throws SQLException {

        List<User> users = userDAO.get();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : users) {
            userDTOList.add(new UserDTO(
                    user.getUserId(),
                    user.getName(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getRole(),
                    user.getNic(),
                    user.getContact()
            ));
        }
        return userDTOList;
    }
}
