package ua.taxi.dao.sql;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by andrii on 27.06.16.
 */
public interface GenericDao<T> {

        // return entity with id
        int create(T el) throws SQLException;

        boolean delete(T el) throws SQLException;

        boolean delete(int id) throws SQLException;

        T findById(int id) throws SQLException;

        List<T> getAll(int offset, int length) throws SQLException;

        // use el id for find in db
        T update(int id, T el) throws SQLException;

}
