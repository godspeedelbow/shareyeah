package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringEscapeUtils;

import play.mvc.Http;

import com.google.gson.JsonObject;

@Entity
public class Note extends AbstractModel {

	@ManyToOne
	public IPv4 ipAddress;

	@Temporal(TemporalType.TIMESTAMP)
	public Date postedAt;

	@Lob
	public String content;

	public Note(String content) {
		this.ipAddress = IPv4.findByString(Http.Request.current().remoteAddress).first();
		if (this.ipAddress == null) {
			this.ipAddress = new IPv4(Http.Request.current().remoteAddress);
		}
		this.content = content;
		this.postedAt = new Date();
	}
	
	@Override
	public void _save() {
		this.content = StringEscapeUtils.escapeHtml(this.content);
		
		if (this.ipAddress == null) {
			this.ipAddress = IPv4.findByString(Http.Request.current().remoteAddress).first();
			if (this.ipAddress == null) {
				this.ipAddress = new IPv4(Http.Request.current().remoteAddress);
				this.ipAddress.save();
			}
		} else {
			IPv4 ipAddress = IPv4.find("byId", this.ipAddress.id).first();
			if (ipAddress == null) {
				this.ipAddress.save();
			}
		}
		if (postedAt == null) {
			this.postedAt = new Date();
		}
		super._save();
	}
	
	public static Note fromJson(JsonObject object) {
		return (Note) AbstractModel.fromJson(object, Note.class);
	}
}
