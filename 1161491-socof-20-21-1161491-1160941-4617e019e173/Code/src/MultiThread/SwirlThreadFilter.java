package MultiThread;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class SwirlThreadFilter extends Thread{

    int initialHeight;
    int finalHeight;
    Color matrix [][];
    double x0;
    double y0;
    int width;
    int height;
    private final CountDownLatch doneSignal;
    private Color clone [][];

    public SwirlThreadFilter(int initialHeight, int finalHeight, Color[][] matrix,
                             double x0, double y0, int width, int height,
                             CountDownLatch doneSignal, Color[][] clone) {
        this.initialHeight = initialHeight;
        if(finalHeight > matrix[0].length){
            this.finalHeight = matrix[0].length;
        }else {
            this.finalHeight = finalHeight;
        }
        this.matrix = matrix;
        this.x0 = x0;
        this.y0 = y0;
        this.width = width;
        this.height = height;
        this.doneSignal = doneSignal;
        this.clone = clone;
    }

    public void run() {

        for (int i = 0; i < width; i++) {
            for (int j = initialHeight; j < finalHeight; j++) {
                double dx = i - x0;
                double dy = j - y0;

                double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                double angle = (Math.PI / 256) * distance;

                int x = (int) ((Math.cos(angle) * dx) - (Math.sin(angle) * dy) + x0);
                int y = (int) ((Math.sin(angle) * dx) + (Math.cos(angle) * dy) + y0);

                if (x >= 0 && x < width && y >= 0 && y < height) {
                    Color pixel = matrix[x][y];
                    int r = pixel.getRed();
                    int g = pixel.getGreen();
                    int b = pixel.getBlue();
                    clone[i][j] = new Color(r, g, b);
                }
            }
        }
        doneSignal.countDown();
    }

}
