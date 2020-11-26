package com.blz.addressbook;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.blz.addressbook.AddressBookService.IOService;

public class AddressBookMainTest {

	private static AddressBookService addressBookService;

	@BeforeClass
	public static void createAddressookOject() {
		addressBookService = new AddressBookService();
		System.out.println("Welcome to the Address Book System.. ");
	}

	@AfterClass
	public static void nullObj() {
		addressBookService = null;
	}

	@Test
	public void givenAddressBook_WhenRetrieved_ShouldMatchContactsCount() throws AddressBookException {
		List<PersonDetails> personDetails = addressBookService.readAddressBookData(IOService.DB_IO);
		Assert.assertEquals(5, personDetails.size());
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
	public void givenAddresBook_WhenAdded_ShouldSyncWithDB() throws AddressBookException, SQLException {
		addressBookService.readAddressBookData(IOService.DB_IO);
		addressBookService.addNewContact("Ani", "Srivastava", "Btm", "Bangalore", "KA", 561234, 985434211,
				"ani@gmail.com");
		boolean result = addressBookService.checkUpdatedRecordSyncWithDatabase("Ani");
		Assert.assertTrue(result);
	}

	@Test
	public void given2Person_WhenAddedDataToDBUsingThread_ShouldMatchPersonsEnteries() throws AddressBookException {
		PersonDetails[] arrayOfEmps = {
				new PersonDetails("Anu", "Srivastava", "Btm", "Bangalore", "KA", 561234, 985434211, "ani@gmail.com"),
				new PersonDetails("Anvita", "Srivastava", "Btm2ndStage", "Bangalore", "KA", 561234, 985434211,
						"ani@gmail.com") };
		addressBookService.readAddressBookData(IOService.DB_IO);
		Instant start = Instant.now();
		addressBookService.addMultipleRecordsInAddressBookWithThreads(Arrays.asList(arrayOfEmps));
		Instant end = Instant.now();
		System.out.println("Duration without thread: " + Duration.between(start, end));
		Assert.assertEquals(5, addressBookService.addMultipleRecordsInAddressBookWithThreads(Arrays.asList(arrayOfEmps)));
	}
}
