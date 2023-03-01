package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CanvasController {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private Canvas canvas;

    @MessageMapping("/paint")
    @SendTo("/topic/canvas")
    public Canvas paint(PaintMessage paintMessage) {
        return canvas.paint(paintMessage);
    }

    @SubscribeMapping("/canvas")
    public Canvas sendInitialCanvas() {
        return canvas;
    }
}
