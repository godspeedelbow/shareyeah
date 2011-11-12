package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends AbstractController {

    public static void index() {
    	String IP = Http.Request.current().remoteAddress;
    	
    	List<Note> notes = Note.find("order by postedAt desc").fetch();
        render(IP, notes);
    }

}