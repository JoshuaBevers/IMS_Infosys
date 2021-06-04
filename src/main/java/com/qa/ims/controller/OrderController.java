package com.qa.ims.controller;

import java.util.List;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.utils.Utils;

/**
 * Takes in Order details for CRUD functionality
 */
public class OrderController implements CrudController<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    private OrderDAO OrderDAO;
    private Utils utils;

    public OrderController(OrderDAO OrderDAO, Utils utils) {
        super();
        this.OrderDAO = OrderDAO;
        this.utils = utils;
    }

    /**
     * Reads all Items to the logger
     */
    @Override
    public List<Order> readAll() {

        List<Order> orders = OrderDAO.readAll();
        for (Order order : orders) {
            LOGGER.info(order);
        }
        return orders;
    }

    /**
     * Creates a Item by taking in user input
     */
    @Override
    public Order create() {
        LOGGER.info("Please enter a customer ID");
        Long CustomerID = utils.getLong();
        LOGGER.info("Please enter any comments");
        String comments = utils.getString();

        //fire off the create order_item DAO.
        Order Order = OrderDAO.create(new Order(CustomerID, comments));
        LOGGER.info(Order != null ? "Order created" : "Issues aquired during creation.");
        return Order;
    }


    /**
     * Updates an existing Item by taking in user input
     */
    @Override
    public Order update() {

        LOGGER.info("Please enter the id of the Order you would like to update \n" + OrderDAO.readAll());
        Long order_id = utils.getLong();
        LOGGER.info("Please enter a customer ID");
        Long CustomerID = utils.getLong();
        LOGGER.info("Please enter any comments");
        String comments = utils.getString();
        Order order = OrderDAO.update(new Order(order_id, CustomerID, comments));
        LOGGER.info("Customer Updated");
        return order;
    }

    /**
     * Deletes an existing Item by the id of the Item
     *
     * @return
     */
    @Override
    public int delete() {
        LOGGER.info("Please enter the id of the Order you would like to delete");
        Long id = utils.getLong();
        return OrderDAO.delete(id);

    }

}