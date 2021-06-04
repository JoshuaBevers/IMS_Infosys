package com.qa.ims.persistence.dao;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Order_Item;
import com.qa.ims.utils.DBUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Order_ItemsDAO implements Dao<Order_Item> {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Order_Item modelFromResultSet(ResultSet resultSet) throws SQLException {
        long order_id = resultSet.getLong("order_id");
        long item_id = resultSet.getLong("item_id");
        long quantity = resultSet.getLong("quantity");

        return new Order_Item(order_id, item_id, quantity);
    }

    @Override
    public List<Order_Item> readAll() {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders_item");) {
            List<Order_Item> order_item = new ArrayList<>();
            while (resultSet.next()) {
                order_item.add(modelFromResultSet(resultSet));
            }
            return order_item;
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public Order_Item read(Long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders_item WHERE order_id = ?");) {
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

    @Override
    public Order_Item create(Order_Item order_item) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO orders_item(order_id, item_id, quantity) VALUES (?, ?, ?)");) {
            statement.setLong(1, order_item.getOrder_id() );
            statement.setLong(2, order_item.getItem_code());
            statement.setLong(3, order_item.getQuantity());
            statement.executeUpdate();
            return readLatest();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private Order_Item readLatest() {
        try (Connection connection = DBUtils.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders_item ORDER BY order_id DESC LIMIT 1");) {
            resultSet.next();
            return modelFromResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Order_Item update(Order_Item order_item) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("UPDATE orders_item SET item_id = ?, quantity = ? WHERE order_id = ?");) {
            statement.setLong(1, order_item.getItem_code());
            statement.setLong(2, order_item.getQuantity());
            statement.setLong(3, order_item.getOrder_id());

            statement.executeUpdate();
            return read(order_item.getOrderItemsID());
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public int delete(long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM orders_item WHERE order_id = ?)");) {
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public int deleteComposite(long id, long item) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM orders_item WHERE (order_id, item_id) IN ((? ,?))");) {
            statement.setLong(1, id);
            statement.setLong(2, item);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public List<Order_Item> getItemList(long order_id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders_item WHERE order_id = ?");)
        {
            statement.setLong(1, order_id);
            ResultSet resultSet = statement.executeQuery();

            List<Order_Item> orderList = new ArrayList<>();
            while (resultSet.next()) {
                orderList.add(modelFromResultSet(resultSet));
            }
            return orderList;
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();

    }

}
