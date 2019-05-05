package com.sbk.cart.service;

import com.sbk.pojo.TbItem;
import com.sbk.utils.SbkResult;

import java.util.List;

/**
 * @author You
 * @create 2018-11-12 19:19
 */
public interface CartService {

    SbkResult addCart(long userId, long itemId, int num);
    SbkResult mergeCart(long userId, List<TbItem> list);
    List<TbItem> getCartList(long userId);
    SbkResult updateCartNum(long userId, long itemId, int num);
    SbkResult deleteCartItem(long userId, long itemId);
    SbkResult clearCart(long userId);
}
