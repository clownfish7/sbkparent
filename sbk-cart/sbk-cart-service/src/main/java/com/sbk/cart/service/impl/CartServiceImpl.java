package com.sbk.cart.service.impl;

import com.sbk.cart.service.CartService;
import com.sbk.mapper.TbItemMapper;
import com.sbk.pojo.TbItem;
import com.sbk.redis.JedisClient;
import com.sbk.utils.JsonUtils;
import com.sbk.utils.SbkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author You
 * @create 2018-11-12 19:20
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public SbkResult addCart(long userId, long itemId, int num) {

        Boolean hexists = jedisClient.hexists("REDIS_CART_PRE" + ":" + userId, itemId + "");

        if(hexists){
            String json = jedisClient.hget("REDIS_CART_PRE" + ":" + userId, itemId + "");
            TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
            tbItem.setNum(tbItem.getNum()+num);
            jedisClient.hset("REDIS_CART_PRE" + ":" + userId, itemId + "",JsonUtils.objectToJson(tbItem));
            return SbkResult.ok();
        }

        TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        tbItem.setNum(num);
        jedisClient.hset("REDIS_CART_PRE" + ":" + userId, itemId + "",JsonUtils.objectToJson(tbItem));
        return SbkResult.ok();
    }

    @Override
    public SbkResult mergeCart(long userId, List<TbItem> itemList) {
        for (TbItem tbItem : itemList) {
            addCart(userId, tbItem.getId(), tbItem.getNum());
        }
        return SbkResult.ok();
    }

    @Override
    public List<TbItem> getCartList(long userId) {
        //根据用户id查询购车列表
        List<String> jsonList = jedisClient.hvals("REDIS_CART_PRE" + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for (String string : jsonList) {
            //创建一个TbItem对象
            TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
            //添加到列表
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public SbkResult updateCartNum(long userId, long itemId, int num) {
        //从redis中取商品信息
        String json = jedisClient.hget("REDIS_CART_PRE" + ":" + userId, itemId + "");
        //更新商品数量
        TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
        tbItem.setNum(num);
        //写入redis
        jedisClient.hset("REDIS_CART_PRE" + ":" + userId, itemId + "", JsonUtils.objectToJson(tbItem));
        return SbkResult.ok();
    }

    @Override
    public SbkResult deleteCartItem(long userId, long itemId) {
        // 删除购物车商品
        jedisClient.hdel("REDIS_CART_PRE" + ":" + userId, itemId + "");
        return SbkResult.ok();
    }

    @Override
    public SbkResult clearCart(long userId) {
        //删除购物车信息
        jedisClient.del("REDIS_CART_PRE" + ":" + userId);
        return SbkResult.ok();
    }
}
