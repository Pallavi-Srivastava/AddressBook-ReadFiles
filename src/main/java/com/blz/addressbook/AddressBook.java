package com.blz.addressbook;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class AddressBook {

	static Scanner sc = new Scanner(System.in);
	static List<PersonDetails> lst = new ArrayList<PersonDetails>();
	PersonDetails newEntry;
	boolean isExist;

	public void addContact(String addressBookName) {
		isExist = false;
		System.out.println("Enter your firstName : ");
		String firstName = sc.nextLine();
		System.out.println("Enter your lastName : ");
		String lastName = sc.nextLine();
		System.out.println("Enter your address : ");
		String address = sc.nextLine();
		System.out.println("Enter your city : ");
		String city = sc.nextLine();
		System.out.println("Enter your state : ");
		String state = sc.nextLine();
		System.out.println("Enter your zipCode : ");
		String zip = sc.nextLine();
		System.out.println("Enter your phoneNo : ");
		String phoneNo = sc.nextLine();
		System.out.println("Enter your emailId : ");
		String email = sc.nextLine();
		if (lst.size() > 0) {
			for (PersonDetails details : lst) {
				newEntry = details;
				if (firstName.equals(newEntry.firstName) && lastName.equals(newEntry.lastName)) {
					System.out.println("Contact " + newEntry.firstName + " " + newEntry.lastName + " already exists");
					isExist = true;
					break;
				}
			}
		}
		if (!isExist) {
			newEntry = new PersonDetails(firstName, lastName, address, city, state, zip, phoneNo, email);
			lst.add(newEntry);
			addDataToFile(firstName, lastName, address, city, state, phoneNo, zip, email, addressBookName);
			try {
				addDataToCSVFile(addressBookName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void editContact() {
		Scanner nameInput = new Scanner(System.in);
		System.out.println(" Enter the first name ");
		String fName = nameInput.nextLine();
		for (int index = 0; index < lst.size(); index++) {
			if (lst.get(index).getFirstName().equals(fName)) {
				System.out.println(lst.get(index));
				@SuppressWarnings("resource")
				Scanner editInput = new Scanner(System.in);
				System.out
						.println(" Enter a choice 	1.first name 2.last name 3. city 4.state 5.zip 6.phone 7.email ");
				int selection = nameInput.nextInt();
				switch (selection) {
				case 1:
					System.out.println(" Enter first name ");
					String first_Name = editInput.nextLine();
					lst.get(index).setFirstName(first_Name);
					System.out.println(lst.get(index).getFirstName());
					break;
				case 2:
					System.out.println(" Enter last name ");
					String second_Name = editInput.nextLine();
					lst.get(index).setLastName(second_Name);
					break;
				case 3:
					System.out.println(" Enter city name ");
					String input_City = editInput.nextLine();
					lst.get(index).setCity(input_City);
					break;
				case 4:
					System.out.println(" Enter State ");
					String input_State = editInput.nextLine();
					lst.get(index).setState(input_State);
					break;
				case 5:
					System.out.println(" Enter pincode ");
					String input_Zip = editInput.nextLine();
					lst.get(index).setZip(input_Zip);
					break;
				case 6:
					System.out.println(" Enter Mobile number ");
					String input_Phone = editInput.nextLine();
					lst.get(index).setPhoneNo(input_Phone);
					break;
				case 7:
					System.out.println(" Enter Email id ");
					String input_Email = editInput.nextLine();
					lst.get(index).setEmail(input_Email);
					break;
				default:
					System.out.println(" Enter valid input ");
					break;
				}
			}
		}
		System.out.println(lst);
	}

	public void deleteContact() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter first name : ");
		String firstName = sc.nextLine();
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).getFirstName().equalsIgnoreCase(firstName)) {
				lst.remove(i);
			} else {
				System.out.println("No data found");
			}
		}
		sc.close();
	}

	public void searchByCity() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter city name : ");
		String city = sc.nextLine();
		lst.stream().filter(n -> n.getCity().equals(city))
				.forEach(i -> System.out.println("Data Found:" + i.getFirstName()));
	}

	public void viewByCity() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter city name : ");
		String city = sc.nextLine();
		lst.stream().filter(n -> n.getCity().equals(city)).forEach(i -> System.out.println(i));
	}

	public void countBasedOnCity() {
		int count = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter city name : ");
		String city = sc.nextLine();
		count = (int) lst.stream().filter(n -> n.getCity().equals(city)).count();// count Stream;
		System.out.println(count);
	}

	public void sortingByName() {
		lst = lst.stream().sorted(Comparator.comparing(PersonDetails::getFirstName)).collect(Collectors.toList());
		lst.forEach(i -> System.out.println(i));
	}

	public void sortingByCity() {
		lst = lst.stream().sorted(Comparator.comparing(PersonDetails::getCity)).collect(Collectors.toList());
		lst.forEach(i -> System.out.println(i));
	}

	public void addDataToFile(String firstName, String lastName, String address, String city, String state,
			String phoneNumber, String zip, String email, String addressBookName) {
		System.out.println("Enter name for txt written file : ");
		String fileName = sc.nextLine();
		File file = new File("E:\\Fellowship\\STS\\File\\" + fileName + ".txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Contact:" + "\n1.First name: " + firstName + "\n2.Last name: " + lastName + "\n3.Address: "
					+ address + "\n4.City: " + city + "\n5.State: " + state + "\n6.Phone number: " + phoneNumber
					+ "\n7.Zip: " + zip + "\n8.email: " + email + "\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readDataFromFile() {
		System.out.println("Enter address book name : ");
		String fileName = sc.nextLine();
		Path filePath = Paths.get("E:\\Fellowship\\STS\\File\\" + fileName + ".txt");
		try {
			Files.lines(filePath).map(line -> line.trim()).forEach(line -> System.out.println(line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDataToCSVFile(String addressBookName) throws IOException {
		System.out.println("Enter name for csv written file : ");
		String fileName = sc.nextLine();
		Path filePath = Paths.get("E:\\Fellowship\\STS\\File\\" + fileName + ".csv");

		if (Files.notExists(filePath))
			Files.createFile(filePath);
		File file = new File(String.valueOf(filePath));

		try {
			FileWriter outputfile = new FileWriter(file, true);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<>();
			for (PersonDetails detail : lst) {
				data.add(new String[] { "Contact:" + "\n1.First name: " + detail.firstName + "\n2.Last name: "
						+ detail.lastName + "\n3.Address: " + detail.address + "\n4.City: " + detail.city
						+ "\n5.State: " + detail.state + "\n6.Phone number: " + detail.phoneNo + "\n7.Zip: "
						+ detail.zip + "\n8.email: " + detail.email + "\n" });
			}
			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readDataFromCSVFile() {
		System.out.println("Enter address book name : ");
		String fileName = sc.nextLine();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("E:\\Fellowship\\STS\\File\\" + fileName + ".csv"));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				for (String token : nextLine) {
					System.out.println(token);
				}
				System.out.print("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
