package org.levkip.jcost.domain;


public class Resident extends Entity {
    private String name;
    private String comment;
    
    public Resident() {}
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
    
    
}
