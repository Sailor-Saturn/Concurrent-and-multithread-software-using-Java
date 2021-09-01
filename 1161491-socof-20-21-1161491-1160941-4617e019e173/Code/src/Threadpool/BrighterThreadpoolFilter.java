package Threadpool;

import java.awt.*;

public class BrighterThreadpoolFilter implements Runnable{
    int initialHeight;
    int finalHeight;
    int value;
    Color matrix [][];

    public BrighterThreadpoolFilter(int initialHeight, int finalHeight, int value, Color[][] matrix) {
        this.initialHeight = initialHeight;
        if(finalHeight > matrix[0].length){
            this.finalHeight = matrix[0].length;
        }else {
            this.finalHeight = finalHeight;
        }
        this.matrix = matrix;
        this.value = value;
    }

    @Override
    public void run() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = initialHeight; j < finalHeight; j++) {

                // fetches values of each pixel
                Color pixel = matrix[i][j];
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                // takes average of color values
                int bright = value;
                if (r + bright > 255)
                    r = 255;
                else
                    r = r + bright;
                if (g + bright > 255)
                    g = 255;
                else
                    g = g + bright;
                if (b + bright > 255)
                    b = 255;
                else
                    b = b + bright;

                // outputs average into picuture to make grayscale
                matrix[i][j] = new Color(r, g, b);
            }
        }
    }
}
