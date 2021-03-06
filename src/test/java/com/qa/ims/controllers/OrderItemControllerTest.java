package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.qa.ims.controller.Order_ItemController;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.Order_ItemsDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order_Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemControllerTest {

    @Mock
    private Utils utils;

    @Mock
    private Order_ItemsDAO dao;

    @Mock
    private ItemDAO itemDAO;

    @InjectMocks
    private Order_ItemController controller;

    @Test
    public void createTest() {
        final long id = 1L, oid = 2L, quantity = 1L;
        final Order_Item created = new Order_Item(1,2,1);
        System.out.println(created);

        Mockito.when(utils.getLong()).thenReturn(id, oid, quantity);
        Mockito.when(dao.create(created)).thenReturn(created);

        assertEquals(created, controller.create());

        Mockito.verify(utils, Mockito.times(3)).getLong();
        Mockito.verify(dao, Mockito.times(1)).create(created);
    }

    @Test
    public void totalTest() {
        List<Order_Item> items = new ArrayList<>();
        Item item = new Item("mock Item", 4.29);
        Order_Item orderItem = new Order_Item(1, 2, 1);
        items.add(new Order_Item(1L, 1L, 1L));
        Mockito.when(utils.getString()).thenReturn("total");
        Mockito.when(utils.getLong()).thenReturn(1L);
        Mockito.when(dao.getItemList(1L)).thenReturn(items);
        //line 62
        Mockito.when(itemDAO.read(1L)).thenReturn(item);

        Mockito.when(dao.readAll()).thenReturn(items);

        assertEquals(items, controller.readAll());

        Mockito.verify(dao, Mockito.times(1)).readAll();
    }
    @Test
    public void readAllTest() {
        List<Order_Item> items = new ArrayList<>();
        items.add(new Order_Item(1L, 1L, 1L));
        Mockito.when(utils.getString()).thenReturn("readall");

        Mockito.when(dao.readAll()).thenReturn(items);

        assertEquals(items, controller.readAll());

        Mockito.verify(dao, Mockito.times(1)).readAll();
    }

//    @Test
//    public void testUpdate() {
//        Order_Item updated = new Order_Item(1L, 1, 2);
//
//        Mockito.when(utils.getLong()).thenReturn(updated.getOrder_id());
//        Mockito.when(utils.getLong()).thenReturn(updated.getItem_code());
//        Mockito.when(utils.getLong()).thenReturn(updated.getQuantity());
//        Mockito.when(dao.update(updated)).thenReturn(updated);
//
//        assertEquals(2, controller.update());
//
//        Mockito.verify(utils, Mockito.times(3)).getLong();
//        Mockito.verify(dao, Mockito.times(1)).update(updated);
//    }

    @Test
    public void deleteTest() {
        final long ID = 1L;
        final long item = 1L;

        Mockito.when(utils.getLong()).thenReturn(ID);
        Mockito.when(utils.getLong()).thenReturn(item);


        assertEquals(0, controller.delete());

        Mockito.verify(utils, Mockito.times(2)).getLong();
    }

}
