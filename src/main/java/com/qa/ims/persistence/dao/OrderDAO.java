package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
                     .prepareStatement("UPDATE orders SET customer_id = ? WHERE id = ?");) {
            statement.setLong(1, order.getCustomerID());
            statement.setLong(2, order.getId());
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


    // nested class to handle Order_Items
    public static class OrderItems implements Dao<Order.OrderItems> {

        @Override
        public Order.OrderItems modelFromResultSet(ResultSet resultSet) throws SQLException {
            long order_id = resultSet.getLong("order_id");
            long item_id = resultSet.getLong("item_id");
            long quantity = resultSet.getLong("quantity");

            return new Order.OrderItems(order_id, item_id, quantity);
        }


        /**
         * Reads all orders from the database
         *
         * @return A list of all the orders items
         */

        @Override
        public List<Order.OrderItems> readAll() {
            try (Connection connection = DBUtils.getInstance().getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM order_item");) {
                List<Order.OrderItems> order_item = new ArrayList<>();
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

        public Order.OrderItems readLatest() {
            try (Connection connection = DBUtils.getInstance().getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM order_item ORDER BY order_id DESC LIMIT 1");) {
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
         * @param orderItems - takes in a order object. id will be ignored
         */

        @Override
        public Order.OrderItems create(Order.OrderItems orderItems) {
            //creates order on order table.
            try (Connection connection = DBUtils.getInstance().getConnection();
                 PreparedStatement statement = connection
                         .prepareStatement("INSERT INTO order_item(order_id, item_id, quantity) VALUES (?, ?, ?)");) {
                statement.setLong(1, orderItems.getOrder_id());
                statement.setLong(2, orderItems.getItem_code());
                statement.setLong(3, orderItems.getQuantity());
                statement.executeUpdate();
                return readLatest();
            } catch (Exception e) {
                LOGGER.debug(e);
                LOGGER.error(e.getMessage());
            }


            return null;
        }

        @Override
        public Order.OrderItems read(Long id) {
            try (Connection connection = DBUtils.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_item WHERE order_id = ?");) {
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
        public Order.OrderItems update(Order.OrderItems order) {
            try (Connection connection = DBUtils.getInstance().getConnection();
                 PreparedStatement statement = connection
                         .prepareStatement("UPDATE order_item SET item_id = ?, quantity = ? WHERE order_id = ?");) {
                statement.setLong(1, order.getItem_code());
                statement.setLong(2, order.getQuantity());
                statement.setLong(3, order.getOrder_id());

                statement.executeUpdate();
                return read(order.getOrderItemsID());
            } catch (Exception e) {
                LOGGER.debug(e);
                LOGGER.error(e.getMessage());
            }
            return null;
        }

        /**
         * Deletes a order in the database
         *
         * @param id - id of the order_item
         */
        @Override
        public int delete(long id) {
            try (Connection connection = DBUtils.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM order_item WHERE order_id = ?)");) {
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
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM order_item WHERE (order_id, item_id) IN ((? ,?))");) {
                statement.setLong(1, id);
                statement.setLong(2, item);
                return statement.executeUpdate();
            } catch (Exception e) {
                LOGGER.debug(e);
                LOGGER.error(e.getMessage());
            }
            return 0;
        }

        public List<Order.OrderItems> getItemList(long order_id) {
            try (Connection connection = DBUtils.getInstance().getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM order_item WHERE order_id = ?");)
            {
                statement.setLong(1, order_id);
                ResultSet resultSet = statement.executeQuery();

                List<Order.OrderItems> orderList = new ArrayList<>();
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

        public double getTotal(long order_id){
            List<Order.OrderItems> orderItemsList = getItemList(order_id);
            //get item id and quantity.
            double total = 0;
            for (Order.OrderItems morder : orderItemsList) {

                LOGGER.info(morder);
            }
            return 5;
        }

    }

}
