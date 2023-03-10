package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CanvasController {
    private String turn;
    @Autowired
    private Canvas canvas;

    @MessageMapping("/paint")
    @SendTo("/topic/canvas")
    public Canvas paint(PaintMessage paintMessage) {
        if (canvas.getWinner() != null) {
            canvas.reset();
            turn = null;
        }
        if (paintMessage.getColor().equals(turn) || !canvas.available(paintMessage))
            return canvas;
        turn = paintMessage.getColor();
        return canvas.paint(paintMessage);
    }

    @SubscribeMapping("/canvas")
    public Canvas sendInitialCanvas() {
        return canvas;
    }
}
