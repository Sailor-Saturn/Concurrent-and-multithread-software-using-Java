package Threadpool;

import java.awt.*;

public class GrayscaleThreadpoolFilter implements Runnable{
    int initialHeight;
    int finalHeight;
    Color matrix [][];

    public GrayscaleThreadpoolFilter(int initialHeight, int finalHeight, Color[][] matrix) {
        this.initialHeight = initialHeight;
        if(finalHeight > matrix[0].length){
            this.finalHeight = matrix[0].length;
        }else {
            this.finalHeight = finalHeight;
        }
        this.matrix = matrix;
    }

    @Override
    public void run() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = initialHeight; j < finalHeight; j++) {
                Color pixel = matrix[i][j];
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                int average = (r + g + b) / 3;
                r = average;
                g = average;
                b = average;
                matrix[i][j] = new Color(r, g, b);
            }
        }
    }
}
