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
        
