package com.blz.addressbook;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.blz.addressbook.AddressBookService.IOService;

public class AddressBookMainTest {

	private static AddressBookService addressBookService;

	@BeforeClass
	public static void createcensusAnalyser() {
		addressBookService = new AddressBookService();
		System.out.println("Welcome to the Address Book System.. ");
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchContactsCount() throws AddressBookException {
		List<PersonDetails> personDetails = addressBookService.readAddressBookData(IOService.DB_IO);
		Assert.assertEquals(3, personDetails.size());
	}

	@Test
	public void givenAddressBook_WhenUpdate_ShouldSyncWithDB() throws AddressBookException {
		List<PersonDetails> personDetails = addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.updateRecord("Jiya", "Ether");
		boolean result = addressBookService.checkUpdatedRecordSyncWithDatabase("Jiya");
		Assert.assertTrue(result);
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchAddressBookCountInGivenRange() throws AddressBookException {
		List<PersonDetails> personDetails = addressBookService.readEmployeePayrollData(IOService.DB_IO, "2020-11-01",
				"2020-11-22");
		Assert.assertEquals(2, personDetails.size());
	}

	@Test
	public void givenAddresBook_WhenRetrieved_wShouldReturnTotalNoOfCity() throws AddressBookException {
		Assert.assertEquals(1, addressBookService.readEmployeePayrollData("Count", "Varanasi"));
	}

	@Test
	public void givenAddresBook_WhenAdded_ShouldSyncWithDB() throws AddressBookException {
		addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.addNewContact("Ani", "Srivastava", "Btm", "Bangalore", "KA", 561234, 985434211,
				"ani@gmail.com");
		boolean result = addressBookService.checkUpdatedRecordSyncWithDatabase("Ani");
		Assert.assertTrue(result);
	}
}
