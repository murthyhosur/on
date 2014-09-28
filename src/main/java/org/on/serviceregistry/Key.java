package org.on.serviceregistry;

public class Key {

	private String context;
	private String type;
	private String dn;
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	
	public static Key prepareKey(String context,String type,String dn) {
		Key key = new Key();
		key.setContext(context);
		key.setType(type);
		key.setDn(dn);
		return key;
	}
}
