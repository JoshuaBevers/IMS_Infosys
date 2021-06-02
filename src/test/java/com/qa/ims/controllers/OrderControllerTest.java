package com.qa.ims.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Order;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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

    @Test
    public void testCreate() {

        final Order mockOrder = new Order(1L, 1L, "hi");
        System.out.println(mockOrder);

        Mockito.when(utils.getLong()).thenReturn(1L);
        Mockito.when(utils.getString()).thenReturn("hi");
        Mockito.when(dao.create(Mockito.any(Order.class))).thenReturn(mockOrder);

        assertEquals(mockOrder, controller.create());
//        Assert.assertThat(mockOrder, controller.create()).isEqual();

//        Mockito.verify(utils, Mockito.times(1)).getLong();
        Mockito.verify(utils, Mockito.times(2)).getString();
        Mockito.verify(dao, Mockito.times(1)).create(mockOrder);
    }

//    @Test
//    public void testReadAll() {
//        //this test is a bif iffy. Learned to be careful with using do whiles as they are super hard to test
//        //on looping systems with no defined return.
//        List<Order> customers = new ArrayList<>();
//        customers.add(new Order(1L, 1L, "harrison"));
//
//        //handle return
//        Mockito.when(utils.getString()).thenReturn("orders");
//        Mockito.when(dao.readAll()).thenReturn(customers);
//        Mockito.when(utils.getString()).thenReturn("items");
//        Mockito.when(utils.getString()).thenReturn("return");
//
//        assertEquals(customers, controller.readAll());
//
//        Mockito.verify(dao, Mockito.times(1)).readAll();
//    }

//    @Test
//    public void testUpdate() {
//        Order updated = new Order(1L, 1L, "perrins");
//
//        Mockito.when(this.utils.getLong()).thenReturn(1L);
//        Mockito.when(this.utils.getString()).thenReturn(updated.getFirstName(), updated.getSurname());
//        Mockito.when(this.dao.update(updated)).thenReturn(updated);
//
//        assertEquals(updated, controller.update());
//
//        Mockito.verify(this.utils, Mockito.times(1)).getLong();
//        Mockito.verify(this.utils, Mockito.times(2)).getString();
//        Mockito.verify(this.dao, Mockito.times(1)).update(updated);
//    }
//
//    @Test
//    public void testDelete() {
//        final long ID = 1L;
//
//        Mockito.when(utils.getLong()).thenReturn(ID);
//        Mockito.when(dao.delete(ID)).thenReturn(1);
//
//        assertEquals(1L, controller.delete());
//
//        Mockito.verify(utils, Mockito.times(1)).getLong();
//        Mockito.verify(dao, Mockito.times(1)).delete(ID);
//    }


}
