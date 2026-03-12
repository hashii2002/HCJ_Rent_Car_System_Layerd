package lk.ijse.hcj_car_rentsystem.dao.custom.impl;

import lk.ijse.hcj_car_rentsystem.dao.custom.UserDAO;
import lk.ijse.hcj_car_rentsystem.dto.UserDTO;
import lk.ijse.hcj_car_rentsystem.entity.User;
import lk.ijse.hcj_car_rentsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    public boolean save(User user) throws SQLException{

        boolean result =
                CrudUtil.execute(
                        "INSERT INTO users (name, user_name, password, email ,address, role, nic, contact) VALUES (?,?,?,?,?,?,?,?)",
                        user.getName(),
                        user.getUserName(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getAddress(),
                        user.getRole(),
                        user.getNic(),
                        user.getContact()
                );

        return result;
    }

    public boolean update(User user) throws SQLException {

        boolean result =
                CrudUtil.execute(
                        "UPDATE users SET name=?,user_name=?, password=?, email=? ,address=?, role=?, nic=?, contact=? WHERE user_id=?",
                        user.getName(),
                        user.getUserName(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getAddress(),
                        user.getRole(),
                        user.getNic(),
                        user.getContact(),
                        user.getUserId()
                );

        return result;
    }


    public boolean delete(String id) throws SQLException {
        boolean result =
                CrudUtil.execute(
                        "DELETE FROM users WHERE user_id=?",
                        Integer.parseInt(id)
                );

        return result;
    }

    public User search(String id) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM users WHERE user_id=?",
                        Integer.parseInt(id)
                );

        if(rs.next()) {
            int userId = rs.getInt("user_id");
            String name = rs.getString("name");
            String userName = rs.getString("user_name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String contact = rs.getString("contact");
            String nic = rs.getString("nic");
            String role = rs.getString("role");

            return new User(userId, name,userName,password,email, address, role, nic, contact);
        }

        return null;
    }

    public UserDTO searchUserByName(String name) throws SQLException {

        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM users WHERE name=?",
                        name
                );

        if(rs.next()) {

            int userId = rs.getInt("user_id");
            String fullName = rs.getString("name");
            String userName = rs.getString("user_name");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String address = rs.getString("address");
            String contact = rs.getString("contact");
            String nic = rs.getString("nic");
            String role = rs.getString("role");

            return new UserDTO( userId,fullName, userName,password ,email, address, role, nic, contact);
        }

        return null;
    }


    public List<User> get() throws SQLException {
        ResultSet rs =
                CrudUtil.execute(
                        "SELECT * FROM users ORDER BY user_id DESC"
                );

        List<User> userList = new ArrayList<>();

        while(rs.next()) {
            User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("role"),
                    rs.getString("nic"),
                    rs.getString("contact")

            );

            userList.add(user);
        }

        return userList;
    }


    @Override
    public User search(User id) throws SQLException {
        return null;
    }
}
