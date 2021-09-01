package MultiThread;//

import Utils.Utils;

import java.io.IOException;
import java.util.Scanner;


public class ApplyFiltersMultiThread {
    public ApplyFiltersMultiThread() {
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String filePath = "";
        System.out.println("Insert the name of the file path you would like to use.");
        filePath = input.nextLine();

        String fileName = Utils.getFileName(filePath);

        System.out.println("How many threads?");
        String inputThreads = input.nextLine();
        int nThreads = Integer.parseInt(inputThreads);

        MultiThreadFilters multiThreadFilters = new MultiThreadFilters(filePath, nThreads);

        String content = " \"Welcome to the multiThread image filtering process, choose a number.\n" +
                "1- Brightness\n"+
                "2- Grayscale\n"+
                "3- Swirl\n" +
                "4- Glass\n"+
                "5- Blur\n";

        System.out.println(content);
        String choice =  input.nextLine();
        input.close();
        String row = "";
        String multipleFileRow = "";
        String outputName = "multiThread.jpg";
        switch (choice) {

            case "1":
                try{
                    outputName = Utils.getOutputFileName(fileName, "BrighterMultiThread.jpg");
                    long startBrightness = System.currentTimeMillis();
                    multiThreadFilters.BrighterFilter(outputName, 128);
                    long brightnessTime = System.currentTimeMillis() - startBrightness;
                    System.out.println("Time of MultiThread Brightness filter: " + brightnessTime);
                    row = Utils.generateTable(brightnessTime, "Brightness", Integer.toString(nThreads), fileName);
                    multipleFileRow = Utils.generateTable("MultiThread", brightnessTime, "Brightness", Integer.toString(nThreads), fileName);
                }catch (InterruptedException ex) {
                    System.out.println("Something was interrupted");
                }
                break;
            case "2":
                try{
                    outputName = Utils.getOutputFileName(fileName, "GrayscaleMultiThread.jpg");
                    long startGreyScale = System.currentTimeMillis();
                    multiThreadFilters.GreyScaleFilter(outputName);
                    long greyScaleTime = System.currentTimeMillis() - startGreyScale;
                    System.out.println("Time of MultiThread Grayscale filter: " + greyScaleTime);
                    row = Utils.generateTable(greyScaleTime, "GreyScale", Integer.toString(nThreads), fileName);
                    multipleFileRow = Utils.generateTable("MultiThread", greyScaleTime, "GreyScale", Integer.toString(nThreads), fileName);
                }catch (InterruptedException ex) {
                    System.out.println("Something was interrupted");
                }
                break;
            case "3":
                try{
                    outputName = Utils.getOutputFileName(fileName, "SwirlFilterMultiThread.jpg");
                    long startSwirl = System.currentTimeMillis();
                    multiThreadFilters.SwirlFilter(outputName);
                    long swirlTime = System.currentTimeMillis() - startSwirl;
                    System.out.println("Time of MultiThread Swirl filter: " + swirlTime);
                    row = Utils.generateTable(swirlTime, "Swirl", Integer.toString(nThreads), fileName);
                    multipleFileRow = Utils.generateTable("MultiThread", swirlTime, "Swirl", Integer.toString(nThreads), fileName);
                }catch (InterruptedException ex) {
                    System.out.println("Something was interrupted");
                }
                break;
            case "4":
                try{
                    outputName = Utils.getOutputFileName(fileName, "GlassFilterMultiThread.jpg");
                    long startGlass = System.currentTimeMillis();
                    multiThreadFilters.GlassFilter(outputName);
                    long glassTime = System.currentTimeMillis() - startGlass;
                    System.out.println("Time of MultiThread Glass filter: " + glassTime);
                    row = Utils.generateTable(glassTime, "Glass", Integer.toString(nThreads), fileName);
                    multipleFileRow = Utils.generateTable("MultiThread", glassTime, "Glass", Integer.toString(nThreads), fileName);
                }catch (InterruptedException ex) {
                    System.out.println("Something was interrupted");
                }
                break;
            case "5":
                try{
                    outputName = Utils.getOutputFileName(fileName, "BlurFilterMultiThread.jpg");
                    long startBlur = System.currentTimeMillis();
                    multiThreadFilters.BlurFilter(outputName);
                    long blurTime = System.currentTimeMillis() - startBlur;
                    System.out.println("Time of MultiThread Blur filter: " + blurTime);
                    row = Utils.generateTable(blurTime, "Blur",  Integer.toString(nThreads), fileName);
                    multipleFileRow = Utils.generateTable("MultiThread", blurTime, "Blur",  Integer.toString(nThreads), fileName);
                }catch (InterruptedException ex) {
                    System.out.println("Something was interrupted");
                }
                break;
        }

        try{
            Utils.writeFile(row, "MultiThreadTimes.md");
            Utils.writeFile(multipleFileRow, "MultipleImplementationsTimes.md");
        }catch (IOException e){
            System.out.println("Something was wrong writing the file.");
        }
    }
}

