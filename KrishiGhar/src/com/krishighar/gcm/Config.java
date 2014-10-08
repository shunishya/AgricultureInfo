package com.krishighar.gcm;

public interface Config {

	// used to share GCM regId with application server

	// static final String APP_SERVER_URL =
	// "http://192.168.1.17/gcm/gcm.php?shareRegId=1";

	static final String APP_SERVER_URL = "http://10.0.2.14:8080/GCM-App-Server/GCMNotification?shareRegId=";

	// Google Project Number
	static final String GOOGLE_PROJECT_ID = "748037204828";

	static final String SHOW_MESSAGE = "com.krishighar.DISPLAY_MESSAGE";
	static final String EXTRA_MESSAGE = "message";

}