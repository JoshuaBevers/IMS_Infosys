package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.qa.ims.persistence.domain.Order;
import org.junit.Before;
import org.junit.Test;

import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {

    private final OrderDAO DAO = new OrderDAO();
    private final OrderDAO.OrderItems IDAO = new OrderDAO.OrderItems();

    @Before
    public void setup() {
        DBUtils.connect();
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void createTest(){
        final Order created = new Order( 2L,1L, "perrins");
        assertEquals(created, DAO.create(created));
    }

    @Test
    public void readAllTest() {
        List<Order> expected = new ArrayList<>();
        expected.add(new Order(1L, 1L, "perrins"));
        assertEquals(expected, DAO.readAll());
    }

    @Test
    public void readLatestTest() {
        assertEquals(new Order(1L, 1L, "perrins"), DAO.readLatest());
    }

    @Test
    public void readTest() {
        assertEquals(new Order(1L, 1L, "perrins"), DAO.read(1L));
    }

    @Test
    public void deleteTest() {
        assertEquals(1, DAO.delete(1L));
    }

    @Test
    public void updateTest() {
        final Order updated = new Order(1L, 1L, "goop");
        assertEquals(updated, DAO.update(updated));
    }

    @Test
    public void createItemTest(){
        final Order.OrderItems orderItems = new Order.OrderItems( 1L, 1L, 1L, 2);
        assertEquals(0, IDAO.create(orderItems).getOrderItemsID());
    }

//    @Test
//    public void testReadAll() {
//        final Order.OrderItems orderItems = new Order.OrderItems(1L, 1L, 2);
//        List<Order.OrderItems> expected = new ArrayList<>();
//        expected.add(orderItems);
//        assertEquals(expected, IDAO.readAll());
//    }
//
//    @Test
//    public void testReadLatest() {
//        assertEquals(new Order.OrderItems(1L, 1L, 2), IDAO.readLatest());
//    }



}
