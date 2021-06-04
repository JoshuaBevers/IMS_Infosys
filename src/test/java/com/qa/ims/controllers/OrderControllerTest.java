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

        Mockito.verify(utils, Mockito.times(1)).getString();
        Mockito.verify(dao, Mockito.times(1)).create(Mockito.any(Order.class));
    }

    @Test
    public void testReadAll() {
        List<Order> order = new ArrayList<>();
        order.add(new Order(1L, 1L, "perrins"));
        Mockito.when(dao.readAll()).thenReturn(order);

        assertEquals(order, controller.readAll());

        Mockito.verify(dao, Mockito.times(1)).readAll();
    }

    @Test
    public void updateTest() {
        final Order mock = new Order(1L, 1L, "tommy");
        Mockito.when(utils.getLong()).thenReturn(mock.getId());
        Mockito.when(utils.getLong()).thenReturn(mock.getCustomerID());
        Mockito.when(utils.getString()).thenReturn(mock.getComments());
        Mockito.when(dao.update(mock)).thenReturn(mock);

        assertEquals(mock, controller.update());

        Mockito.verify(utils, Mockito.times(2)).getLong();
        Mockito.verify(utils, Mockito.times(1)).getString();
        Mockito.verify(dao, Mockito.times(1)).update(mock);
    }
//
    @Test
    public void testDelete() {
        final long ID = 1L;

        Mockito.when(utils.getLong()).thenReturn(ID);
        Mockito.when(dao.delete(ID)).thenReturn(1);

        assertEquals(1, controller.delete());

        Mockito.verify(utils, Mockito.times(1)).getLong();
        Mockito.verify(dao, Mockito.times(1)).delete(ID);
    }


}
