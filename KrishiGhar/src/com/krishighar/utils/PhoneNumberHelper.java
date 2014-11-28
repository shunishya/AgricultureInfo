package com.krishighar.utils;

import java.util.Locale;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.krishighar.models.Contacts;

public class PhoneNumberHelper {

	private PhoneNumberUtil mPhoneUtil;

	public PhoneNumberHelper() {
		mPhoneUtil = PhoneNumberUtil.getInstance();
	}

	public boolean isValidPhoneNumber(String phoneNumberString,
			String countryCode) {
		try {
			PhoneNumber phoneNumber = mPhoneUtil.parse(phoneNumberString,
					countryCode);
			if (mPhoneUtil.isValidNumber(phoneNumber)) {
				return true;
			}
		} catch (NumberParseException e) {
			// return false;
		}
		return false;
	}

	public String getPhoneNumberIfValid(String phoneNumberString,
			String countryCode) {
		try {
			PhoneNumber phoneNumber = mPhoneUtil.parse(phoneNumberString,
					countryCode);
			if (mPhoneUtil.isValidNumber(phoneNumber)) {
				return getContactNumberCountry(phoneNumberString, countryCode)
						.getPhoneNumber();
				// return String.valueOf(phoneNumber.getNationalNumber());
			}
		} catch (NumberParseException e) {
			// return false;
		}
		return null;
	}

	public Contacts getContactNumberCountry(String phoneNumberFromDevice,
			String countryString) {
		if (countryString != null)
			countryString = countryString.toUpperCase(Locale.US);
		Contacts contact = new Contacts();
		try {
			PhoneNumber phoneNumber = mPhoneUtil.parse(phoneNumberFromDevice,
					countryString);
			if (mPhoneUtil.isValidNumber(phoneNumber)) {
				if (mPhoneUtil.getNumberType(phoneNumber).equals(
						PhoneNumberUtil.PhoneNumberType.MOBILE)) {
					contact.setPhoneNumber(mPhoneUtil
							.getNationalSignificantNumber(phoneNumber));
					return contact;
				}
			}
		} catch (NumberParseException e) {

		}
		return null;
	}

	public String getFormatedPhoneNumber(String phoneNumberString,
			String countryCode) {
		try {
			PhoneNumber phoneNumber = mPhoneUtil.parse(phoneNumberString,
					countryCode);
			String formatedNumber = mPhoneUtil.format(phoneNumber,
					PhoneNumberFormat.NATIONAL);
			return formatedNumber;
		} catch (NumberParseException e) {
			// return false;
		}
		return null;
	}

}
