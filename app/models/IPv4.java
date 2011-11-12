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
public class IPv4 extends AbstractModel {

	int octet1, octet2, octet3, octet4;

//	@OneToMany
//	public List<Note> notes;

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
		return IPv4.find(
				"octet1 = ? and octet2 = ? and octet3 = ? and octet4 = ?",
				Integer.parseInt(octets[0]), Integer.parseInt(octets[1]),
				Integer.parseInt(octets[2]), Integer.parseInt(octets[3]));
	}

	public String toString() {
		return octet1 + "." + octet2 + "." + octet3 + "." + octet4;
	}

	@Override
	public boolean equals(Object _other) {
		if (!(_other instanceof IPv4)) {
			return false;
		}
		IPv4 other = (IPv4)_other;
		return this.equals(other);
	}
	
	public boolean equals(IPv4 other) {
		return (this.octet1 == other.octet1 && this.octet2 == other.octet2 && this.octet3 == other.octet3 && this.octet4 == other.octet4);
	}
	
}
