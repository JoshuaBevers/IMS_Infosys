package com.qa.ims.persistence.domain;

import com.qa.ims.utils.DBUtils;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class OrderItemTest {

    @Mock
    private Order_Item order = new Order_Item(1L, 1L, 1);

    @Before
    public void setup() {
        DBUtils.connect();
        DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void EqualsTest() {
        EqualsVerifier.simple().forClass(Order_Item.class).verify();
    }

    @Test
    public void hashTest() {
        EqualsVerifier.simple().forClass(Order_Item.class).verify();
    }

    @Test
    public void GetIDTest() {
        assertEquals(1L, order.getOrder_id(), .01);
    }

    @Test
    public void setOrderTest() {
        order.setOrderID(2);
        assertEquals(2L, order.getOrder_id(), .01);
    }

    @Test
    public void getItem_codeTest() {
        assertEquals(1L, order.getItem_code(), .01);
    }

    @Test
    public void getQuantityTest() {
        assertEquals(1L, order.getItem_code(), .01);
    }
    @Test

    public void setQuantityTest() {
        order.setQuantity(1);
        assertEquals(1L, order.getQuantity(), .01);
    }

}
