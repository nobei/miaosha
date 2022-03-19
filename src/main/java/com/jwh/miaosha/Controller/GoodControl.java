package com.jwh.miaosha.Controller;

import com.jwh.miaosha.Data.Goods;
import com.jwh.miaosha.Model.ResponseModel;
import com.jwh.miaosha.Model.ResponseStatus;
import com.jwh.miaosha.Service.GoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/good")
@Slf4j
public class GoodControl {

    @Autowired
    GoodService goodService;

    @RequestMapping(value = "/goodId/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Goods> getGoodsById(@PathVariable("id") Integer goodId){
        ResponseModel responseModel = new ResponseModel();
        Goods goods = goodService.getGoodsById(goodId);
        if (goodId != null) {
            responseModel.setData(goods);
            responseModel.setStatusCode(ResponseStatus.Success);
        }else{
            responseModel.setStatusCode(ResponseStatus.Failed);
        }

        return responseModel;

    }

    @RequestMapping("/getList")
    @ResponseBody
    public ResponseModel<List<Goods>> getGoodsAll(){
        ResponseModel responseModel = new ResponseModel();
        List<Goods> goods = goodService.getAvaliableGoods();
        if(goods != null){
            responseModel.setData(goods);
            responseModel.setStatusCode(ResponseStatus.Success);
        }else{
            responseModel.setStatusCode(ResponseStatus.Failed);
        }

        return responseModel;
    }




}
