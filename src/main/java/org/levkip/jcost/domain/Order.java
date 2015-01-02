package org.levkip.jcost.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by levy on 16.10.14.
 */
class Order extends ServiceOrder {
    private List<Good> goods = new ArrayList<Good>();

	public List<Good> getGoods() {
		return goods;
	}

	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}
	
	public void add(Good good) {
		if (goods == null)
			goods = new ArrayList<Good>();
		
		goods.add(good);
	}
        
}
