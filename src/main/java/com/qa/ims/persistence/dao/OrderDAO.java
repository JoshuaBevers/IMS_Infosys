package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements Dao<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long customer_id = resultSet.getLong("customer_id");
        String comments = resultSet.getString("comments");

        return new Order(id, customer_id, comments);
    }

    /**
     * Reads all orders from the database
     *
     * @return A list of all the orders
     */

    @Override
    public List<Order> readAll() {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(modelFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Order readLatest() {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");) {
            resultSet.next();
            return modelFromResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Creates a order in the database
     *
     * @param order - takes in a order object. id will be ignored
     */

    @Override
    public Order create(Order order) {
        //creates order on order table.
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO orders(customer_id, comments) VALUES (?, ?)");) {
            statement.setDouble(1, order.getCustomerID());
            statement.setString(2, order.getComments());
            statement.executeUpdate();
            return readLatest();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Order read(Long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?");) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                resultSet.next();
                return modelFromResultSet(resultSet);
            }
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Updates a order in the database
     *
     * @param order - takes in a order object, the id field will be used to
     *              update that order in the database
     * @return
     */
    @Override
    public Order update(Order order) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("UPDATE orders SET customer_id = ?, comments = ? WHERE id = ?");) {
            statement.setLong(1, order.getCustomerID());
            statement.setString(2, order.getComments());
            statement.setLong(3, order.getId());
            statement.executeUpdate();
            return read(order.getId());
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a order in the database
     *
     * @param id - id of the order
     */
    @Override
    public int delete(long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");) {
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

}
