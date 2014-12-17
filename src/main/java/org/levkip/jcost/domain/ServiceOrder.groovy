package org.levkip.jcost.domain


class ServiceOrder extends Entity {
    long date;
    Resident payee;
    Currency currency;
    List<Payment> payments = [];
    String comment;
}
