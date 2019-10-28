package com.diyishuai.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WsController {

    @RequestMapping(path = "/ws")
    public String ws(){
        return "ws";
    }


    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage say(RequestMessage message) {
        for (int i = 0; i < 10; i++) {
            return new ResponseMessage(i+"!!!!!");
        }
//        return
//        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }
}