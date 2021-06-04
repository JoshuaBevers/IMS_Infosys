package com.qa.ims.persistence.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class ItemTest {

    @Mock
    private Item mockItem = new Item(1L, "Google", 1.99);

    @Test
    public void getIDTest() {
        long num = 1L;
        assertEquals(1L, (long) mockItem.getId());
    }

    @Test
    public void setIDTest() {
        mockItem.setId(2L);
        assertEquals(2L, (long) mockItem.getId());
    }

    @Test
    public void getItemNameTest() {
        assertEquals("Google", mockItem.getItemName());
    }

    @Test
    public void setItemNameTest() {
        mockItem.setItemName("Alphabet");
        assertEquals("Alphabet", mockItem.getItemName());
    }

    @Test
    public void getGetItemPrice() {
        assertEquals((double)1.99, (double) mockItem.getItemPrice() ,.01);

    }

    @Test
    public void setGetItemPrice() {
        mockItem.setItemPrice(2.99);
        assertEquals((double)2.99, (double) mockItem.getItemPrice(), .01);
    }

    @Test
    public void testEquals() {
        EqualsVerifier.simple().forClass(Item.class).verify();
    }

}
