package com.blz.addressbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMain {

	public static void main(String[] args) throws IOException {
		pickedUpOptions();
	}

	static Map<String, AddressBookMain> addressBookObj = new HashMap<>();
	static AddressBookMain addressObj = new AddressBookMain();
	static Scanner scanner = new Scanner(System.in);
	static String addressBookName;

	public void addAddressBook() throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println(
				"Enter choice \n0.Creating new addressbook \n1.Adding contacts in existing register \n2.Exit ");
		int entry = input.nextInt();
		if (entry != 2) {
			switch (entry) {
			case 0:
				Scanner nameInput = new Scanner(System.in);
				System.out.println(" Enter name of address book ");
				String nameOfNewBook = nameInput.nextLine();
				if (addressBookObj.containsKey(nameOfNewBook)) {
					System.out.println(" address book already exists");
					break;
				}
				addressBookObj.put(nameOfNewBook, addressObj);
				System.out.println(" address  book" + " " + nameOfNewBook + " " + "has been added");
				AddressBookMain.pickedUpOptions();
				break;
			case 1:
				Scanner existingAddressName = new Scanner(System.in);
				System.out.println(" Enter name of address book ");
				String nameOfExistingRegister = existingAddressName.nextLine();
				if (addressBookObj.containsKey(nameOfExistingRegister)) {
					addressBookObj.get(nameOfExistingRegister);
					AddressBookMain.pickedUpOptions();
				} else
					System.out.println(" address book is not found ");
			case 2:
				entry = 2;
				break;
			default:
				System.out.println(" Enter valid input ");
				break;
			}
		}
	}

	public static void pickedUpOptions() throws IOException {
		AddressBook addressBook = new AddressBook();
		Scanner sc = new Scanner(System.in);
		int flag = 1;
		while (flag == 1) {
			System.out.println("Welcome to address book program ");
			System.out.println(
					"Enter choice \n1. AddContact \n2.Edit \n3.Delete \n4. Search \n5.View person \n6.Count no.of peoples in same city"
							+ " \n7.SortByName \n8.SortByCityName \n9.AddContactToFile \n10.ReadDataFromFile \n11.addContactsToCSVFile \n12.ReadDataFromCSVFile"
							+ " \n13.AddContactToJSONFile \n14.ReadDataFromJSoNFile \n15. Exit ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				addressBook.addContact(addressBookName);
				break;
			case 2:
				if (AddressBook.lst.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.editContact();
				break;
			case 3:
				if (AddressBook.lst.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.deleteContact();
				break;
			case 4:
				if (addressBook.lst.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.searchByCity();
				break;
			case 5:
				if (addressBook.lst.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.viewByCity();
				break;
			case 6:
				if (addressBook.lst.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.countBasedOnCity();
				break;
			case 7:
				if (addressBook.lst.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.sortingByName();
				break;
			case 8:
				if (addressBook.lst.isEmpty()) {
					System.out.println(" Address book is empty ");
					break;
				}
				addressBook.sortingByCity();
				break;
			case 9:
				addressBook.addContact(addressBookName);
				System.out.println("Successfully Added to text file");
				break;
			case 10:
				addressBook.readDataFromFile();
				break;
			case 11:
				addressBook.addContact(addressBookName);
				System.out.println("Successfully Added to CSV file");
				break;
			case 12:
				addressBook.readDataFromCSVFile(addressBookName);
			case 13:
				addressBook.addContact(addressBookName);
				System.out.println("Successfully Added to JSON file");
				break;
			case 14:
				addressBook.readDataFromJSONFile(addressBookName);
			case 15:
				addressObj.addAddressBook();
				flag = 0;
				break;
			default:
				System.out.println(" Enter a valid choice");
				break;
			}
		}
		System.out.println(addressBook.lst);
	}
}
