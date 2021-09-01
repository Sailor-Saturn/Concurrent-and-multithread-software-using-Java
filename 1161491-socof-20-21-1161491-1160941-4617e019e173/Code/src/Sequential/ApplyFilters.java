package Sequential;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import Utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class ApplyFilters {
    public ApplyFilters() {
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String filePath = "";
        System.out.println("Insert the name of the file path you would like to use.");
        filePath = input.nextLine();

        String fileName = Utils.getFileName(filePath);

        String content = " \"Welcome to the Sequential image filtering process, choose a number.\n" +
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
        String outputFile = "";
        Filters filters = new Filters(filePath);

        switch (choice) {
            case "1":
                outputFile = Utils.getOutputFileName(fileName, "Brighter.jpg");
                long startBrightness = System.currentTimeMillis();
                filters.BrighterFilter(outputFile, 128);
                long brightnessTime = System.currentTimeMillis() - startBrightness;
                System.out.println("Time of Sequential Bright filter: " + brightnessTime);
                row = Utils.generateTable(brightnessTime, "Brightness", "--", fileName);
                multipleFileRow =  Utils.generateTable("Sequential", brightnessTime, "Brightness", "--", fileName);
                break;
            case "2":
                outputFile = Utils.getOutputFileName(fileName, "Greyscale.jpg");
                long startGreyScale = System.currentTimeMillis();
                filters.GreyScaleFilter(outputFile);
                long greyScaleTime = System.currentTimeMillis() - startGreyScale;
                System.out.println("Time of Sequential Grayscale filter: " + greyScaleTime);
                row = Utils.generateTable(greyScaleTime, "Grayscale", "--", fileName);
                multipleFileRow = Utils.generateTable("Sequential", greyScaleTime, "Grayscale", "--", fileName);
                break;
            case "3":
                outputFile = Utils.getOutputFileName(fileName, "Swirl.jpg");
                long startSwirl = System.currentTimeMillis();
                filters.SwirlFilter(outputFile);
                long swirlTime = System.currentTimeMillis() - startSwirl;
                System.out.println("Time of Sequential Swirl filter: " + swirlTime);
                row = Utils.generateTable(swirlTime, "Swirl", "--", fileName);
                multipleFileRow = Utils.generateTable("Sequential", swirlTime, "Swirl", "--", fileName);
                break;
            case "4":
                outputFile = Utils.getOutputFileName(fileName, "Glass.jpg");
                long startGlass = System.currentTimeMillis();
                filters.GlassFilter(outputFile);
                long glassTime = System.currentTimeMillis() - startGlass;
                System.out.println("Time of Sequential Glass filter: " + glassTime);
                row = Utils.generateTable(glassTime, "Glass", "--", fileName);
                multipleFileRow = Utils.generateTable("Sequential", glassTime, "Glass", "--", fileName);
                break;
            case "5":
                outputFile = Utils.getOutputFileName(fileName, "Blur.jpg");
                long startBlur = System.currentTimeMillis();
                filters.BlurFilter(outputFile);
                long blurTime = System.currentTimeMillis() - startBlur;
                System.out.println("Time of Sequential Blur filter: " + blurTime);
                row = Utils.generateTable(blurTime, "Blur", "--", fileName);
                multipleFileRow = Utils.generateTable("Sequential", blurTime, "Blur", "--", fileName);
                break;
        }

        Utils.writeFile(row, "SequentialTimes.md");
        //Write to file with all implementations
        Utils.writeFile(multipleFileRow, "MultipleImplementationsTimes.md");
    }
}

