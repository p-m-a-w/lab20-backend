package com.example.demo;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Canvas {
    @Getter
    private String[][] colorGrid;
    @Getter
    private final int width = 64;
    @Getter
    private final int height = 64;

    public Canvas() {
        colorGrid = new String[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colorGrid[y][x] = "#ffffff";
            }
        }
    }

    public Canvas paint(PaintMessage paintMessage) {
        int posX = paintMessage.getPosX();
        int posY = paintMessage.getPosY();
        if (posX >= 0 && posX < width && posY >= 0 && posY < height) {
            colorGrid[posY][posX] = paintMessage.getColor();
        }
        System.out.println(Arrays.deepToString(colorGrid));
        return this;
    }
}
