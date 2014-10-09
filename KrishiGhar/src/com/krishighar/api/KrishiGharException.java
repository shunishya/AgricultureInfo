package com.krishighar.api;


public class KrishiGharException extends Exception {
	private static final long serialVersionUID = 1L;
	private String exeption;

	KrishiGharException(String s) {
		exeption = s;
	}

	public KrishiGharException(String prefix, int errorCode) {
		exeption = prefix;
		switch (errorCode) {
		case 406:
			exeption += " already exists.";
			break;
		}
	}

	public String toString() {
		return ("KrishiGhar: " + exeption);
	}

}