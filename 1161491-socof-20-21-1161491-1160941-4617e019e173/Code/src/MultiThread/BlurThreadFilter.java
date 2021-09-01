package MultiThread;

import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class BlurThreadFilter extends Thread{

    int initialHeight;
    int finalHeight;
    Color matrix [][];
    int width;
    int height;
    private final CountDownLatch doneSignal;
    private Color clone [][];
    private Semaphore sem;

    public BlurThreadFilter(int initialHeight, int finalHeight, Color[][] matrix,  int width, int height,
                            CountDownLatch doneSignal, Color[][] clone) {
        this.initialHeight = initialHeight;
        if(finalHeight > matrix[0].length){
            this.finalHeight = matrix[0].length;
        }else {
            this.finalHeight = finalHeight;
        }
        this.matrix = matrix;
        this.width = width;
        this.height = height;
        this.doneSignal = doneSignal;
        this.clone = clone;
    }

    public void run() {
        for (int i = 0; i < width; i++) {
            for (int j = initialHeight; j < finalHeight; j++) {
                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;

                for (int x = i - 1; x < i + 2; x++) {
                    for (int y = j - 1; y < j + 2; y++) {
                        if (x >= 0 && x < width && y >= 0 && y < height) {
                            Color pixel = matrix[x][y];
                            redSum += pixel.getRed();
                            greenSum += pixel.getGreen();
                            blueSum += pixel.getBlue();
                        }
                    }
                }

                int r = redSum / 9;
                int g = greenSum / 9;
                int b = blueSum / 9;
                clone[i][j] = new Color(r, g, b);
            }
        }
        doneSignal.countDown();
    }
}
