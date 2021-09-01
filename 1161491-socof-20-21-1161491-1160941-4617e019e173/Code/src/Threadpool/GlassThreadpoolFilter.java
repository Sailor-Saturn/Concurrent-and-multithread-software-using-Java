package Threadpool;

import Utils.Utils;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class GlassThreadpoolFilter implements Runnable{

    int initialHeight;
    int finalHeight;
    Color matrix [][];
    int width;
    int height;
    private Color clone [][];
    private Random rando;

    public GlassThreadpoolFilter(int initialHeight, int finalHeight, Color[][] matrix, int width, int height,
                                 Color[][] clone, Random rando) {
        this.initialHeight = initialHeight;
        if(finalHeight > matrix[0].length){
            this.finalHeight = matrix[0].length;
        }else {
            this.finalHeight = finalHeight;
        }
        this.matrix = matrix;
        this.width = width;
        this.height = height;
        this.clone = clone;
        this.rando = rando;
    }

    @Override
    public void run() {
        for (int i = 0; i < width; i++) {
            for (int j = initialHeight; j < finalHeight; j++) {
                int randomNumber = Utils.random(rando, -5, 5);
                int newX = (width + i + randomNumber) % width;
                int newY = (height + j + randomNumber) % height;
                Color pixel = matrix[newX][newY];
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();
                clone[i][j] = new Color(r, g, b);
            }
        }

    }
}
