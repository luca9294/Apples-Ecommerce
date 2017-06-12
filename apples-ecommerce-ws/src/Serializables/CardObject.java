package Serializables;

import java.util.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CardObject")
public class CardObject {
	@XmlElement(name = "customer_id")
	private int customer_id;
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

	public CardObject(int customer_id, int month, int year, String key, String number, String vvc, String cName,
			int lastChars) {
		this.cName = cName;
		this.customer_id = customer_id;
		this.month = month;
		this.year = year;
		this.key = key;
		this.number = number;
		this.vvc = vvc;
		this.lastChars = lastChars;
	}

	public int getCustomerId() {
		return customer_id;
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
