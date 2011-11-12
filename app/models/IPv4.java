package models;

import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Entity
public class IPv4 extends Model {

	int octet1, octet2, octet3, octet4;
	
	@OneToMany
	private
	List<Note> notes;

	public IPv4(String ipAddress) {
		String[] octets = ipAddress.split("\\.");
		if (octets.length != 4) {
			Logger.error("IP %s doesn't have 4 octets!", ipAddress);
		}
		octet1 = Integer.decode(octets[0]);
		octet2 = Integer.decode(octets[1]);
		octet3 = Integer.decode(octets[2]);
		octet4 = Integer.decode(octets[3]);
	}
	
	public static JPAQuery findByString(String ipAddress) {
		String[] octets = ipAddress.split("\\.");
		if (octets.length != 4) {
			Logger.error("IP %s doesn't have 4 octets!", ipAddress);
		}
		return IPv4.find("octet1 = ? and octet2 = ? and octet3 = ? and octet4 = ?", Integer.parseInt(octets[0]), Integer.parseInt(octets[1]), Integer.parseInt(octets[2]), Integer.parseInt(octets[3]));
	}
	
	public String toString() {
		return octet1+"."+octet2+"."+octet3+"."+octet4;
	}
	
	public static class Serializer implements JsonSerializer<IPv4> {

		@Override
		public JsonElement serialize(IPv4 arg0, Type arg1,
				JsonSerializationContext arg2) {
			return new JsonPrimitive(arg0.toString());
		}
		
	}
}
