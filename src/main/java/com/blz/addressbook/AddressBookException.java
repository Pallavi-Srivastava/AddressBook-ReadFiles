package com.blz.addressbook;

public class AddressBookException extends Exception {

	enum ExceptionType {
		DatabaseException, NoSuchClass, ResourcesNotClosedException,  ConnectionFailed, CommitFailed
	}

	public ExceptionType type;

	public AddressBookException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}
