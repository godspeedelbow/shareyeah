package controllers;

import java.util.ArrayList;
import java.util.List;

import models.IPv4;
import models.Note;
import play.mvc.Controller;
import play.mvc.Http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Notes extends AbstractController {

	public static void list() {
		String _ipAddress = Http.Request.current().remoteAddress;
		IPv4 ipAddress = IPv4.findByString(_ipAddress).first();
		if (ipAddress == null) {
			renderJSON(new ArrayList<Note>());
		}
		List<Note> notes = Note.find("ipAddress = ? order by postedAt desc",
				ipAddress).fetch();
		renderJSON(notes);
	}

	public static void create(JsonObject body) {
		Note note = Note.fromJson(body);
		note.save();
		renderJSON(note);
	}

	public static void read(long id) {
		String _ipAddress = Http.Request.current().remoteAddress;
		IPv4 ipAddress = IPv4.findByString(_ipAddress).first();
		if (ipAddress == null) {
			renderJSON(new ArrayList<Note>());
		}
		Note note = Note.find("byIdAndIpAddress", id, ipAddress).first();
		renderJSON(note);
	}

	public static void update(JsonObject body) {
		Note note = Note.fromJson(body);
		Note noteToUpdate = Note.find("byIdAndIpAddress", note.id, note.ipAddress).first();
		// check if note exists and 
		if (noteToUpdate != null) {
			noteToUpdate.content = note.content;
			noteToUpdate.save();
			renderJSON(noteToUpdate);
		} else {
			renderJSON("");
		}
	}

	public static void delete(long id) {
		String _ipAddress = Http.Request.current().remoteAddress;
		IPv4 ipAddress = IPv4.findByString(_ipAddress).first();
		if (ipAddress == null) {
			renderJSON(new ArrayList<Note>());
		}
		Note note = Note.find("byIdAndIpAddress", id, ipAddress).first();
		if (note != null) {
			note.delete();
			renderJSON(note);
		} else {
			renderJSON(null);
		}
	}

	public static void deleteAll() {
		int number = Note.deleteAll();
		renderJSON(number);
	}
}
