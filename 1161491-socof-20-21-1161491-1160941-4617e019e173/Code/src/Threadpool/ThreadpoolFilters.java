package Threadpool;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import Utils.Utils;

public class ThreadpoolFilters {
    int nThreads;
    String file;
    Color image[][];
    Random rando = new Random();
    ExecutorService executor;

    ThreadpoolFilters(String filename,ExecutorService executor, int nThreads){
        this.file = filename;
        image = Utils.loadImage(filename);
        this.executor = executor;
        this.nThreads = nThreads;
    }

    public Color[][] BrighterFilter(int value) {
        Color tmp[][] = Utils.copyImage(image);
        int partitionHeight = tmp[0].length/ nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }

        for (int i = 0; i < nThreads; ++i) { // create and start threads
            Runnable task = new BrighterThreadpoolFilter(i*partitionHeight,partitionHeight*(i + 1),value, tmp);
            executor.execute(task);
        }

        return tmp;
    }
    public Color[][] GrayScaleFilter(){
        Color tmp[][] = Utils.copyImage(image);
        int partitionHeight = tmp[0].length/ nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }

        for (int i = 0; i < nThreads; ++i) { // create and start threads
            Runnable task = new GrayscaleThreadpoolFilter(i*partitionHeight,partitionHeight*(i + 1), tmp);
            executor.execute(task);
        }
       return tmp;
    }

    public Color[][] GlassFilter() {
        Color[][] tmp = Utils.copyImage(image);
        Color[][] clone = Utils.copyImage(image);

        int width = tmp.length;
        int height = tmp[0].length;

        int partitionHeight = tmp[0].length/ nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }

        for (int i = 0; i < nThreads; ++i) { // create and start threads
            Runnable task = new GlassThreadpoolFilter(i*partitionHeight, partitionHeight*(i + 1),
                    tmp, width, height, clone, rando);
            executor.execute(task);
        }

        return clone;
    }

    public Color[][] SwirlFilter() {
        Color[][] tmp = Utils.copyImage(image);
        Color[][] clone = Utils.copyImage(image);
        int width = tmp.length;
        int height = tmp[0].length;

        double x0 = 0.5 * (width - 1);
        double y0 = 0.5 * (height - 1);

        int partitionHeight = tmp[0].length/ nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }

        for (int i = 0; i < nThreads; ++i) { // create and start threads
            Runnable task = new SwirlThreadpoolFilter(i*partitionHeight,partitionHeight*(i + 1),tmp,x0,y0,width,height,clone);
            executor.execute(task);
        }
        return clone;
    }

    public Color[][] BlurFilter()  {
        Color[][] tmp = Utils.copyImage(image);
        Color[][] clone = Utils.copyImage(image);

        int width = tmp.length;
        int height = tmp[0].length;

        int partitionHeight = tmp[0].length/ nThreads;
        if(tmp[0].length % nThreads != 0){
            partitionHeight++;
        }

        for (int i = 0; i < nThreads; ++i) { // create and start threads
            Runnable task = new BlurThreadpoolFilter(i*partitionHeight,partitionHeight*(i + 1), tmp,
                    width, height, clone);
            executor.execute(task);
        }

        return clone;
    }
}
