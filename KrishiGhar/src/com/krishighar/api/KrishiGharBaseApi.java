package com.krishighar.api;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class KrishiGharBaseApi {
	HttpClient client = new DefaultHttpClient();
	public static String BASE_URL = "http://krishighar-friendsandfamily.rhcloud.com/agriApi/ws/";

	public static String GET_LOCATION_URL = BASE_URL + "location/pull";
	public static String GET_CROPS_URL = BASE_URL
			+ "locationCrop/pull";
	public static String SUBSCRIBE_URL = BASE_URL + "subscriber/s";
	public static String GET_CROP_INFO_URL = BASE_URL + "info/crop";
	public static String SETTING_URL = BASE_URL + "subscriber/s";
	public static String SEND_GCM_REGISTRATION_ID = BASE_URL
			+ "GCM/registration";

	public InputStream getData(String url) throws KrishiGharException {
		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		InputStream instream;

		// Execute the request
		HttpResponse response;
		try {
			response = client.execute(httpget);

			int httpStatusCode = response.getStatusLine().getStatusCode();
			switch (httpStatusCode) {
			case 200:
				// Get hold of the response entity
				HttpEntity entity = response.getEntity();
				// If the response does not enclose an entity, there is no need
				// to worry about connection release
				instream = entity.getContent();

				return instream;
			case 400:
				throw new KrishiGharException("Bad Request 400");
			case 401:
				throw new KrishiGharException("Authentication Failed 401.");
			case 500:
				throw new KrishiGharException("Internal Server Error 500");
			default:

				throw new KrishiGharException("No results :" + httpStatusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new KrishiGharException(
					"Failed to get response from server :" + e.getMessage());
		}

	}

	public InputStream postData(String data, String url)
			throws KrishiGharException {
		// Prepare a request object
		HttpPost httpPost = new HttpPost(url);
		InputStream instream;
		httpPost.setHeader("Content-Type", "application/json");
		try {
			httpPost.setEntity(new StringEntity(data));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// Execute the request
		HttpResponse response;
		try {
			response = client.execute(httpPost);

			int httpStatusCode = response.getStatusLine().getStatusCode();
			switch (httpStatusCode) {
			case 200:
				// Get hold of the response entity
				HttpEntity entity = response.getEntity();
				// If the response does not enclose an entity, there is no need
				// to worry about connection release
				instream = entity.getContent();
				return instream;
			case 400:
				throw new KrishiGharException("Bad Request 400");
			case 401:
				throw new KrishiGharException("Authentication Failed 401.");
			case 500:
				throw new KrishiGharException("Internal Server Error 500");
			default:
				throw new KrishiGharException("No results");
			}
		} catch (Exception e) {
			throw new KrishiGharException(
					"Failed to get response from server :" + e.getMessage());
		}

	}
}
