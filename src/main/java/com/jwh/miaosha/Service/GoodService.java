package com.jwh.miaosha.Service;

import com.jwh.miaosha.Annotation.annotation.Cacheable;
import com.jwh.miaosha.Common.Constant;
import com.jwh.miaosha.Dao.GoodsMapper;
import com.jwh.miaosha.Data.Goods;
import com.jwh.miaosha.Expection.ExceptionErrorCode;
import com.jwh.miaosha.Expection.ExceptionLogical;
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


    @Cacheable(prefixKey = Constant.Good, Key = "info")
    public Goods getGoodsById(Integer goodsId){
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods == null){
            log.info( LoggerConst.Goods.name(),goodsId + "goods is not found");
            throw new ExceptionLogical(ExceptionErrorCode.GoodNotFond);
        }
        return goods;
    }


    public List<Goods> getAvaliableGoods(){
        List<Goods> goodsAll = goodsMapper.getAvaliableGoods();
        if (goodsAll == null){
            log.info( LoggerConst.Goods.name(), "goods is none");
            throw new ExceptionLogical(ExceptionErrorCode.GoodIsNone);
        }
        return goodsAll;
    }





}
