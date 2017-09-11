package Serializables;

import java.util.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CardObject")
public class CardObject {
	@XmlElement(name = "email")
	private String email;
	@XmlElement(name = "month")
	private int month;
	@XmlElement(name = "year")
	private int year;
	@XmlElement(name = "key")
	private String key;
	@XmlElement(name = "number")
	private String number;
	@XmlElement(name = "vvc")
	private String vvc;
	@XmlElement(name = "cName")
	private String cName;
	@XmlElement(name = "lastChars")
	private int lastChars;

	public CardObject() {
	}

	public CardObject(String email, int month, int year, String key, String number, String vvc, String cName,
			int lastChars) {
		this.cName = cName;
		this.email = email;
		this.month = month;
		this.year = year;
		this.key = key;
		this.number = number;
		this.vvc = vvc;
		this.lastChars = lastChars;
	}

	public String getEmail() {
		return email;
	}

	public String getVvc() {
		return vvc;
	}

	public String getKey() {
		return key;
	}

	public String getNumber() {
		return number;
	}

	public String getcName() {
		return cName;
	}

	public int getLastChats() {
		return lastChars;
	}

	public int getExpMonth() {
		return month;
	}

	public int getExpYear() {
		return year;
	}

}
