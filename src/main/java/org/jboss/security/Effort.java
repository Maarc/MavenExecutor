package org.jboss.security;

public enum Effort {
	
	Low(1),
	Medium(3),
	High(10),
	None(0),
	Unknown(0);
	
	final int days;
	
	Effort(int days) {
		this.days = days;
	}
	
}
