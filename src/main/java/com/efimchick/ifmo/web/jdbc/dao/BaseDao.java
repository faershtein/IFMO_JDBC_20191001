package com.efimchick.ifmo.web.jdbc.dao;

import com.efimchick.ifmo.web.jdbc.ConnectionSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao<T, K extends Number> implements Dao<T, K> {

    protected ConnectionSource connectionSource = ConnectionSource.instance();

    @Override
    public Optional<T> getById(K id) {
        try (Connection connection = connectionSource.createConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("select * from " + getTableName() + " where id = ?");
            statement.setLong(1, id.longValue());
            ResultSet resultSet = statement.executeQuery();
            return Optional.ofNullable(resultSet.next() ? extractFromResultSet(resultSet) : null);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<T> getAll() {
        return findList("select * from " + getTableName());
    }

    @Override
    public T save(T t) {
        delete(t);
        try (Connection connection = connectionSource.createConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(prepareInsertQuery(t));
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(T t) {
        try (Connection connection = connectionSource.createConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("delete from " + getTableName() + " where id = ?");
            statement.setLong(1, getId(t).longValue());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected List<T> findList(String query) {
        try (Connection connection = connectionSource.createConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(extractFromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    protected abstract T extractFromResultSet(ResultSet resultSet) throws SQLException;

    protected abstract String getTableName();

    protected abstract K getId(T t);

    protected abstract String prepareInsertQuery(T t);
}
