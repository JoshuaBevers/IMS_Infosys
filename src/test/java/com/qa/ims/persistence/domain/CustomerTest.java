package com.qa.ims.persistence.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;


public class CustomerTest {

	@Mock
	private Customer mockCustomer = new Customer(1L, "Larry", "Grenkins");

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Item.class).verify();
	}

	@Test
	public void getIDTest(){
		assertEquals(1L, mockCustomer.getId(), .01);
	}

	@Test
	public void setIDTest() {
		mockCustomer.setId(2L);
		assertEquals(2, mockCustomer.getId(), .01);
	}

	@Test
	public void getFirstNameTest(){
		assertEquals("Larry", mockCustomer.getFirstName());
	}

	@Test
	public void setFirstNameTest(){
		mockCustomer.setFirstName("Bob");
		assertEquals("Bob", mockCustomer.getFirstName());
	}

	@Test
	public void getLastNameTest(){
		assertEquals("Grenkins", mockCustomer.getSurname());
	}

	@Test
	public void setLastNameTest() {
		mockCustomer.setSurname("Smith");
		assertEquals("Smith", mockCustomer.getSurname());
	}

	@Test
	public void hashTest() {
		EqualsVerifier.simple().forClass(Customer.class).verify();
	}


}
