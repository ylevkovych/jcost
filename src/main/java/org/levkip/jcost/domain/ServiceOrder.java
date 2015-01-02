package org.levkip.jcost.domain;

import java.util.ArrayList;
import java.util.List;


class ServiceOrder extends Entity {
    private Long date;
    private Resident payee;
    private Currency currency;
    private List<Payment> payments = new ArrayList<Payment>();
    private String comment;
    
    public void addPayment(Payment payment) {
    	if (payments == null) 
    		payments = new ArrayList<Payment>();
    	
    	payments.add(payment);    	
    }
    
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public Resident getPayee() {
		return payee;
	}
	public void setPayee(Resident payee) {
		this.payee = payee;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public List<Payment> getPayments() {
		return payments;
	}
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
    
    
}
