package repository;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository <T,ID> extends SuperRepository{
    boolean save(T t) throws SQLException;
    List<T> getAll() throws SQLException;
    boolean delete(ID id) throws SQLException;
    T search(ID id) throws SQLException;
    boolean update(T t) throws SQLException;
}
