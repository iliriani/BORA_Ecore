package RefClasses;

import boraproj.services.RepoConnect;

import java.util.*;

public class ModelProcessor {
    private RepoConnect conn = new RepoConnect();
    public void getAverageNrOfUniqueClasses() {
        ArrayList<String> allModels = conn.getAllModelNames();
        // Store classes for each model
        Map<String, ArrayList<String>> modelClasses = new HashMap<>();
        // Track which models have each class
        Map<String, Set<String>> classToModels = new HashMap<>();

        // Step 1: Collect classes for each model
        for (String model : allModels) {
            ArrayList<String> allClasses = conn.getAllDomainClasses(model);

            // Store classes for this model
            modelClasses.put(model, new ArrayList<>(allClasses));

            // Track which models have each class
            for (String cls : allClasses) {
                classToModels.computeIfAbsent(cls, k -> new HashSet<>()).add(model);
            }

            // Print model and its classes
//            System.out.println("Model: " + model);
//            System.out.println("Classes: " + allClasses);
        }

        // Step 2: Identify unique classes for each model
        Map<String, ArrayList<String>> uniqueClasses = new HashMap<>();
        for (String model : allModels) {
            uniqueClasses.put(model, new ArrayList<>());
            ArrayList<String> classes = modelClasses.get(model);
            for (String cls : classes) {
                // A class is unique if it appears in exactly one model
                if (classToModels.get(cls).size() == 1) {
                    uniqueClasses.get(model).add(cls);
                }
            }
            // Print unique classes for this model
            System.out.println("Unique Classes: " + uniqueClasses.get(model));
        }

        // Step 3: Calculate average number of unique classes
        int sumUniqueClasses = uniqueClasses.values().stream()
                .mapToInt(List::size)
                .sum();
        double average = allModels.isEmpty() ? 0.0 : (double) sumUniqueClasses / allModels.size();

        // Print results
        System.out.println("Sum of unique classes: " + sumUniqueClasses);
        System.out.println("Number of models: " + allModels.size());
        System.out.printf("Average number of unique classes per model: %.2f%n", average);
    }
}