//package lk.ijse.hcj_car_rentsystem.model;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import lk.ijse.hcj_car_rentsystem.dto.UserDTO;
//import lk.ijse.hcj_car_rentsystem.util.CrudUtil;
//
//public class UserModel {
//
//    public boolean saveUser(UserDTO userDTO) throws SQLException  {
//
//        boolean result =
//                CrudUtil.execute(
//                        "INSERT INTO users (name, user_name, password, email ,address, role, nic, contact) VALUES (?,?,?,?,?,?,?,?)",
//                        userDTO.getName(),
//                        userDTO.getUserName(),
//                        userDTO.getPassword(),
//                        userDTO.getEmail(),
//                        userDTO.getAddress(),
//                        userDTO.getRole(),
//                        userDTO.getNic(),
//                        userDTO.getContact()
//                );
//
//        return result;
//    }
//
//    public boolean updateUser(UserDTO userDTO) throws SQLException {
//
//        boolean result =
//                CrudUtil.execute(
//                        "UPDATE users SET name=?,user_name=?, password=?, email=? ,address=?, role=?, nic=?, contact=? WHERE user_id=?",
//                        userDTO.getName(),
//                        userDTO.getUserName(),
//                        userDTO.getPassword(),
//                        userDTO.getEmail(),
//                        userDTO.getAddress(),
//                        userDTO.getRole(),
//                        userDTO.getNic(),
//                        userDTO.getContact(),
//                        userDTO.getUserId()
//                );
//
//        return result;
//    }
//
//    public boolean deleteUser(int id) throws SQLException {
//        boolean result =
//                CrudUtil.execute(
//                        "DELETE FROM users WHERE user_id=?",
//                        id
//                );
//
//        return result;
//    }
//
//    public UserDTO searchUser(int id) throws SQLException {
//
//        ResultSet rs =
//                CrudUtil.execute(
//                        "SELECT * FROM users WHERE user_id=?",
//                        id
//                );
//
//        if(rs.next()) {
//            int userId = rs.getInt("user_id");
//            String name = rs.getString("name");
//            String userName = rs.getString("user_name");
//            String password = rs.getString("password");
//            String email = rs.getString("email");
//            String address = rs.getString("address");
//            String contact = rs.getString("contact");
//            String nic = rs.getString("nic");
//            String role = rs.getString("role");
//
//            return new UserDTO(userId, name,userName,password,email, address, role, nic, contact);
//        }
//
//        return null;
//    }
//
//    public UserDTO searchUserByName(String name) throws SQLException {
//
//        ResultSet rs =
//                CrudUtil.execute(
//                        "SELECT * FROM users WHERE name=?",
//                        name
//                );
//
//        if(rs.next()) {
//
//            int userId = rs.getInt("user_id");
//            String fullName = rs.getString("name");
//            String userName = rs.getString("user_name");
//            String password = rs.getString("password");
//            String email = rs.getString("email");
//            String address = rs.getString("address");
//            String contact = rs.getString("contact");
//            String nic = rs.getString("nic");
//            String role = rs.getString("role");
//
//            return new UserDTO( userId,fullName, userName,password ,email, address, role, nic, contact);
//        }
//
//        return null;
//    }
//
//
//    public List<UserDTO> getUsers() throws SQLException {
//        ResultSet rs =
//                CrudUtil.execute(
//                        "SELECT * FROM users ORDER BY user_id DESC"
//                );
//
//        List<UserDTO> userList = new ArrayList<>();
//
//        while(rs.next()) {
//            UserDTO userDTO = new UserDTO(
//                rs.getInt("user_id"),
//                rs.getString("name"),
//                rs.getString("user_name"),
//                rs.getString("password"),
//                rs.getString("email"),
//                rs.getString("address"),
//                rs.getString("role"),
//                rs.getString("nic"),
//                rs.getString("contact")
//
//            );
//
//            userList.add(userDTO);
//        }
//
//        return userList;
//    }
//
//}
