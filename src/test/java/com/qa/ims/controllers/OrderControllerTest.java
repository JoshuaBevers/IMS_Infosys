package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)

public class OrderControllerTest {

    @Mock
    private Utils utils;

    @Mock
    private OrderDAO dao;

    @Mock
    private Order.OrderItems orderItems;

    @InjectMocks
    private OrderController controller;

    @Before
    public void setup() {
        DBUtils.connect();
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreate() {

        final Order mockOrder = new Order(1L, 1L, "hi");
        System.out.println(mockOrder);

        Mockito.when(utils.getLong()).thenReturn(1L);
        Mockito.when(utils.getString()).thenReturn("hi");
        Mockito.when(dao.create(Mockito.any(Order.class))).thenReturn(mockOrder);

        assertEquals(mockOrder, controller.create());

        Mockito.verify(utils, Mockito.times(2)).getString();
        Mockito.verify(dao, Mockito.times(1)).create(Mockito.any(Order.class));
    }

    @Test
    public void testReadAll() {
        //this test is a bif iffy. Learned to be careful with using do whiles as they are super hard to test
        //on looping systems with no defined return.
        List<Order> customers = new ArrayList<>();
        customers.add(new Order(1L, 1L, "harrison"));

        //handle return
        Mockito.when(utils.getString()).thenReturn("orders");
        Mockito.when(dao.readAll()).thenReturn(customers);
        Mockito.when(utils.getString()).thenReturn("items");
        Mockito.when(utils.getString()).thenReturn("return");

        assertEquals(customers, controller.readAll());

        Mockito.verify(dao, Mockito.times(1)).readAll();
    }

//    @Test
//    public void testUpdate() {
//        Mockito.when(utils.getString()).thenReturn("item");
//        Mockito.when(utils.getLong()).thenReturn(1L);
//        final Order.OrderItems mockOrder = new Order.OrderItems(1L, 1L, 2);
//        controller.update();
//        Mockito.when(utils.getString()).thenReturn("items");
//    }
////
//    @Test
//    public void testDelete() {
//        final long ID = 1L;
//
//        Mockito.when(utils.getLong()).thenReturn(ID);
//        Mockito.when(dao.delete(ID)).thenReturn(1);
//
//        assertEquals(0, controller.delete());
//
//        Mockito.verify(utils, Mockito.times(1)).getLong();
//        Mockito.verify(dao, Mockito.times(1)).delete(ID);
//    }


}
