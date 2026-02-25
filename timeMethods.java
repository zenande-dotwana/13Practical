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
        
