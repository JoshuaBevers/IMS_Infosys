package com.qa.ims.persistence.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Mock
    private Order order = new Order(1L, 1L, "Customer Comments");

    @Mock
    private Order.OrderItems order_item = new Order.OrderItems(1, 1, 2);

    @Test
    public void testEquals() {
        EqualsVerifier.simple().forClass(Order.class).verify();
    }

    @Test
    public void testGetID() {
        assertEquals(1L, order.getId(), .01);
    }

    @Test
    public void setOrder() {
        order.setId(2L);
        assertEquals(2L, order.getId(), .01);
    }

    @Test
    public void testGetCustomerId() {
        assertEquals(1L, order.getCustomerID(), .01);
    }

    @Test
    public void setCustomerID() {
        order.setCustomerID(2L);
        assertEquals(2L, order.getCustomerID(), .01);
    }

    @Test
    public void testGetCustomerComments() {
        assertEquals("Customer Comments", order.getComments());
    }

    @Test
    public void setCustomerComments() {
        order.setComments("hi");
        assertEquals("hi", order.getComments());
    }

    @Test
    public void  getOrder_itemCodeTest() {
        assertEquals(1L, order_item.getItem_code());
    }

    @Test
    public void getOrder_itemOrderIDTest() {
        assertEquals(1L, order_item.getOrder_id());
    }

    @Test
    public void setOrder_itemCodeTest() {
        order_item.setItem_code(2L);
        assertEquals(2L, order_item.getItem_code());
    }

    @Test
    public void getOrder_ItemQuantityTest() {
        assertEquals(2L, order_item.getQuantity());
    }

    @Test
    public void setOrder_ItemQuantityTest() {
        order_item.setQuantity(1);
        assertEquals(1, order_item.getQuantity());
    }

}
