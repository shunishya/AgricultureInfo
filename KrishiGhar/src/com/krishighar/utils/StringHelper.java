package com.krishighar.utils;

import com.krishighar.fragments.LanguageChooseFrag;

public class StringHelper {
	public static String getAppName(int lang_id) {
		if (lang_id == LanguageChooseFrag.ENGLISH) {
			return "Krishi Ghar";
		} else {
			return "कृषि घर";
		}

	}

	public static String getLocationFragTitle(int lang_id) {
		if (lang_id == LanguageChooseFrag.ENGLISH) {
			return "Select Location";
		} else {
			return "स्थान रोज्नुहोस";
		}
	}

	public static String getCropdFragTitle(int lang_id) {
		if (lang_id == LanguageChooseFrag.ENGLISH) {
			return "Select Crops";
		} else {
			return "अन्न रोज्नुहोस";
		}
	}

	public static String getDialogMessage(int lang_id) {
		if (lang_id == LanguageChooseFrag.ENGLISH) {
			return "Internet is not enabled! Want to go to settings menu ?";
		} else {
			return "अघाडी बढ्न इन्टरनेट चाहिन्छ ! इन्टरनेट सुचारु गर्नुहुन्छ ?";
		}
	}

	public static String getPositiveValue(int lang_id) {
		if (lang_id == LanguageChooseFrag.ENGLISH) {
			return "Yes";
		} else {
			return "हो";
		}
	}

	public static String getNegativeValue(int lang_id) {
		if (lang_id == LanguageChooseFrag.ENGLISH) {
			return "No";
		} else {
			return "होइन";
		}
	}

	public static String getDialogTitle(int lang_id) {
		if (lang_id == LanguageChooseFrag.ENGLISH) {
			return "NETWORK SETTINGS";
		} else {
			return "इन्टरनेट सूचना";
		}
	}

}
