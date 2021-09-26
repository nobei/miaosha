package com.jwh.miaosha.Service;

import com.jwh.miaosha.Dao.GoodsMapper;
import com.jwh.miaosha.Data.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jwh.miaosha.Logger.LoggerConst;

import java.util.List;

@Service
@Slf4j
public class GoodService {
    @Autowired
    GoodsMapper goodsMapper;

    public Goods getGoodsById(Integer goodsId){
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods == null){
            log.info( LoggerConst.Goods.name(),goodsId + "goods is not found");
        }
        return goods;
    }


    public List<Goods> getAvaliableGoods(){
        List<Goods> goodsAll = goodsMapper.getAvaliableGoods();
        if (goodsAll == null){
            log.info( LoggerConst.Goods.name(), "goods is none");
        }
        return goodsAll;
    }





}
