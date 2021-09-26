package com.jwh.miaosha.Mq.comsume;

import com.jwh.miaosha.Data.User;
import com.jwh.miaosha.Model.MiaoShaModel;
import com.jwh.miaosha.Service.TransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：jwh
 * @description：TODO
 * @date ：2022/2/17 21:00
 */
@Slf4j
@Component
public class comsume {
    @Autowired
    TransService transService;


    @RabbitListener(queues = "do_miaoSha")
    public void receive(byte[] body) {
        log.info("开始消费");
        try {
            String message = new String(body, "UTF-8");
            MiaoShaModel miaoShaModel = Utils.JacksonUtils.reverseSer(message, MiaoShaModel.class);
            User user = miaoShaModel.getUser();
            int goodId = miaoShaModel.getGoodId();
            int num = miaoShaModel.getNum();
            transService.transaction(user, goodId, num);
            log.info("消费成功:{}", message);
        } catch (IOException e) {
            log.info("JacksonTransformError:{}", e.getMessage());
            e.printStackTrace();
        }

    }


}
