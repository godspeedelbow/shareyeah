package models;

import play.Play;
import play.db.jpa.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public abstract class AbstractModel extends Model {

	protected static AbstractModel fromJson(JsonObject object, Class clazz) {
		GsonBuilder gson = new GsonBuilder();
		String dateformat = Play.configuration.getProperty("date.format");
		String timeformat = Play.configuration.getProperty("time.format");
		gson.setDateFormat(dateformat
				+ (timeformat != null ? " " + timeformat : ""));
		return gson.create().fromJson(object, clazz);
	}
}
