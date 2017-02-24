package org.usfirst.frc.team2415.robot;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class StreamerPacket implements Serializable{
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> attributes = new HashMap<String, Object>();
	private String type = "";
	private Date created;
	public StreamerPacket(String type) {
		this.setType(type);
		created = new Date();
	}

	public StreamerPacket() {
		created = new Date();
	}
	
	public void addAttribute(String name, Object value){
		attributes.put(name, value);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Object getAttribute(String name){
		return attributes.get(name);
	}
	public Date getDateCreated(){
		return created;
	}
	public void setDateCurrent(){
		created = new Date();
	}
	@Override
	public String toString(){
		String string = "type: "+type+"; ";
		Set<String> keys = attributes.keySet();
		for(String key:keys){
			string+=key+": "+ attributes.get(key)+"; ";
		}
		string += "Date: " + created.toString()+";";
		return string;
	}
	
}
