package MultiThread;

import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class GrayscaleThreadFilter extends Thread{

    int initialHeight;
    int finalHeight;
    Color matrix [][];
    private final CountDownLatch doneSignal;

    public GrayscaleThreadFilter(CountDownLatch doneSignal, int initialHeight,
                                 int finalHeight, Color[][]matrix) {
        this.initialHeight = initialHeight;
        if(finalHeight > matrix[0].length){
            this.finalHeight = matrix[0].length;
        }else {
            this.finalHeight = finalHeight;
        }
        this.matrix = matrix;
        this.doneSignal = doneSignal;
    }

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
        doneSignal.countDown();
    }
}
