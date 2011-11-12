package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class Note extends Model {

	public String IP;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date postedAt;

	@Lob
	public String content;

	public Note(String IP, String content) {
		this.IP = IP;
		this.content = content;

		this.postedAt = new Date();
	}
}
