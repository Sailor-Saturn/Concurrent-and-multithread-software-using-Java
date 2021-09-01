package MultiThread;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import Utils.Utils;

public class MultiThreadFilters {
    int nThreads;
    String file;
    Color image[][];
    Random rando = new Random();
    CountDownLatch doneSignal;

    // Constructor with filename for source image
    MultiThreadFilters(String filename, int nThreads) {
        this.file = filename;
        image = Utils.loadImage(filename);
        this.nThreads = nThreads;
        this.doneSignal = new CountDownLatch(nThreads);
    }

    // Brighter filter works by adding value to each of the red, green and blue of each pixel
    // up to the maximum of 255
    public void BrighterFilter(String outputFile, int value) throws InterruptedException {
        Color tmp[][] = Utils.copyImage(image);
        int partitionHeight = tmp[0].length/nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }
        for (int i = 0; i < nThreads; ++i) { // create and start threads
            new Thread(new BrighterThreadFilter(doneSignal, i*partitionHeight,  partitionHeight*(i + 1), tmp,128)).start();
        }
        // wait for all to finish
        doneSignal.await();
        Utils.writeImage(tmp, outputFile);
    }

    public void GreyScaleFilter(String outputFile) throws InterruptedException {
        Color tmp[][] = Utils.copyImage(image);
        int partitionHeight = tmp[0].length/nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }
        for (int i = 0; i < nThreads; ++i) { // create and start threads
            new Thread(new GrayscaleThreadFilter(doneSignal, i*partitionHeight,  partitionHeight*(i + 1), tmp)).start();
        }
        // wait for all to finish
        doneSignal.await();
        Utils.writeImage(tmp, outputFile);
    }

    public void SwirlFilter(String outputFile) throws InterruptedException{
        Color[][] tmp = Utils.copyImage(image);
        Color[][] clone = Utils.copyImage(image);
        int width = tmp.length;
        int height = tmp[0].length;

        double x0 = 0.5 * (width - 1);
        double y0 = 0.5 * (height - 1);

        int partitionHeight = tmp[0].length/nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }

        for (int i = 0; i < nThreads; ++i) { // create and start threads
            new Thread(new SwirlThreadFilter(i*partitionHeight,partitionHeight*(i + 1),tmp,x0,y0,width,height,doneSignal,clone)).start();
        }

        // wait for all to finish
        doneSignal.await();
        Utils.writeImage(clone, outputFile);

    }

    public void GlassFilter(String outputFile) throws InterruptedException{
        Color[][] tmp = Utils.copyImage(image);
        Color[][] clone = Utils.copyImage(image);

        int width = tmp.length;
        int height = tmp[0].length;

        int partitionHeight = tmp[0].length/nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }
        for (int i = 0; i < nThreads; ++i) { // create and start threads
            new Thread(new GlassThreadFilter(i*partitionHeight,partitionHeight*(i + 1),
                    tmp,width,height,doneSignal,clone, rando)).start();
        }
        // wait for all to finish
        doneSignal.await();
        Utils.writeImage(clone, outputFile);
    }

    public void BlurFilter(String outputFile) throws InterruptedException{
        Color[][] tmp = Utils.copyImage(image);
        Color[][] clone = Utils.copyImage(image);

        int width = tmp.length;
        int height = tmp[0].length;

        int partitionHeight = tmp[0].length/nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }

        for (int i = 0; i < nThreads; ++i) { // create and start threads
            new Thread(new BlurThreadFilter(i*partitionHeight,partitionHeight*(i + 1),
                    tmp,width,height,doneSignal,clone)).start();
        }

        // wait for all to finish
        doneSignal.await();
        Utils.writeImage(clone, outputFile);
    }


}


