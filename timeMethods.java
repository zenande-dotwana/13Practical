import java.lang.Math.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class timeMethods {
    public static int N = 32654; // Maximum possible key range
    public static Node[] records; // Array to store all records
    public static Node[] sortedRecords; // Sorted copy for binary search

    public static void main(String[] args) {
        DecimalFormat twoD = new DecimalFormat("0.00");
        DecimalFormat fourD = new DecimalFormat("0.0000");
        DecimalFormat fiveD = new DecimalFormat("0.00000");
        long start, finish;
        double linearRunTime = 0, linearRunTime2 = 0;
        double binaryRunTime = 0, binaryRunTime2 = 0;
        double time;
        int n = N;
        int repetition, repetitions = 30;
        
        // Load data from file
        loadData("ulysses.numbered");
        
        // Create sorted copy for binary search
        sortedRecords = records.clone();
        Arrays.sort(sortedRecords, (a, b) -> Integer.compare(a.key, b.key));

         // Generate 30 random keys for testing
        int[] testKeys = generateRandomKeys(30, 1, 32654);
        
        System.out.println("Starting timing experiments with " + repetitions + " repetitions...");
        System.out.println("Each repetition performs 30 key lookups");
        System.out.println("------------------------------------------------");
        
        for(repetition = 0; repetition < repetitions; repetition++) {
            // Time linear search
            start = System.currentTimeMillis();
            for(int key : testKeys) {
                linearSearch(key);
            }
            finish = System.currentTimeMillis();
            time = (double)(finish - start);
            linearRunTime += time;
            linearRunTime2 += (time * time);
            
            // Time binary search
            start = System.currentTimeMillis();
            for(int key : testKeys) {
                binarySearch(key);
            }
            finish = System.currentTimeMillis();
            time = (double)(finish - start);
            binaryRunTime += time;
            binaryRunTime2 += (time * time);
            
            // Shuffle keys for next repetition to avoid caching effects
            shuffleArray(testKeys);
        }
        
        // Calculate statistics for linear search
        double linearAveRuntime = linearRunTime / repetitions;
        double linearStdDeviation = Math.sqrt((linearRunTime2 / repetitions) - 
                                    (linearAveRuntime * linearAveRuntime));
        
        // Calculate statistics for binary search
        double binaryAveRuntime = binaryRunTime / repetitions;
        double binaryStdDeviation = Math.sqrt((binaryRunTime2 / repetitions) - 
                                   (binaryAveRuntime * binaryAveRuntime));
        
        // Output results
        System.out.println("\n\n========== SEARCH ALGORITHM PERFORMANCE COMPARISON ==========");
        System.out.println("Testing with 30 random keys, repeated " + repetitions + " times");
        System.out.println("------------------------------------------------");
        System.out.println("LINEAR SEARCH (unsorted data):");
        System.out.println("  Average time: " + fourD.format(linearAveRuntime) + " ms");
        System.out.println("  Standard deviation: " + fourD.format(linearStdDeviation) + " ms");
        System.out.println("\nBINARY SEARCH (sorted data):");
        System.out.println("  Average time: " + fourD.format(binaryAveRuntime) + " ms");
        System.out.println("  Standard deviation: " + fourD.format(binaryStdDeviation) + " ms");
        System.out.println("------------------------------------------------");
        System.out.println("\nFour required numbers for submission:");
        
