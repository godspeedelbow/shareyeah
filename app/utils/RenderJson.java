package utils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import play.Play;
import play.exceptions.UnexpectedException;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.Result;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;

public class RenderJson extends Result {

	String json;

	public RenderJson(Object o) {
		GsonBuilder gson = new GsonBuilder();
		String dateformat = Play.configuration.getProperty("date.format");
		String timeformat = Play.configuration.getProperty("time.format");
		gson.setDateFormat(dateformat
				+ (timeformat != null ? " " + timeformat : ""));
		json = gson.create().toJson(o);
	}

	public RenderJson(Object o, Type type) {
		GsonBuilder gson = new GsonBuilder();
		String dateformat = Play.configuration.getProperty("date.format");
		String timeformat = Play.configuration.getProperty("time.format");
		gson.setDateFormat(dateformat
				+ (timeformat != null ? " " + timeformat : ""));
		json = gson.create().toJson(o, type);
	}

	public RenderJson(Object o, JsonSerializer<?>... adapters) {
		GsonBuilder gson = new GsonBuilder();
		for (Object adapter : adapters) {
			Type t = getMethod(adapter.getClass(), "serialize")
					.getParameterTypes()[0];
			gson.registerTypeAdapter(t, adapter);
		}
		String dateformat = Play.configuration.getProperty("date.format");
		String timeformat = Play.configuration.getProperty("time.format");
		gson.setDateFormat(dateformat
				+ (timeformat != null ? " " + timeformat : ""));
		json = gson.create().toJson(o);
	}

	public RenderJson(String jsonString) {
		json = jsonString;
	}

	public void apply(Request request, Response response) {
		try {
			String encoding = getEncoding();
			setContentTypeIfNotSet(response, "application/json; charset="
					+ encoding);
			response.out.write(json.getBytes(encoding));
		} catch (Exception e) {
			throw new UnexpectedException(e);
		}
	}

	//
	static Method getMethod(Class<?> clazz, String name) {
		for (Method m : clazz.getDeclaredMethods()) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}
}
