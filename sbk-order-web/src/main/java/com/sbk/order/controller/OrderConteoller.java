package com.sbk.order.controller;

import com.sbk.order.pojo.OrderInfo;
import com.sbk.order.service.OrderService;
import com.sbk.pojo.TbItem;
import com.sbk.pojo.TbOrderItem;
import com.sbk.pojo.TbUser;
import com.sbk.service.ItemService;
import com.sbk.utils.CookieUtils;
import com.sbk.utils.JsonUtils;
import com.sbk.utils.SbkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author You
 * @create 2018-11-13 14:32
 */
@Controller
public class OrderConteoller {

    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(String[] cart_list, HttpServletRequest request){
        List<TbItem> list = new ArrayList<>();
        if(cart_list.length > 0 && cart_list != null){
            for (String s : cart_list) {
                String[] cartItem = s.split("!");
                TbItem item = itemService.getItemById(Long.parseLong(cartItem[0]));
                item.setNum(Integer.parseInt(cartItem[1]));
                list.add(item);
            }
        }
        request.setAttribute("cartList",list );
        return "order-cart";
    }

    @RequestMapping("/order/order")
    public String showOrder(OrderInfo orderInfo, HttpServletRequest request){
        TbUser user = (TbUser) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        List<OrderInfo> orderList = orderService.getOrderList(orderInfo);
        for (OrderInfo info : orderList) {
            System.out.println(info.getOrderId());
            System.out.println(info.getOrderItems());
            System.out.println("---------------------------------------------------");
            List<TbOrderItem> orderItems = info.getOrderItems();
            for (TbOrderItem orderItem : orderItems) {
                System.out.println(orderItem.getId()+"---"+orderItem.getNum());
            }
            System.out.println("===================================================");
        }
        request.setAttribute("orderList", orderList);
        return "order";
    }

    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public String newOrder(OrderInfo orderInfo, HttpServletRequest request){
        TbUser user = (TbUser) request.getAttribute("user");
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());

        SbkResult result = orderService.newOrder(orderInfo);
        //如果订单生成成功，需要删除购物车
       /* if (result.getStatus() == 200) {
            //清空购物车
            cartService.clearCartItem(user.getId());
        }*/

        request.setAttribute("orderId", result.getData());
        request.setAttribute("payment", orderInfo.getPayment());

        return "success";
    }

    @RequestMapping("/order/delete/{orderId}")
    public String deleteOrder(@PathVariable String orderId){
        orderService.deleteOrder(orderId);
        return "redirect:/order/order.html";
    }

    @RequestMapping("/order/delOrder")
    public String delCart(String[] order_list){
        for (String orderId : order_list) {
            orderService.deleteOrder(orderId);
        }
        return "redirect:/order/order.html";
    }

    @RequestMapping("/order/clearOrder")
    public String clearCart(HttpServletRequest request){
        TbUser user = (TbUser) request.getAttribute("user");
        if(user!=null){
            orderService.clearOrder(user.getId());
        }
        return "redirect:/order/order.html";
    }
}
