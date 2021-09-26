package com.jwh.miaosha.Controller;

import com.jwh.miaosha.Annotation.annotation.Register;
import com.jwh.miaosha.Common.Constant;
import com.jwh.miaosha.Data.Goods;
import com.jwh.miaosha.Data.Order;
import com.jwh.miaosha.Data.User;
import com.jwh.miaosha.Model.MiaoShaModel;
import com.jwh.miaosha.Model.ResponseModel;
import com.jwh.miaosha.Model.ResponseStatus;
import com.jwh.miaosha.Mq.product.product;
import com.jwh.miaosha.Redis.RedisUtils;
import com.jwh.miaosha.Service.GoodService;
import com.jwh.miaosha.Service.TransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.jwh.miaosha.Model.ResponseModel.ResponseBuild;

@Slf4j
@Controller
@RequestMapping("/order")
public class orderController {
    @Autowired
    TransService transService;
    @Autowired
    RedisUtils redis;
    @Autowired
    GoodService goodService;
    @Autowired
    com.jwh.miaosha.Mq.product.product product;

    static Map<Integer, Boolean> mapBook = new HashMap<>();

    private ReentrantLock reentrantLock = new ReentrantLock();

    @RequestMapping(value = "/do_miaoSha/{goodId}/{num}", method = RequestMethod.POST)
    @ResponseBody
    @Register
    public ResponseModel miaoSha(HttpServletRequest request, @PathVariable int goodId, @PathVariable int num) throws Exception {
        log.info(Thread.currentThread()+"");
        log.info(redis.toString());
        ResponseBuild responseBuild = new ResponseBuild();
        if (mapBook.get(goodId) == null || !mapBook.get(goodId)) {
            responseBuild.setData(null).setMessage("orderHasSelled").setStatusCode(ResponseStatus.SelledOut);
            return responseBuild.Build();
        } else {
            int goodNum = (int) redis.get(Constant.Good, goodId + "", Integer.class);
            if (goodNum <= 0) {
                mapBook.put(goodId, false);
                responseBuild.setStatusCode(ResponseStatus.SelledOut);
            } else {
                User user = null;
                Cookie cookie[] = request.getCookies();
                for (Cookie cookie1 : cookie) {
                    if (cookie1.getName().equals(Constant.User.name())) {
                        String token = cookie1.getValue();
                        user = (User) redis.get(Constant.User, token, User.class);
                    }
                }
                if (user != null) {
                    transactionWithMQ(responseBuild,user,goodId,num,goodNum);
                }else{
                    responseBuild.setStatusCode(ResponseStatus.Failed).setMessage("登录失效");
                }

            }
        }
        return responseBuild.Build();

    }

    public void transactionWithMQ(ResponseBuild responseBuild, User user,int goodId,
                                  int num,int goodNum) throws Exception {
        MiaoShaModel miaoShaModel = new MiaoShaModel();
        miaoShaModel.setUser(user);
        miaoShaModel.setGoodId(goodId);
        miaoShaModel.setNum(num);
        product.order_do_mq(miaoShaModel);

    }

    public void transactionWithoutMQ(ResponseBuild responseBuild, User user,int goodId,
                                     int num,int goodNum){
        try {
            reentrantLock.lock();
            Order oreder = transService.transaction(user, goodId, num);
            if(oreder != null){
                responseBuild.setStatusCode(ResponseStatus.Success).setData(oreder);
                redis.set(Constant.Good,""+goodId,goodNum-num);
            }else{
                responseBuild.setStatusCode(ResponseStatus.Failed);
                redis.set(Constant.Good,""+goodId,0);
            }
        }catch(Exception e) {
            log.error(e.getMessage());
            responseBuild.setStatusCode(ResponseStatus.Failed).setMessage("程序异常");
        }finally{
            reentrantLock.unlock();
        }

    }


    @PostConstruct
    public void initRedis() {
        List<Goods> goodsList = goodService.getAvaliableGoods();
        if (goodsList == null) {
            return;
        }
        for (Goods good : goodsList) {
            redis.set(Constant.Good, "" + good.getId(), good.getGoodnum());
            mapBook.put(good.getId(), true);
        }

    }


}
