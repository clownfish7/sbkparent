package com.sbk.order.service.impl;

import com.sbk.mapper.TbOrderItemMapper;
import com.sbk.mapper.TbOrderMapper;
import com.sbk.mapper.TbOrderShippingMapper;
import com.sbk.order.pojo.OrderInfo;
import com.sbk.order.service.OrderService;
import com.sbk.pojo.*;
import com.sbk.utils.IDUtils;
import com.sbk.utils.SbkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author You
 * @create 2018-11-13 16:10
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;


    @Override
    public SbkResult newOrder(OrderInfo orderInfo) {

        long id = IDUtils.genItemId();
        orderInfo.setOrderId(String.valueOf(id));
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderMapper.insert(orderInfo);
        //向订单明细表插入数据。
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem : orderItems) {
            String odId = IDUtils.genItemId()+"";
            tbOrderItem.setId(odId);
            tbOrderItem.setOrderId(id+"");
            orderItemMapper.insert(tbOrderItem);
        }
        //向订单物流表插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(String.valueOf(id));
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);

        return SbkResult.ok(id);
    }

    @Override
    public SbkResult updateOrder(String orderId) {
        TbOrder order = orderMapper.selectByPrimaryKey(orderId);
        order.setStatus(2);
        orderMapper.updateByPrimaryKey(order);
        return SbkResult.ok();
    }

    @Override
    public List<OrderInfo> getOrderList(OrderInfo orderInfo) {
        TbOrderExample example = new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(orderInfo.getUserId());
        List<TbOrder> orders = orderMapper.selectByExample(example);
        List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
        for (TbOrder order : orders) {
            TbOrderItemExample itemExample = new TbOrderItemExample();
            TbOrderItemExample.Criteria itemCriteria = itemExample.createCriteria();
            itemCriteria.andOrderIdEqualTo(order.getOrderId());
            List<TbOrderItem> tbOrderItems = orderItemMapper.selectByExample(itemExample);
            OrderInfo info = new OrderInfo();
            info.setOrderId(order.getOrderId());
            info.setPayment(order.getPayment());
            info.setStatus(order.getStatus());
            info.setCreateTime(order.getCreateTime());
            info.setUpdateTime(order.getUpdateTime());
            info.setOrderItems(tbOrderItems);
            orderInfoList.add(info);
        }
        return orderInfoList;
    }

    @Override
    public SbkResult deleteOrder(String orderId) {
        orderMapper.deleteByPrimaryKey(orderId);
        return SbkResult.ok();
    }

    @Override
    public SbkResult clearOrder(long userId) {
        TbOrderExample example = new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        orderMapper.deleteByExample(example);
        return SbkResult.ok();
    }
}
