package com.krishighar.utils;

import java.util.ArrayList;

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
			return "Select crops and animals";
		} else {
			return "बाली र पशु रोज्नुहोस";
		}
	}

	public static String getListHeaderTitle(int lang_id) {
		if (lang_id == LanguageChooseFrag.ENGLISH) {
			return "touch here to select ";
		} else {
			return " रोज्न यहाँ छुनुहोस्";
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

	public static ArrayList<String> getTabTitles(int lang_id) {
		ArrayList<String> titles = new ArrayList<>();
		if (lang_id == LanguageChooseFrag.ENGLISH) {
			titles.add("Providers Info");
			titles.add("Subscribe Items");
		} else {
			titles.add("दाताहरूको सूचना");
			titles.add("कृषि वस्तुहरू");
		}
		return titles;
	}

}
