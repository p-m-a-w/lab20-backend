package com.example.demo;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Canvas {
    @Getter
    private String[][] colorGrid;
    @Getter
    private final int width = 3;
    @Getter
    private final int height = 3;
    @Getter
    private String winner;

    public Canvas() {
        colorGrid = new String[height][width];
        reset();
    }

    public void reset() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colorGrid[y][x] = "#ffffff";
            }
        }
    }

    private boolean sameColorTiles(int[] y, int[] x) {
        assert x.length == y.length && x.length >= 2;
        if (colorGrid[y[0]][x[0]].equals("#ffffff"))
            return false;
        for (int i = 1; i < x.length; i++) {
            if (!Objects.equals(
                    colorGrid[y[i]][x[i]],
                    colorGrid[y[0]][x[0]]
            ))
                return false;
        }
        return true;
    }

    public String winner() {
        for (int i = 0; i < 3; i++) {
            if (sameColorTiles(new int[]{i, i, i}, new int[]{0, 1, 2}))
                return colorGrid[i][0];
            if (sameColorTiles(new int[]{0, 1, 2}, new int[]{i, i, i}))
                return colorGrid[0][i];
        }
        if (
                sameColorTiles(new int[]{0, 1, 2}, new int[]{0, 1, 2}) ||
                        sameColorTiles(new int[]{2, 1, 0}, new int[]{0, 1, 2})
        )
            return colorGrid[1][1];

        int size = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!Objects.equals(colorGrid[y][x], "#ffffff"))
                    size++;
            }
        }
        return size >= 9 ? "over" : null;
    }

    public boolean available(PaintMessage paintMessage) {
        int posX = paintMessage.getPosX();
        int posY = paintMessage.getPosY();
        return Objects.equals(colorGrid[posY][posX], "#ffffff");
    }

    public Canvas paint(PaintMessage paintMessage) {
        int posX = paintMessage.getPosX();
        int posY = paintMessage.getPosY();
        if (posX >= 0 && posX < width && posY >= 0 && posY < height)
            colorGrid[posY][posX] = paintMessage.getColor();
        winner = winner();
        if (winner != null) reset();
        return this;
    }
}
