package org.levkip.jcost.domain;


public class Currency extends Entity{
    private String name;
    private String shortName;
    
    public Currency() {}
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}    
}
