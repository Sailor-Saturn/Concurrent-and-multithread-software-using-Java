package Concurrent;

import Utils.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentImplementation {
    int nThreads;

    public ConcurrentImplementation(int nThreads) {
        this.nThreads = nThreads;
    }

    public void runThreads(String filePath) throws ExecutionException, InterruptedException {
        Color image[][] = Utils.loadImage(filePath);

        int partitionHeight = image[0].length/ nThreads;
        if(image[0].length % nThreads != 0){
            partitionHeight++;
        }

        //Split image into snippets
        ConcurrentHashMap<Integer, Color [] []> map = new ConcurrentHashMap<>();

        for (int i = 0; i < nThreads; i++) {
            int initialHeight = i*partitionHeight;
            int finalHeight = partitionHeight*(i + 1);
            if(finalHeight > image[0].length){
                partitionHeight = partitionHeight - (finalHeight-image[0].length);
                finalHeight = image[0].length;
            }
            Color [] [] snippet = new Color [image.length][partitionHeight];
            for (int j = 0; j < image.length; j++) {
                int snippetHeight = 0;
                for (int k = initialHeight;  k < finalHeight; k++) {
                    snippet[j][snippetHeight] = image[j][k];
                    snippetHeight++;
                }
            }
            map.put(i, snippet);
        }

        List<Semaphore> semaphoreList = new ArrayList(nThreads);
        for (int i = 0; i < nThreads; i++) {
            semaphoreList.add(new Semaphore(1));
        }

        for (int i = 0; i < nThreads; i++) {
            int index = i;
            CompletableFuture<Color[][]> cf1 = CompletableFuture.supplyAsync(() -> {
                try {
                    return applyGrayscale(index, map, semaphoreList.get(index));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            });
            CompletableFuture<Color[][]> cf2 = CompletableFuture.supplyAsync(() -> {
                try {
                    return applyBrightness(index, map,128, semaphoreList.get(index));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }

        ForkJoinPool.commonPool().awaitQuiescence(20, TimeUnit.SECONDS);

        Color [][] finalImage = new Color[image.length][image[0].length];
        int partitionHeightFinal = 0;
        for (int i = 0; i < map.size() ; i++) {
            Color [][]snippet = map.get(i);
            for (int j = 0; j < snippet.length; j++) {
                for (int k = 0;  k < snippet[0].length; k++) {
                    finalImage[j][k + partitionHeightFinal] = snippet[j][k];
                }
            }
            partitionHeightFinal += snippet[0].length;
        }
        Utils.writeImage(finalImage,"Concurrent.jpg");
    }

    private Color[][] applyGrayscale(int index, ConcurrentHashMap<Integer, Color [] []> map,
                                     Semaphore sem) throws InterruptedException {
        sem.acquire();
        Color[][] partialMatrix = map.get(index);
        for (int i = 0; i < partialMatrix.length; i++) {
            for (int j = 0; j < partialMatrix[i].length; j++) {
                Color pixel = partialMatrix[i][j];
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                int average = (r + g + b) / 3;
                r = average;
                g = average;
                b = average;
                partialMatrix[i][j] = new Color(r, g, b);

            }
        }
        map.replace(index, partialMatrix);
        sem.release();
        return partialMatrix;
    }

    private Color[][] applyBrightness(int index,ConcurrentHashMap<Integer, Color [] []> map,
                                      int value, Semaphore sem) throws InterruptedException {
        sem.acquire();
        Color[][] partialMatrix = map.get(index);
        for (int i = 0; i < partialMatrix.length; i++) {
            for (int j = 0; j < partialMatrix[i].length; j++) {

                // fetches values of each pixel
                Color pixel = partialMatrix[i][j];
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
                partialMatrix[i][j] = new Color(r, g, b);

            }
        }
        map.replace(index, partialMatrix);
        sem.release();
        return partialMatrix;
    }

}
