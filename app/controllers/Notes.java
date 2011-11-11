package controllers;

import java.util.List;

import models.Note;
import play.mvc.Controller;
import play.mvc.Http;

public class Notes extends Controller {

	public static void list() {
		List<Note> notes = Note.findAll();
		render(notes);
	}
	
	public static void create(String content) {
    	String IP = Http.Request.current().remoteAddress;
		Note note = new Note(IP, content).save();
		render(note);
	}
}
