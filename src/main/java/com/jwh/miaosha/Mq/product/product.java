package com.jwh.miaosha.Mq.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jwh.miaosha.Config.ApplicationCtx;
import com.jwh.miaosha.Model.MiaoShaModel;
import groovy.util.logging.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author ：jwh
 * @description：TODO
 * @date ：2022/2/17 21:15
 */
@Component
@Slf4j
public class product implements RabbitTemplate.ConfirmCallback {
    @Resource(name = "ctx")
    ApplicationContext applicationCtx;

    @Value("${rabbitmq.topic}")
    String topic;

    @Value("${rabbitmq.routeKey}")
    String routeKey;

    private RabbitTemplate rabbitTemplate;

    public void order_do_mq(MiaoShaModel miaoShaModel) throws Exception {
        try {
            MessageProperties messageProperties = new MessageProperties();
            String parseJson = Utils.JacksonUtils.transform(miaoShaModel);
            Message message = new Message(parseJson.getBytes(), messageProperties);

            rabbitTemplate = applicationCtx.getBean(RabbitTemplate.class);

            rabbitTemplate.convertAndSend(routeKey, message,
                    (message1) -> {
                        System.err.println("------添加额外的设置---------");
                        message1.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
                        message1.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
                        return message1;
                    });
            log.info("发送消息:{}",message);
        }catch (AmqpException e){
            log.info("发送消息失败:{0},{1}",miaoShaModel.toString());
            throw new Exception("mq");
        }catch (JsonProcessingException e){
            log.info("解析对象失败:{0}",miaoShaModel.toString());
            throw new Exception("jackson");
        }
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

    }
}
