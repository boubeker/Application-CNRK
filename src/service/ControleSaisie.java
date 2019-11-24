package service;

import java.util.regex.Matcher;

public class ControleSaisie {
	private Matcher matcher;

	public boolean isString(String text) {
		if (text.matches("^[a-zA-Z]+$")) {
			return true;
		}
		return false;
	}

	public boolean isNull(String text) {
		if (text == "") {
			return true; // null
		}
		return false;
	}

	public boolean adresse(String text) {
		if (text.matches("^[A-Z a-z 0-9]+$")) {
			return true;
		}
		return false;
	}

	public boolean isTel(String text) {
		if (text.matches("^[0-9]+$") && text.length() == 8) {
			return true;
		} else {
			return false;
		}
	}
}