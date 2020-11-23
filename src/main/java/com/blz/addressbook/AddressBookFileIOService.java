package com.blz.addressbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AddressBookFileIOService {
	public static String AddressBook_FILE_NAME = "adressbook.txt";

	public void writeData(List<PersonDetails> personsList) {
		StringBuffer buffer = new StringBuffer();
		personsList.forEach(emp -> {
			String empDataString = emp.toString().concat("\n");
			buffer.append(empDataString);
		});

		try {
			Files.write(Paths.get(AddressBook_FILE_NAME), buffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printData() {
		try {
			Files.lines(new File(AddressBook_FILE_NAME).toPath()).forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long countEntries() {
		long entries = 0;
		try {
			entries = Files.lines(new File(AddressBook_FILE_NAME).toPath()).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}

	public List<PersonDetails> readData() {
		List<PersonDetails> employeePayrollList = new ArrayList<>();
		try {
			Files.lines(new File(AddressBook_FILE_NAME).toPath()).map(line -> line.trim())
					.forEach(line -> System.out.println(line));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
}
