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

}
