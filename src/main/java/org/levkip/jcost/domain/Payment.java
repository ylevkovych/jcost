package org.levkip.jcost.domain;


class Payment extends Entity{
    private Resident payer;
    private Double amount;
	public Resident getPayer() {
		return payer;
	}
	public void setPayer(Resident payer) {
		this.payer = payer;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
    
    
}
