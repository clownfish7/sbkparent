package com.sbk.order.service;

import com.sbk.order.pojo.OrderInfo;
import com.sbk.utils.SbkResult;

import java.util.List;

/**
 * @author You
 * @create 2018-11-13 16:09
 */
public interface OrderService {

    SbkResult newOrder(OrderInfo orderInfo);
    SbkResult updateOrder(String orderId);
    List<OrderInfo> getOrderList(OrderInfo orderInfo);
    SbkResult deleteOrder(String orderId);
    SbkResult clearOrder(long userId);

}
