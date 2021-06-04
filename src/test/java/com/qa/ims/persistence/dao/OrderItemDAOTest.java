package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.qa.ims.persistence.domain.Order_Item;
import org.junit.Before;
import org.junit.Test;

import com.qa.ims.utils.DBUtils;

public class OrderItemDAOTest {

    private final Order_ItemsDAO DAO = new Order_ItemsDAO();

    @Before
    public void setup() {
        DBUtils.connect();
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreate() {
        final Order_Item created = new Order_Item(1L, 2L, 1);
        assertEquals(created, DAO.create(created));
    }

    @Test
    public void testReadAll() {
        List<Order_Item> expected = new ArrayList<>();
        expected.add(new Order_Item(1L, 1, 1));
        assertEquals(expected, DAO.readAll());
    }

    @Test
    public void testReadLatest() {
        assertEquals(new Order_Item(1L, 1, 1), DAO.readLatest());
    }

    @Test
    public void testRead() {
        final long ID = 1L;
        assertEquals(new Order_Item(ID, 1, 1), DAO.read(ID));
    }

    @Test
    public void testUpdate() {
        final Order_Item updated = new Order_Item(1L, 1, 1);
        assertEquals(updated, DAO.update(updated));
    }

    @Test
    public void testDelete() {
        assertEquals(0, DAO.delete(1));
    }
}
