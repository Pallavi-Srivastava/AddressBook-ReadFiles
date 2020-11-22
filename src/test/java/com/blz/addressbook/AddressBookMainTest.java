package com.blz.addressbook;

import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddressBookMainTest {

	private static AddressBook addressBook;

	@BeforeClass
	public static void createcensusAnalyser() {
		addressBook = new AddressBook();
		System.out.println("Welcome to the Address Book System.. ");
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchContactsCount() throws AddressBookException, SQLException {
		List<PersonDetails> personDetails = addressBook.readData();
		Assert.assertEquals(2, personDetails.size());
	}
}
