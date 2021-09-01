package Concurrent;

import Utils.Utils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ConcurrentMain {

    public ConcurrentMain() {
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Scanner input = new Scanner(System.in);
        String filePath = "";
        System.out.println("Insert the name of the file path you would like to use.");
        filePath = input.nextLine();

        String fileName = Utils.getFileName(filePath);

        System.out.println("How many threads?");
        String inputThreads = input.nextLine();
        int nThreads = Integer.parseInt(inputThreads);

        input.close();

        ConcurrentImplementation t2 = new ConcurrentImplementation(nThreads);
        long start = System.currentTimeMillis();
        t2.runThreads(filePath);
        long duration = System.currentTimeMillis() - start;
        System.out.println("Concurrent Grayscale & Brightness times: " + duration);
        //Write to concurrent file
        String row = Utils.generateTable(duration, "Grayscale & Brightness", Integer.toString(nThreads), fileName);
        Utils.writeFile(row, "ConcurrentTimes.md");
        //Write to file with all implementations
        String multipleFileRow = Utils.generateTable("Concurrent", duration, "Grayscale & Brightness", Integer.toString(nThreads), fileName);
        Utils.writeFile(multipleFileRow, "MultipleImplementationsTimes.md");
    }

}
