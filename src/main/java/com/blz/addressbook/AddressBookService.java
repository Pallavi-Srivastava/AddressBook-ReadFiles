package com.blz.addressbook;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {

	public enum IOService {
		DB_IO
	}

	private List<PersonDetails> addressBookList;
	private static AddressBookDBService addressBookDBService;

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public List<PersonDetails> readAddressBookData(IOService ioService) throws AddressBookException {
		if (ioService.equals(IOService.DB_IO))
			return this.addressBookList = addressBookDBService.readData();
		return this.addressBookList;
	}

	public void updateRecord(String firstname, String address) throws AddressBookException {
		int result = addressBookDBService.updateAddressBookData(firstname, address);
		if (result == 0)
			return;
		PersonDetails addressBookData = this.getAddressBookData(firstname);
		if (addressBookData != null)
			addressBookData.address = address;
	}

	public boolean checkUpdatedRecordSyncWithDatabase(String firstname) throws AddressBookException {
		try {
			List<PersonDetails> addressBookData = addressBookDBService.getAddressBookData(firstname);
			return addressBookData.get(0).equals(getAddressBookData(firstname));
		} catch (AddressBookException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DatabaseException);
		}
	}

	private PersonDetails getAddressBookData(String firstname) {
		return this.addressBookList.stream().filter(addressBookItem -> addressBookItem.firstName.equals(firstname))
				.findFirst().orElse(null);
	}

	public List<PersonDetails> readEmployeePayrollData(IOService ioService, String start, String end)
			throws AddressBookException {
		try {
			LocalDate startLocalDate = LocalDate.parse(start);
			LocalDate endLocalDate = LocalDate.parse(end);
			if (ioService.equals(IOService.DB_IO))
				return addressBookDBService.readData(startLocalDate, endLocalDate);
			return this.addressBookList;
		} catch (AddressBookException e) {
			throw new AddressBookException(e.getMessage(), AddressBookException.ExceptionType.DatabaseException);
		}
	}

	public int readEmployeePayrollData(String function, String city) throws AddressBookException {
		return addressBookDBService.readDataPayroll(function, city);
	}

	public void addNewContact(String firstName, String lastName, String address, String city, String state, int zip,
			int phoneNumber, String email) throws AddressBookException {
		addressBookList.add(
				addressBookDBService.addNewContact(firstName, lastName, address, city, state, zip, phoneNumber, email));
	}
	
	public void addPersonsWithThread(List<PersonDetails> personDetailsDataList) {
		Map<Integer, Boolean> addressAdditionStatus = new HashMap<Integer, Boolean>();
		personDetailsDataList.forEach(addressbookdata -> {
			Runnable task = () -> {
				addressAdditionStatus.put(addressbookdata.hashCode(), false);
				System.out.println("Contact Being Added:" + Thread.currentThread().getName());
				try {
					this.addNewContact(addressbookdata.getFirstName(), addressbookdata.getLastName(),
							addressbookdata.getAddress(), addressbookdata.getCity(), addressbookdata.getState(),
							addressbookdata.getZip(), addressbookdata.getPhoneNo(), addressbookdata.getEmail());
				} catch (AddressBookException e) {
					e.printStackTrace();
				}
				addressAdditionStatus.put(addressbookdata.hashCode(), true);
				System.out.println("Contact Added:" + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, addressbookdata.getFirstName());
			thread.start();
		});
		while (addressAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		System.out.println(this.addressBookList);
	}

	public int countEntries(IOService dbIo) {
		return addressBookList.size();
	}
}
