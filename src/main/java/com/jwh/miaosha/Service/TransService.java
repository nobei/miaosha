package com.jwh.miaosha.Service;

import com.jwh.miaosha.Dao.GoodsMapper;
import com.jwh.miaosha.Dao.OrderMapper;
import com.jwh.miaosha.Data.Goods;
import com.jwh.miaosha.Data.Order;
import com.jwh.miaosha.Data.User;
import com.jwh.miaosha.Redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Slf4j
public class TransService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    OrderMapper orderMapper;


    @Transactional
    public Order  transaction(User user, int goodId, int num){
        Goods goods = goodsMapper.selectByPrimaryKey(goodId);
        Order order = null;
        if (goods != null &&
                goods.getGoodnum() >= num){
            goods.setGoodnum(goods.getGoodnum()-num);
            goodsMapper.updateByPrimaryKey(goods);
            order = new Order();
            order.setBugcount(num);
            order.setBugtime(new Date());
            order.setGoodid(goodId);
            order.setUserid(user.getId());
            order.setBugprice(goods.getGoodprice());
            orderMapper.insert(order);
        }
        return order;
    }
}
