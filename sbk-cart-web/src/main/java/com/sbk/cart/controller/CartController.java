package com.sbk.cart.controller;

import com.sbk.cart.service.CartService;
import com.sbk.pojo.TbItem;
import com.sbk.pojo.TbUser;
import com.sbk.service.ItemService;
import com.sbk.utils.CookieUtils;
import com.sbk.utils.JsonUtils;
import com.sbk.utils.SbkResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author You
 * @create 2018-11-12 13:50
 */

@Controller
public class CartController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;


    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response){

        TbUser user = (TbUser) request.getAttribute("user");
        if(user != null){
            cartService.addCart(user.getId(), itemId, num);
            return "cartSuccess";
        }

        List<TbItem> cartList = getCartListFromCookies(request);
        boolean flag = false;
        for (TbItem tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                flag = true;
                tbItem.setNum(tbItem.getNum()+num);
                break;
            }
        }

        if(!flag){
            TbItem item = itemService.getItemById(itemId);
            item.setNum(num);
            cartList.add(item);
        }

        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),3600*24*7, true);
        return "cartSuccess";
    }

    private List<TbItem> getCartListFromCookies(HttpServletRequest request){
        String cart = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isBlank(cart)) {
            return new ArrayList<TbItem>();
        }
        List<TbItem> list = JsonUtils.jsonToList(cart, TbItem.class);
        return list;
    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, HttpServletResponse response){
        List<TbItem> cartList = getCartListFromCookies(request);
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            //从cookie中取购物车列表
            //如果不为空，把cookie中的购物车商品和服务端的购物车商品合并。
            cartService.mergeCart(user.getId(), cartList);
            //把cookie中的购物车删除
            CookieUtils.deleteCookie(request, response, "cart");
            //从服务端取购物车列表
            cartList = cartService.getCartList(user.getId());

        }
        request.setAttribute("cartList", cartList);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public SbkResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num,
                                   HttpServletRequest request, HttpServletResponse response){

        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.updateCartNum(user.getId(), itemId, num);
            return SbkResult.ok();
        }

        List<TbItem> cartList = getCartListFromCookies(request);
        for (TbItem cart : cartList) {
            if(cart.getId().longValue() == itemId){
                cart.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),3600*24*7, true);
        return SbkResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId,
                                   HttpServletRequest request, HttpServletResponse response){

        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.deleteCartItem(user.getId(), itemId);
            return "redirect:/cart/cart.html";
        }

        List<TbItem> cartList = getCartListFromCookies(request);
        for (TbItem cart : cartList) {
            if(cart.getId().longValue() == itemId){
                cartList.remove(cart);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),3600*24*7, true);
        return "redirect:/cart/cart.html";
    }

    @RequestMapping("/cart/delCart")
    public String delCart(String[] cart_list,
                             HttpServletRequest request, HttpServletResponse response){
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            if(cart_list.length > 0 && cart_list != null){
                for (String s : cart_list) {
                    String[] cartItem = s.split("!");
                    cartService.deleteCartItem(user.getId(), Long.parseLong(cartItem[0]));
                }
            }
            return "redirect:/cart/cart.html";
        }

        List<TbItem> cartList = getCartListFromCookies(request);
        for (String s : cart_list) {
            String[] cartItem = s.split("!");
            for (TbItem cart : cartList) {
                if(cart.getId().longValue() == Long.parseLong(cartItem[0])){
                    cartList.remove(cart);
                }
            }
        }

        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),3600*24*7, true);
        return "redirect:/cart/cart.html";
    }

    @RequestMapping("/cart/clearCart")
    public String clearCart(HttpServletRequest request,HttpServletResponse response){
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.clearCart(user.getId());
            return "redirect:/cart/cart.html";
        }

        CookieUtils.setCookie(request, response, "cart", "",3600*24*7, true);
        return "redirect:/cart/cart.html";
    }
}
