package controllers;

import java.lang.reflect.Type;

import models.Note;

import play.mvc.Controller;
import utils.RenderJson;

import com.google.gson.Gson;
import com.google.gson.JsonSerializer;

public abstract class AbstractController extends Controller {

	protected static void renderJSON(String jsonString) {
		throw new RenderJson(jsonString);
	}

	protected static void renderJSON(Object o) {
		throw new RenderJson(o);
	}

	protected static void renderJSON(Object o, Type type) {
		throw new RenderJson(o, type);
	}

	protected static void renderJSON(Object o, JsonSerializer<?>... adapters) {
		throw new RenderJson(o, adapters);
	}
}
