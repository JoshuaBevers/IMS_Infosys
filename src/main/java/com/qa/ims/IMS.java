package com.qa.ims;

import com.qa.ims.controller.*;
import com.qa.ims.persistence.dao.Order_ItemsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Order_Item;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Domain;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.utils.DBUtils;
import com.qa.ims.utils.Utils;

public class IMS {

    public static final Logger LOGGER = LogManager.getLogger();

    private final CustomerController customers;
    private final ItemController items;
    private final OrderController order;
    private final Order_ItemController orderItems;
    private final Utils utils;

    public IMS() {
        this.utils = new Utils();

        final CustomerDAO custDAO = new CustomerDAO();
        this.customers = new CustomerController(custDAO, utils);

        final ItemDAO itemDAO = new ItemDAO();
        this.items = new ItemController(itemDAO, utils);

        final Order_ItemsDAO orderItemDAO = new Order_ItemsDAO();
        this.orderItems = new Order_ItemController(orderItemDAO, itemDAO, utils);

        final OrderDAO orderDAO = new OrderDAO();
        this.order = new OrderController(orderDAO, utils);
    }

    public void imsSystem() {
        LOGGER.info("Welcome to the Inventory Management System!");
        DBUtils.connect();

        Domain domain = null;
        do {
            LOGGER.info("Which entity would you like to use?");
            Domain.printDomains();

            domain = Domain.getDomain(utils);

            domainAction(domain);

        } while (domain != Domain.STOP);
    }

    private void domainAction(Domain domain) {
        boolean changeDomain = false;

        do {

            CrudController<?> active = null;
            switch (domain) {
                case CUSTOMER:
                    active = this.customers;
                    break;
                case ITEM:
                    active = this.items;
                    break;
                case ORDER:
                    active = this.order;
                    break;
                case PURCHASES:
                    active = this.orderItems;
                    break;
                case STOP:
                    return;
                default:
                    break;
            }


            LOGGER.info(() -> "What would you like to do with " + domain.name().toLowerCase() + ":");

            Action.printActions();
            Action action = Action.getAction(utils);

            if (action == Action.RETURN) {
                changeDomain = true;
            } else {
                doAction(active, action);
            }

        } while (!changeDomain);
    }


    public void doAction(CrudController<?> crudController, Action action) {
        switch (action) {
            case CREATE:
                crudController.create(); // we need to modify code here.
                break;
            case READ:
                crudController.readAll(); //
                break;
            case UPDATE:
                crudController.update(); //
                break;
            case DELETE:
                crudController.delete(); //
                break;
            case RETURN:
                break;
            default:
                break;
        }
    }

}
