package Threadpool;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import Utils.Utils;

public class ApplyFiltersThreadpool {

    public ApplyFiltersThreadpool() {

    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Color[][] res = new Color[0][0];
        String filePath = "";
        String outputFile = "threadpool.jpg";
        System.out.println("Insert the name of the file path you would like to use.");
        filePath = input.nextLine();

        String fileName = Utils.getFileName(filePath);

        System.out.println("How many threads?");
        String inputThreads = input.nextLine();
        int nThreads = Integer.parseInt(inputThreads);

        String content = " \"Welcome to the Threadpool image filtering process, choose a number.\n" +
                "1- Brightness\n"+
                "2- Grayscale\n"+
                "3- Swirl\n" +
                "4- Glass\n"+
                "5- Blur\n";

        System.out.println(content);
        String choice =  input.nextLine();

        input.close();
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        ThreadpoolFilters threadpoolFilters = new ThreadpoolFilters(filePath,executor, nThreads);
        String row = "";
        String multipleFileRow = "";
        switch (choice) {
            case "1":
                outputFile = Utils.getOutputFileName(fileName, "BrighterThreadpool.jpg");
                long startBrightness = System.currentTimeMillis();
                res = threadpoolFilters.BrighterFilter(128);
                long brightnessTime = System.currentTimeMillis() - startBrightness;
                System.out.println("Time of Threadpool Brightness filter: " + brightnessTime);
                row = Utils.generateTable(brightnessTime, "Brightness", Integer.toString(nThreads), fileName);
                multipleFileRow = Utils.generateTable("Threadpool-based", brightnessTime, "Brightness", Integer.toString(nThreads), fileName);
                break;
            case "2":

                long startGreyScale = System.currentTimeMillis();
                res = threadpoolFilters.GrayScaleFilter();
                long greyScaleTime = System.currentTimeMillis() - startGreyScale;
                outputFile = Utils.getOutputFileName(fileName, "GrayscaleThreadpool.jpg");
                System.out.println("Time of Threadpool Grayscale filter: " + greyScaleTime);
                row = Utils.generateTable(greyScaleTime, "Grayscale", Integer.toString(nThreads), fileName);
                multipleFileRow = Utils.generateTable("Threadpool-based", greyScaleTime, "Grayscale", Integer.toString(nThreads), fileName);
                break;
            case "3":

                long startSwirl = System.currentTimeMillis();
                res = threadpoolFilters.SwirlFilter();
                long swirlTime = System.currentTimeMillis() - startSwirl;
                outputFile = Utils.getOutputFileName(fileName, "SwirlThreadpool.jpg");
                System.out.println("Time of Threadpool Swirl filter: " + swirlTime);
                row = Utils.generateTable(swirlTime, "Swirl", Integer.toString(nThreads), fileName);
                multipleFileRow = Utils.generateTable("Threadpool-based", swirlTime, "Swirl", Integer.toString(nThreads), fileName);
                break;
            case "4":
                long startGlass = System.currentTimeMillis();
                res = threadpoolFilters.GlassFilter();
                long glassTime = System.currentTimeMillis() - startGlass;
                outputFile = Utils.getOutputFileName(fileName, "GlassThreadpool.jpg");
                System.out.println("Time of Threadpool Glass filter: " + glassTime);
                row = Utils.generateTable(glassTime, "Glass", Integer.toString(nThreads), fileName);
                multipleFileRow = Utils.generateTable("Threadpool-based", glassTime, "Glass", Integer.toString(nThreads), fileName);
                break;
            case "5":
                long startBlur = System.currentTimeMillis();
                res = threadpoolFilters.BlurFilter();
                long blurTime = System.currentTimeMillis() - startBlur;
                outputFile = Utils.getOutputFileName(fileName, "BlurThreadpool.jpg");
                System.out.println("Time of Threadpool Blur filter: " + blurTime);
                row = Utils.generateTable(blurTime, "Blur", Integer.toString(nThreads), fileName);
                multipleFileRow = Utils.generateTable("Threadpool-based", blurTime, "Blur", Integer.toString(nThreads), fileName);
                break;
        }

        executor.shutdown();

        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {

            executor.shutdownNow();
        }

        try{
            Utils.writeFile(row, "ThreadpoolTimes.md");
            Utils.writeFile(multipleFileRow, "MultipleImplementationsTimes.md");
            //Write to file with all implementations
        }catch (IOException e){
            System.out.println("Something was wrong writing the file.");
        }

        Utils.writeImage(res, outputFile);
    }
}
