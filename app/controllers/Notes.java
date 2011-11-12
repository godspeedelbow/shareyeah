package controllers;

import java.util.List;

import models.Note;

import org.hibernate.Session;

import play.db.jpa.JPA;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;

public class Notes extends Controller {

	public static void list() {
		List<Note> notes = Note.findAll();
		renderJSON(notes);
	}
	
	public static void create(String content) {
    	String IP = Http.Request.current().remoteAddress;
		Note note = new Note(IP, content).save();
		renderJSON(note);
	}
	
	public static void read(long id) {
		Note note = Note.find("byId", id).first();
		renderJSON(note);
	}
	
	public static void update(long id, String content) {
		Note note = Note.find("byId", id).first();
		note.content = content;
		note.save();
		renderJSON(note);
	}
	
	public static void delete(long id) {
		Note note = Note.find("byId", id).first();
		note.delete();
		renderJSON(note);
	}
	
	public static void deleteAll() {
		int number = Note.deleteAll();
		renderJSON(number);
	}
}
