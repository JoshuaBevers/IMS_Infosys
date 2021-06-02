package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;

import com.qa.ims.utils.Utils;

/**
 * Takes in Item details for CRUD functionality
 *
 */
public class ItemController implements CrudController<Item> {

    public static final Logger LOGGER = LogManager.getLogger();

    private ItemDAO ItemDAO;
    private Utils utils;

    public ItemController(ItemDAO ItemDAO, Utils utils) {
        super();
        this.ItemDAO = ItemDAO;
        this.utils = utils;
    }

    /**
     * Reads all Items to the logger
     */
    @Override
    public List<Item> readAll() {
        List<Item> Items = ItemDAO.readAll();
        for (Item Item : Items) {
            LOGGER.info(Item);
        }
        return Items;
    }

    /**
     * Creates a Item by taking in user input
     */
    @Override
    public Item create() {
        LOGGER.info("Please enter a item name");
        String itemName = utils.getString();
        LOGGER.info("Please enter a price");
        double itemPrice = utils.getDouble();
        Item Item = ItemDAO.create(new Item(itemName, itemPrice));
        LOGGER.info("Item created");
        return Item;
    }

    /**
     * Updates an existing Item by taking in user input
     */
    @Override
    public Item update() {
        LOGGER.info("Please enter the id of the Item you would like to update");
        long id = utils.getLong();
        LOGGER.info("Please enter a item name");
        String itemName = utils.getString();
        LOGGER.info("Please enter the item's MSRP");
        double itemPrice = utils.getDouble();
        Item Item = ItemDAO.update(new Item(id, itemName, itemPrice));
        LOGGER.info("Customer Updated");
        return Item;
    }

    /**
     * Deletes an existing Item by the id of the Item
     *
     * @return
     */
    @Override
    public int delete() {
        LOGGER.info("Please enter the id of the Item you would like to delete");
        Long id = utils.getLong();
        return ItemDAO.delete(id);
    }

}