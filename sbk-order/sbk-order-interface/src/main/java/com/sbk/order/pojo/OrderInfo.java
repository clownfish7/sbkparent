package com.sbk.order.pojo;

import com.sbk.pojo.TbOrder;
import com.sbk.pojo.TbOrderItem;
import com.sbk.pojo.TbOrderShipping;

import java.util.List;

/**
 * @author You
 * @create 2018-11-13 16:02
 */
public class OrderInfo extends TbOrder {

    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

}
