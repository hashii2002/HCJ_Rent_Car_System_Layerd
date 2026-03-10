package lk.ijse.hcj_car_rentsystem.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO <T> extends SuperDAO {

    public boolean save(T dto) throws SQLException;
    public boolean update(T dto) throws SQLException;
    public boolean delete(String id) throws SQLException;
    public T search(String id) throws SQLException;
    public List<T> get() throws SQLException;

}
