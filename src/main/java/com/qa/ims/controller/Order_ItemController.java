package com.qa.ims.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Locale;


import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.Order_ItemsDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order_Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.utils.Utils;

public class Order_ItemController implements CrudController<Order_Item> {

    public static final Logger LOGGER = LogManager.getLogger();

    private Order_ItemsDAO OrderItemsDAO;
    private ItemDAO itemDAO;
    private Utils utils;

    public Order_ItemController(Order_ItemsDAO OrderItemsDAO, ItemDAO itemDAO, Utils utils) {
        super();
        this.utils = utils;
        this.itemDAO = itemDAO;
        this.OrderItemsDAO = OrderItemsDAO;
    }

    @Override
    public List<Order_Item> readAll() {

        List<Order_Item> items = OrderItemsDAO.readAll();
        double total = 0;

        LOGGER.info("Please select an option below");
        LOGGER.info("READALL: Reads out all the orders in the system.");
        LOGGER.info("TOTAL: Read the total cost for an order.");
        LOGGER.info("RETURN: Returns to a previous menu");

        //get the users answer
        String answer = utils.getString();

        switch (answer.toLowerCase()) {
            case "readall":
                for (Order_Item item : items) {
                    LOGGER.info(item);
                }
                break;
            case "total":
                LOGGER.info("Please enter the order you wish to get totals on");
                //get order ID.
                long orderID = utils.getLong();
                //search order_items for all orders with the id
                List<Order_Item> order = OrderItemsDAO.getItemList(orderID);

                for (Order_Item item : order) {
                    long itemcode = item.getItem_code();
                    //read the item
                    Item curItem = itemDAO.read(itemcode);
                    double price = curItem.getItemPrice();
                    LOGGER.info("The cost of " + curItem.getItemName() + " is " + curItem.getItemPrice());
                    total = total + price;
                }
                LOGGER.info("The total is: " + total);
            default:
                break;
        }


        return items;
    }

    @Override
    public Order_Item create() {
        LOGGER.info("Please enter a order ID");
        long orderID = utils.getLong();
        LOGGER.info("Please enter a customer ID");
        long customerID = utils.getLong();
        LOGGER.info("Please enter how many of this item you want.");
        long quantity = utils.getLong();

        Order_Item orderItem = OrderItemsDAO.create(new Order_Item(orderID, customerID, quantity));
        LOGGER.info("item order created");
        return orderItem;
    }

    @Override
    public Order_Item update() {
        LOGGER.info("Please enter the order Id you wish to update");
        long id = utils.getLong();
        LOGGER.info("please select the item on the order you wish to modify");
        long item_id = utils.getLong();
        LOGGER.info("Please enter how many you would like.");
        long quantity = utils.getLong();
        Order_Item updatedOrder = OrderItemsDAO.update(new Order_Item(id, item_id, quantity));
        LOGGER.info(updatedOrder != null ? "Order created" : "Issues acquired during update." + updatedOrder);
        return updatedOrder;
    }

    @Override
    public int delete() {
//        LOGGER.info(OrderItemsDAO.readAll());
        LOGGER.info("Please enter the order Id where you want to delete an item");
        Long order_id = utils.getLong();
        LOGGER.info("Please enter the item you want to delete from the order");
        Long item_id = utils.getLong();
        return OrderItemsDAO.deleteComposite(order_id, item_id);
    }
}
