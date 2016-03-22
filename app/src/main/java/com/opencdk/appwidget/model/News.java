package com.opencdk.appwidget.model;

import org.json.JSONException;
import org.json.JSONObject;

public class News {

	private String title;

	private String date;

	private int newsMark;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNewsMark() {
		return newsMark;
	}

	public void setNewsMark(int newsMark) {
		this.newsMark = newsMark;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("title", title);
			json.put("date", date);
			json.put("newsMark", newsMark);
		} catch (JSONException e) {
			// e.printStackTrace();
		}

		return json;

	}

	public static News toObject(String jsonString) {
		News news = null;
		try {
			JSONObject json = new JSONObject(jsonString);

			String title = json.optString("title");
			String date = json.optString("date");
			int newsMark = json.optInt("newsMark", 0);

			news = new News();
			news.setTitle(title);
			news.setDate(date);
			news.setNewsMark(newsMark);
		} catch (JSONException e) {
			// e.printStackTrace();
		}

		return news;
	}

}
