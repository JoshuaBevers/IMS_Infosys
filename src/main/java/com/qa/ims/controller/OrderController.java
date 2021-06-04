package com.qa.ims.controller;

import java.util.List;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderDAO.OrderItems;
import com.qa.ims.utils.Utils;

/**
 * Takes in Order details for CRUD functionality
 */
public class OrderController implements CrudController<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    private CustomerDAO CustomerDAO;
    private OrderDAO OrderDAO;
    private ItemDAO ItemDAO;
    private Utils utils;
    private OrderItems OrderItems;

    public OrderController(CustomerDAO CustomerDAO, OrderDAO OrderDAO, ItemDAO ItemDAO, OrderItems orderItems, Utils utils) {
        super();
        this.ItemDAO = ItemDAO;
        this.CustomerDAO = CustomerDAO;
        this.OrderDAO = OrderDAO;
        this.utils = utils;
        this.OrderItems = orderItems;
    }

    /**
     * Reads all Items to the logger
     */
    @Override
    public List<Order> readAll() {
        boolean exitRead = false;

        do {
            LOGGER.info("Which entry would you like to see?.");
            LOGGER.info("orders: print a list of orders.");
            LOGGER.info("items: print a list of items on an order.");
            LOGGER.info("total: print a total cost on an order.");
            LOGGER.info("return: returns to previous menu");
            String answer = utils.getString();
            switch(answer.toLowerCase()) {

                case "orders":
                    List<Order> orders = OrderDAO.readAll();
                    for (Order order : orders) {
                        LOGGER.info(order);
                    }
                    break;

                case "items":
                    LOGGER.info("Please enter the order id you'd like to see items on.");
                    LOGGER.info(OrderDAO.readAll());
                    long order_id = utils.getLong();
                    List<Order.OrderItems> orderItemsList = OrderItems.getItemList(order_id);
                    for (Order.OrderItems morder : orderItemsList) {
                        LOGGER.info(morder);
                    }
                    break;

                case "total":
                    double total = 0;
                    LOGGER.info("Please enter the order id you'd like to see a total of.");
                    LOGGER.info(OrderDAO.readAll());
                    long id = utils.getLong();
                    List<Order.OrderItems> ItemsList = OrderItems.getItemList(id);
                    for (Order.OrderItems item : ItemsList) {
                        Item currItem = ItemDAO.read(item.getItem_code());
                        double price = currItem.getItemPrice();
                        LOGGER.info(currItem.getItemName() + " price is: " + price);
                        total = total + price;
                    }
                    LOGGER.info("The total is: " + total);

                    break;

                case "return":
                    exitRead = true;
                    break;

                default:
                    break;
            }

        } while (!exitRead);

        List<Order> orders = OrderDAO.readAll(); // exists to satisfy return type for now.
        return orders;
    }

    /**
     * Creates a Item by taking in user input
     */
    @Override
    public Order create() {
        LOGGER.info("Please enter a customer ID" );
        Long CustomerID = utils.getLong();
        LOGGER.info("Please enter any comments");
        String comments = utils.getString();

        //fire off the create order_item DAO.
        Order Order = OrderDAO.create(new Order(CustomerID, comments));
        LOGGER.info(Order != null ? "Order created": "Issues aquired during creation.");
//        removed until I figure out how to mock getId for testing purposes.

        // Logger queries the user on which order they would like to operate on.
        // Databases grabs order based on what user requests.

        long orderID = Order.getId();
        System.out.println("Current ID is: " + orderID);

        LOGGER.info("Would you like to add an item to the cart now??");
        LOGGER.info("add: Add an item to the cart");
        LOGGER.info("return: returns to previous menu.");
        String answer = utils.getString();
        LOGGER.info(answer);

        switch (answer) {
            case "add":
                addOrder(orderID);
                return Order;
            case "return":
                return Order;
            default:
                break;
        }
        LOGGER.info("Final state of Order: "+ Order);
        return Order;
    }

    private void addOrder(long orderID) {
        LOGGER.info("What Item would you like to add?" + ItemDAO.readAll());
        long ItemID = utils.getLong();
        LOGGER.info("How many would you like to add?");
        long quantity = utils.getLong();
        Order.OrderItems orderItems = new Order.OrderItems(orderID, ItemID, quantity);
        //creates new order
        OrderItems.create(orderItems);

    }

    /**
     * Updates an existing Item by taking in user input
     */
    @Override
    public Order update() {
        boolean exitUpdate = false;

        do {
            LOGGER.info("Would you like to update an order? Or change an orders item? Or add an item to existing order.");
            LOGGER.info("order: update order");
            LOGGER.info("item:update an item on a order");
            LOGGER.info("add: add an item to an existing order");
            LOGGER.info("return: return to previous menu");

            String answer = utils.getString();

            switch (answer.toLowerCase()) {
                case "order":
                    //update order
                    LOGGER.info("Please enter the id of the Order you would like to update \n" + OrderDAO.readAll());
                    Long order_id = utils.getLong();
                    LOGGER.info("Please enter a customer ID" + CustomerDAO.readAll());
                    Long CustomerID = utils.getLong();
                    LOGGER.info("Please enter any comments");
                    String comments = utils.getString();
                    Order order = OrderDAO.update(new Order(order_id, CustomerID, comments));
                    LOGGER.info("Customer Updated");
                    return order;

                case "item":
                    //updates an item
                    LOGGER.info("Please enter the id of the Order you would like to update");
                    LOGGER.info(OrderItems.readAll());
                    Long item_order_id = utils.getLong();
                    LOGGER.info("Please enter a new Item " + ItemDAO.readAll() + "/n");
                    long item_item_id = utils.getLong();
                    LOGGER.info("Please enter a new quantity ");
                    long item_quantity = utils.getLong();
                    Order.OrderItems itemUpdate = new Order.OrderItems(item_order_id, item_item_id, item_quantity);
                    OrderItems.update(itemUpdate);

                    LOGGER.info("Customer Updated");
                    return null;

                case "add":
                    //adds an item
                    LOGGER.info("please enter the order you'd like to add something to.");
                    LOGGER.info(OrderDAO.readAll());
                    Long add_id = utils.getLong();
                    addOrder(add_id);
                    return null;

                case "return":
                    exitUpdate = true;
                    LOGGER.info("returning.");
                    break;

                default:
                    LOGGER.info("Failing.");

                    return null;
            }
        } while (!exitUpdate);

        return null;
    }

    private String getAnswer() {
        String answer = utils.getString();
        return answer;

    }

    /**
     * Deletes an existing Item by the id of the Item
     *
     * @return
     */
    @Override
    public int delete() {
        boolean exitUpdate = false;
        int success = 0;

        do {
            LOGGER.info("Would you like to delete an order? Or delete and item from an existing order?");
            LOGGER.info("order: deletes an order without items");
            LOGGER.info("item: deletes an item on a order");
            LOGGER.info("return: return to previous menu");

            String answer = utils.getString();

            switch (answer.toLowerCase()) {
                case "order":
                    LOGGER.info("Please enter the id of the Order you would like to delete");
                    Long id = utils.getLong();
                    return OrderDAO.delete(id);

                case "item":
                    LOGGER.info(OrderItems.readAll());
                    LOGGER.info("Please enter the order Id where you want to delete an item");
                    Long order_id = utils.getLong();
                    LOGGER.info("Please enter the item you want to delete from the order");
                    Long item_id = utils.getLong();
                    OrderItems.deleteComposite(order_id, item_id);
                    break;
                case "return":
                    return 1;
                default:
                    break;
            }

            LOGGER.info("Sorry about this.");

        } while (!exitUpdate);

        return success;
    }

}