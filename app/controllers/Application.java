package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	String IP = Http.Request.current().remoteAddress;
    	
    	List<Note> notes = Note.findAll();
        render(IP, notes);
    }

}