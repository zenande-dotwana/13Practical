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

        System.out.println("1. Linear Search Average: " + fourD.format(linearAveRuntime));
        System.out.println("2. Linear Search Std Dev: " + fourD.format(linearStdDeviation));
        System.out.println("3. Binary Search Average: " + fourD.format(binaryAveRuntime));
        System.out.println("4. Binary Search Std Dev: " + fourD.format(binaryStdDeviation));
    }
    
    // Node class to hold key and data
    static class Node {
        int key;
        String data;
        
        Node(int key, String data) {
            this.key = key;
            this.data = data;
        }
    }
    
    // Load data from file
    static void loadData(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            ArrayList<Node> nodeList = new ArrayList<>();
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                // Assuming format: "key data" or "key: data" or similar
                String[] parts = line.split("\\s+", 2);
                if (parts.length >= 2) {
                    try {
                        int key = Integer.parseInt(parts[0]);
                        String data = parts[1];
                        nodeList.add(new Node(key, data));
                    } catch (NumberFormatException e) {
                        // Skip lines that don't start with a number
                        continue;
                    }
                }
            }
            reader.close();
            
            // Convert ArrayList to array
            records = nodeList.toArray(new Node[0]);
            System.out.println("Loaded " + records.length + " records from " + filename);
            
        } catch (FileNotFoundException e) {
            System.err.println("Error: File " + filename + " not found!");
            System.err.println("Creating sample data for testing...");
            createSampleData();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            createSampleData();
        }
    }
    
    // Create sample data if file not found
    static void createSampleData() {
        records = new Node[1000];
        for (int i = 0; i < 1000; i++) {
            int key = 1 + (int)(Math.random() * 32654);
            records[i] = new Node(key, "Sample data for key " + key);
        }
        System.out.println("Created " + records.length + " sample records for testing");
    }
    

        
        
